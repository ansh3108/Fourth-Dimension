package com.dimension.fourthdimension.entity;

import com.dimension.fourthdimension.FourthDimension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<StasisGrenadeEntity> STASIS_GRENADE = EntityType.Builder.<StasisGrenadeEntity>create(StasisGrenadeEntity::new, SpawnGroup.MISC)
            .dimensions(0.25f, 0.25f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FourthDimension.MOD_ID, "stasis_grenade")));

    public static final EntityType<StasisBubbleEntity> STASIS_BUBBLE = EntityType.Builder.<StasisBubbleEntity>create(StasisBubbleEntity::new, SpawnGroup.MISC)
            .dimensions(5f, 5f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FourthDimension.MOD_ID, "stasis_bubble")));

    public static void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(FourthDimension.MOD_ID, "stasis_grenade"), STASIS_GRENADE);
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(FourthDimension.MOD_ID, "stasis_bubble"), STASIS_BUBBLE);
    }
}

