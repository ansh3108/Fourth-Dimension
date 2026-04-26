package com.dimension.fourthdimension;

import com.dimension.fourthdimension.entity.ModEntities;
import com.dimension.fourthdimension.item.AccelerationClockItem;
import com.dimension.fourthdimension.item.ModItems;
import com.dimension.fourthdimension.item.RewindPocketwatchItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class FourthDimension implements ModInitializer {
    public static final String MOD_ID = "fourth-dimension";

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModEntities.registerEntities();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerManager().getPlayerList()) {
                if (player.getMainHandStack().isOf(ModItems.REWIND_POCKETWATCH)) {
                    RewindPocketwatchItem.recordState(player);
                }
                
                if (player.getMainHandStack().isOf(ModItems.ACCELERATION_CLOCK)) {
                    AccelerationClockItem.tickClock(player);
                }
            }
        });
    }
}