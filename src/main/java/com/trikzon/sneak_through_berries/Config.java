package com.trikzon.sneak_through_berries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public static class ConfigBean {
        public boolean sneakToStopDamage = true;
        public boolean requiredArmorLosesDurabilityWhenWalking = true;
        public RequiredToWalkConfigBean requiredToWalk = new RequiredToWalkConfigBean();
    }

    public static class RequiredToWalkConfigBean {
        public boolean boots = true;
        public boolean leggings = true;
        public boolean chestPlate = false;
        public boolean helmet = false;
    }

    public static ConfigBean init(Logger logger, String modId) {
        File file = new File("./config/" + modId + ".json");

        ConfigBean config = new ConfigBean();
        if (file.exists()) {
            config = read(logger, file);
        } else {
            write(logger, file, config);
        }
        return config;
    }

    private static ConfigBean read(Logger logger, File file) {
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(fileReader, ConfigBean.class);
        } catch (IOException e) {
            logger.error("Failed to read from config file.");
            return null;
        }
    }

    private static void write(Logger logger, File file, ConfigBean config) {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            logger.error("Failed to write the config file as parent directories couldn't be made.");
            return;
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(config));
            fileWriter.flush();
        } catch (IOException e) {
            logger.error("Failed to write the config file.");
        }
    }
}
