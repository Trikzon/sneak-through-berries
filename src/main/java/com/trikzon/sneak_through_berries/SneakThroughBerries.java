/*
 * Copyright 2020 Trikzon
 *
 * Sneak Through Berries is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * https://www.gnu.org/licenses/
 */
package com.trikzon.sneak_through_berries;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SneakThroughBerries implements ModInitializer {
    public static final String MOD_ID = "sneak-through-berries";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Config.ConfigBean CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = Config.init(LOGGER, MOD_ID);
    }
}
