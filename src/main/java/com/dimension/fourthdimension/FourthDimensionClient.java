package com.dimension.fourthdimension;

import com.dimension.fourthdimension.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class FourthDimensionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.STASIS_GRENADE, FlyingItemEntityRenderer::new);
        
        EntityRendererRegistry.register(ModEntities.STASIS_BUBBLE, EmptyEntityRenderer::new);
    }
}