package com.dimension.fourthdimension.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomData;
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
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChronoAnchorItem extends Item {
    public ChronoAnchorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.success(stack);
        }

        CustomData customData = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, CustomData.DEFAULT);
        NbtCompound nbt = customData.copyNbt();

        if (nbt.contains("AnchorSet") && nbt.getBoolean("AnchorSet")) {
            
            double x = nbt.getDouble("PosX");
            double y = nbt.getDouble("PosY");
            double z = nbt.getDouble("PosZ");
            float yaw = nbt.getFloat("Yaw");
            float pitch = nbt.getFloat("Pitch");
            String dim = nbt.getString("Dimension");
            float health = nbt.getFloat("Health");
            int food = nbt.getInt("Food");

            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
            ServerWorld targetWorld = serverPlayer.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, Identifier.of(dim)));

            if (targetWorld != null) {
                serverPlayer.teleport(targetWorld, x, y, z, yaw, pitch);
                
                serverPlayer.setHealth(health);
                serverPlayer.getHungerManager().setFoodLevel(food);

                nbt.putBoolean("AnchorSet", false);
                stack.set(DataComponentTypes.CUSTOM_DATA, CustomData.of(nbt));

                world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                user.sendMessage(Text.literal("§dTimeline restored."), true);
                
                user.getItemCooldownManager().set(this, 100); 
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

            stack.set(DataComponentTypes.CUSTOM_DATA, CustomData.of(nbt));

            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.sendMessage(Text.literal("§bSpatial anchor locked."), true);
        }

        return TypedActionResult.success(stack);
    }
}