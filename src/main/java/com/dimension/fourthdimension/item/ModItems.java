package com.dimension.fourthdimension.item;

import com.dimension.fourthdimension.FourthDimension;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final RegistryKey<Item> REWIND_KEY = key("rewind_pocketwatch");
    public static final RegistryKey<Item> STASIS_KEY = key("stasis_grenade");
    public static final RegistryKey<Item> CLOCK_KEY = key("acceleration_clock");

    public static final Item REWIND_POCKETWATCH = new RewindPocketwatchItem(new Item.Settings().registryKey(REWIND_KEY).maxCount(1));
    public static final Item STASIS_GRENADE = new StasisGrenadeItem(new Item.Settings().registryKey(STASIS_KEY).maxCount(16));
    public static final Item ACCELERATION_CLOCK = new AccelerationClockItem(new Item.Settings().registryKey(CLOCK_KEY).maxCount(1));

    private static RegistryKey<Item> key(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FourthDimension.MOD_ID, name));
    }

    public static void registerModItems() {
        Registry.register(Registries.ITEM, REWIND_KEY, REWIND_POCKETWATCH);
        Registry.register(Registries.ITEM, STASIS_KEY, STASIS_GRENADE);
        Registry.register(Registries.ITEM, CLOCK_KEY, ACCELERATION_CLOCK);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(REWIND_POCKETWATCH);
            entries.add(STASIS_GRENADE);
            entries.add(ACCELERATION_CLOCK);
        });
    }
}

