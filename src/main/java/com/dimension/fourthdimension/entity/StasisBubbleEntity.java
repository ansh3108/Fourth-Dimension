package com.dimension.fourthdimension.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StasisBubbleEntity extends Entity {
    private int age = 0;
    private final World entityWorld;

    public StasisBubbleEntity(EntityType<?> type, World world) {
        super(type, world);
        this.entityWorld = world;
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.entityWorld == null || this.entityWorld.isClient()) return;

        if (++age > 100) {
            this.discard();
            return;
        }

        var entities = this.entityWorld.getOtherEntities(this, this.getBoundingBox().expand(5.0));
        for (Entity entity : entities) {
            entity.setVelocity(Vec3d.ZERO);
            entity.velocityDirty = true;
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override protected void initDataTracker(DataTracker.Builder builder) {}
    @Override protected void readCustomData(ReadView view) {}
    @Override protected void writeCustomData(WriteView view) {}
}

