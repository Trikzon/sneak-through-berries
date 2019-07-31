package trikzon.sneakthroughberries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config implements ModInitializer {

    public static ConfigObject CONFIG;
    private final Path configFile = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("sneakthroughberries.json");

    @Override
    public void onInitialize() {
        String config = "";
        if (Files.exists(configFile)) {
            try {
                config = new String(Files.readAllBytes(configFile), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            CONFIG = save();
            return;
        }

        Gson gson = new Gson();
        CONFIG = gson.fromJson(config, ConfigObject.class);
    }

    private ConfigObject save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ConfigObject defaultConfig = new ConfigObject(true, true, false, true, true, false, false);
        String config = gson.toJson(defaultConfig);

        try (FileWriter file = new FileWriter(configFile.toString())){
            file.write(config);
            file.flush();

        } catch (IOException e) {
            File dir = new File(FabricLoader.getInstance().getConfigDirectory().toPath().toString());
            dir.mkdir();
            save();
        }

        return defaultConfig;
    }
}
