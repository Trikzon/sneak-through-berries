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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SneakThroughBerries implements ModInitializer
{
    public static final String MOD_ID = "sneak-through-berries";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final File MOD_CONFIG_FILE = new File("./config/" + MOD_ID + ".json");

    public static ConfigBean CONFIG = new ConfigBean();

    public static class ConfigBean
    {
        public boolean requireBootsToWalk = true;
        public boolean requireLeggingsToWalk = true;
        public boolean requireChestToWalk = false;
        public boolean requireHelmetToWalk = false;
        public boolean requireNothingToWalk = false;
        public boolean worksForPlayer = true;
        public boolean worksForNonPlayer = true;
    }

    private void readConfigFile()
    {
        try (FileReader file = new FileReader(MOD_CONFIG_FILE))
        {
            Gson gson = new Gson();
            CONFIG = gson.fromJson(file, ConfigBean.class);
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to read from config file.");
        }
    }

    private void writeConfigFile()
    {
        if (!MOD_CONFIG_FILE.getParentFile().exists() && !MOD_CONFIG_FILE.getParentFile().mkdirs())
        {
            LOGGER.error("Failed to write the config file as parent directories couldn't be made.");
            return;
        }
        try (FileWriter file = new FileWriter(MOD_CONFIG_FILE))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            file.write(gson.toJson(CONFIG));
            file.flush();
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to write the config file.");
        }
    }

    @Override
    public void onInitialize()
    {
        if (MOD_CONFIG_FILE.exists())
        {
            readConfigFile();
        }
        else
        {
            writeConfigFile();
        }
    }
}
