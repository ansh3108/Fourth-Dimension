package com.dimension.fourthdimension.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class RewindPocketwatchItem extends Item {
    private static final Map<UUID, Deque<Vec3d>> HISTORY = new HashMap<>();

    public RewindPocketwatchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && user instanceof ServerPlayerEntity player) {
            var history = HISTORY.get(player.getUuid());
            if (history != null && !history.isEmpty()) {
                Vec3d pos = history.peekLast();
                player.teleport((ServerWorld) world, pos.x, pos.y, pos.z, Collections.emptySet(), player.getYaw(), player.getPitch(), true);
                history.clear();
                player.getItemCooldownManager().set(stack, 100);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public static void recordState(ServerPlayerEntity player) {
        var history = HISTORY.computeIfAbsent(player.getUuid(), k -> new LinkedList<>());
        history.addFirst(new Vec3d(player.getX(), player.getY(), player.getZ()));
        if (history.size() > 100) history.removeLast();
    }
}

