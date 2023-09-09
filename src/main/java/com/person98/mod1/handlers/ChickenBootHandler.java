package com.person98.mod1.handlers;

import com.person98.mod1.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChickenBootHandler {

    private static final int COOLDOWN_TICKS = 5;
    private static Map<UUID, Integer> playerCooldowns = new HashMap<>();
    private static Map<UUID, Boolean> playerSneakingStates = new HashMap<>();
    private static Map<UUID, ChickenEntity> fallingChickens = new HashMap<>();

    public static void register() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                world.getPlayers().forEach(player -> {
                    UUID playerUUID = player.getUuid();
                    boolean isSneaking = player.isSneaking();
                    boolean wasSneaking = playerSneakingStates.getOrDefault(playerUUID, false);
                    ChickenEntity chicken = fallingChickens.get(playerUUID);

                    if (chicken != null && chicken.isOnGround()) {
                        player.stopRiding();
                        chicken.remove(Entity.RemovalReason.DISCARDED);
                        fallingChickens.remove(playerUUID);
                        world.spawnParticles(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 20, 0.5, 0.5, 0.5, 0.5);
                        player.fallDistance = 0; // Prevent fall damage
                    } else if (!player.isOnGround() && !fallingChickens.containsKey(playerUUID) && player.getVelocity().y < -0.5) {
                        chicken = new ChickenEntity(EntityType.CHICKEN, world);
                        chicken.updatePosition(player.getX(), player.getY() - 1, player.getZ());
                        world.spawnEntity(chicken);
                        player.startRiding(chicken, true);
                        fallingChickens.put(playerUUID, chicken);
                    }

                    if (player.getVelocity().lengthSquared() > 0.0001) {
                        if (player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.CHICKEN_BOOTS) {
                            if (!playerCooldowns.containsKey(playerUUID) || playerCooldowns.get(playerUUID) <= 0) {
                                playChickenCluckSound(world, player.getX(), player.getY(), player.getZ());
                                playerCooldowns.put(playerUUID, COOLDOWN_TICKS);
                            } else {
                                playerCooldowns.put(playerUUID, playerCooldowns.get(playerUUID) - 1);
                            }

                            if (isSneaking && !wasSneaking) {
                                dropEgg(world, player.getX(), player.getY(), player.getZ());
                            }

                            playerSneakingStates.put(playerUUID, isSneaking);
                        }
                    }
                });
            }
        });
    }

    private static void playChickenCluckSound(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    private static void dropEgg(World world, double x, double y, double z) {
        ItemEntity eggEntity = new ItemEntity(world, x, y, z, new ItemStack(Items.EGG));
        world.spawnEntity(eggEntity);
    }
}