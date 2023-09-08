package com.person98.mod1.handlers;

import com.person98.mod1.Mod1;
import com.person98.mod1.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ChickenInteractEventHandler {
    public static void initialize() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSneaking() && entity instanceof ChickenEntity) {

                if (!world.isClient) {
                    ItemStack chickenItem = new ItemStack(ModItems.CHICKEN, 1);

                    if (player instanceof ServerPlayerEntity) {
                        synchronized (((ServerPlayerEntity) player).getInventory()) {
                            ((ServerPlayerEntity) player).getInventory().insertStack(chickenItem);
                        }
                    }

                    // Remove the chicken entity
                    entity.remove(Entity.RemovalReason.DISCARDED);

                    // Play the chicken clucking sound
                    playChickenCluckSound(world, player.getX(), player.getY(), player.getZ());

                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        });
    }

    private static void playChickenCluckSound(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.AMBIENT, 1.0F, 1.0F);
    }
}
