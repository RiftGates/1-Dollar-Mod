package com.person98.mod1.handlers;

import com.person98.mod1.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChickenBootHandler {

    private static final int COOLDOWN_TICKS = 5; // Adjust as needed
    private static Map<UUID, Integer> playerCooldowns = new HashMap<>();
    private static Map<UUID, Boolean> playerSneakingStates = new HashMap<>(); // To track if a player is sneaking

    public static void register() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                world.getPlayers().forEach(player -> {
                    UUID playerUUID = player.getUuid();
                    boolean isSneaking = player.isSneaking();
                    boolean wasSneaking = playerSneakingStates.getOrDefault(playerUUID, false);

                    if (player.getVelocity().lengthSquared() > 0.0001) {
                        if (player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.CHICKEN_BOOTS) {

                            // If the player isn't on cooldown, play the sound
                            if (!playerCooldowns.containsKey(playerUUID) || playerCooldowns.get(playerUUID) <= 0) {
                                playChickenCluckSound(world, player.getX(), player.getY(), player.getZ());
                                playerCooldowns.put(playerUUID, COOLDOWN_TICKS);
                            } else {
                                // Otherwise, decrement the cooldown
                                playerCooldowns.put(playerUUID, playerCooldowns.get(playerUUID) - 1);
                            }

                            // If the player starts sneaking (but wasn't sneaking last tick)
                            if (isSneaking && !wasSneaking) {
                                dropEgg(world, player.getX(), player.getY(), player.getZ());
                            }

                            // Update sneaking state for the player
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
