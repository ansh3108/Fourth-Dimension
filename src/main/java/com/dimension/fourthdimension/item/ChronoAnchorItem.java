package com.dimension.fourthdimension.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import java.util.Set;

public class ChronoAnchorItem extends Item {
    public ChronoAnchorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        NbtComponent customData = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound nbt = customData.copyNbt();

        if (nbt.contains("AnchorSet") && nbt.getBoolean("AnchorSet").orElse(false)) {
            double x = nbt.getDouble("PosX").orElse(0.0);
            double y = nbt.getDouble("PosY").orElse(0.0);
            double z = nbt.getDouble("PosZ").orElse(0.0);
            float yaw = nbt.getFloat("Yaw").orElse(0.0f);
            float pitch = nbt.getFloat("Pitch").orElse(0.0f);
            String dim = nbt.getString("Dimension").orElse("minecraft:overworld");
            float health = nbt.getFloat("Health").orElse(20.0f);
            int food = nbt.getInt("Food").orElse(20);

            if (world.getServer() != null) {
                ServerWorld targetWorld = world.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, Identifier.of(dim)));
                
                if (targetWorld != null && user instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.teleport(targetWorld, x, y, z, Set.of(), yaw, pitch, true);
                    serverPlayer.setHealth(health);
                    serverPlayer.getHungerManager().setFoodLevel(food);

                    nbt.putBoolean("AnchorSet", false);
                    stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));

                    world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.sendMessage(Text.literal("§dTimeline restored."), true);
                    
                    user.getItemCooldownManager().set(stack, 100); 
                }
            }
        } else {
            nbt.putBoolean("AnchorSet", true);
            nbt.putDouble("PosX", user.getX());
            nbt.putDouble("PosY", user.getY());
            nbt.putDouble("PosZ", user.getZ());
            nbt.putFloat("Yaw", user.getYaw());
            nbt.putFloat("Pitch", user.getPitch());
            nbt.putString("Dimension", world.getRegistryKey().getValue().toString());
            nbt.putFloat("Health", user.getHealth());
            nbt.putInt("Food", user.getHungerManager().getFoodLevel());

            stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));

            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.sendMessage(Text.literal("§bSpatial anchor locked."), true);
        }

        return ActionResult.SUCCESS;
    }
}