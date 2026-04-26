package com.dimension.fourthdimension.entity;

import com.dimension.fourthdimension.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class StasisGrenadeEntity extends ThrownItemEntity {
    private final World entityWorld;

    public StasisGrenadeEntity(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
        this.entityWorld = world;
    }

    public StasisGrenadeEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.STASIS_GRENADE, owner, world, stack);
        this.entityWorld = world;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STASIS_GRENADE;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.entityWorld != null && !this.entityWorld.isClient()) {
            var bubble = new StasisBubbleEntity(ModEntities.STASIS_BUBBLE, this.entityWorld);
            bubble.setPosition(this.getX(), this.getY(), this.getZ());
            this.entityWorld.spawnEntity(bubble);
            this.discard();
        }
    }
}

