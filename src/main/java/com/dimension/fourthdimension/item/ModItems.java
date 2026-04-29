package com.dimension.fourthdimension.item;

import com.dimension.fourthdimension.FourthDimension;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final RegistryKey<Item> ACCELERATION_CLOCK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FourthDimension.MOD_ID, "acceleration_clock"));
    public static final Item ACCELERATION_CLOCK = Registry.register(
        Registries.ITEM, 
        ACCELERATION_CLOCK_KEY.getValue(),
        new AccelerationClockItem(new Item.Settings().registryKey(ACCELERATION_CLOCK_KEY).maxCount(1))
    );

    public static final RegistryKey<Item> REWIND_POCKETWATCH_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FourthDimension.MOD_ID, "rewind_pocketwatch"));
    public static final Item REWIND_POCKETWATCH = Registry.register(
        Registries.ITEM, 
        REWIND_POCKETWATCH_KEY.getValue(),
        new RewindPocketwatchItem(new Item.Settings().registryKey(REWIND_POCKETWATCH_KEY).maxCount(1))
    );

    public static final RegistryKey<Item> CHRONO_ANCHOR_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FourthDimension.MOD_ID, "chrono_anchor"));
    public static final Item CHRONO_ANCHOR = Registry.register(
        Registries.ITEM, 
        CHRONO_ANCHOR_KEY.getValue(),
        new ChronoAnchorItem(new Item.Settings().registryKey(CHRONO_ANCHOR_KEY).maxCount(1))
    );

    private static void addItemsToToolItemGroup(FabricItemGroupEntries entries) {
        entries.add(ACCELERATION_CLOCK);
        entries.add(REWIND_POCKETWATCH);
        entries.add(CHRONO_ANCHOR);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolItemGroup);
    }
}