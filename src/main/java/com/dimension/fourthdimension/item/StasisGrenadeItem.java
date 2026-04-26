package com.dimension.fourthdimension.item;

import com.dimension.fourthdimension.entity.StasisGrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class StasisGrenadeItem extends Item {
    public StasisGrenadeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            var grenade = new StasisGrenadeEntity(world, user, stack);
            grenade.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(grenade);
        }
        if (!user.getAbilities().creativeMode) stack.decrement(1);
        return ActionResult.SUCCESS;
    }
}

