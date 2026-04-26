package com.dimension.fourthdimension.item;

import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AccelerationClockItem extends Item {
    public AccelerationClockItem(Settings settings) {
        super(settings);
    }

    public static void tickClock(ServerPlayerEntity player) {
        if (player.getEntityWorld() instanceof ServerWorld serverWorld) {
            HitResult hit = player.raycast(5.0, 0.0F, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                var pos = ((BlockHitResult) hit).getBlockPos();
                var state = serverWorld.getBlockState(pos);
                
                for (int i = 0; i < 10; i++) {
                    if (state.hasRandomTicks()) {
                        state.randomTick(serverWorld, pos, serverWorld.random);
                    }
                }
            }
        }
    }
}

