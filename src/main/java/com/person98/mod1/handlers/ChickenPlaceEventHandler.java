package com.person98.mod1.handlers;

import com.person98.mod1.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class ChickenPlaceEventHandler {
    public static void initialize() {
        // Register the event handler
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // If the item in player's hand is ModItems.CHICKEN
            if (hand == Hand.MAIN_HAND && ModItems.CHICKEN.equals(player.getStackInHand(hand).getItem())) {

                player.getStackInHand(hand).decrement(1);
                if (player.getStackInHand(hand).isEmpty()) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                }
                BlockPos pos = hitResult.getBlockPos().offset(hitResult.getSide());

                // Check if the world is server (because we don't want to spawn entities on the client)
                if (!world.isClient) {
                    // Spawn the chicken
                    ChickenEntity chicken = new ChickenEntity(EntityType.CHICKEN, world);
                    chicken.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    world.spawnEntity(chicken);
                }

                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

    }

}