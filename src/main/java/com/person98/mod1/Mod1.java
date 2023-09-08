package com.person98.mod1;

import com.person98.mod1.handlers.ChickenBootHandler;
import com.person98.mod1.handlers.ChickenInteractEventHandler;
import com.person98.mod1.handlers.ChickenPlaceEventHandler;
import com.person98.mod1.item.ModItemGroups;
import com.person98.mod1.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.Channels;

public class Mod1 implements ModInitializer {
	public static final String MOD_ID = "mod1";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ChickenInteractEventHandler.initialize();
		ChickenPlaceEventHandler.initialize();
		ChickenBootHandler.register();

	}
}