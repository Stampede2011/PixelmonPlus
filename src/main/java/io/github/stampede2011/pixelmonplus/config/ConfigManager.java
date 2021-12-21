package io.github.stampede2011.pixelmonplus.config;

import com.google.common.reflect.TypeToken;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigManager {

    private static final PixelmonPlus plugin = PixelmonPlus.getInstance();;

    private static ConfigPopups popupsConfig;
    private static ConfigCosmetics cosmeticsConfig;
    private static ConfigCapes capesConfig;

    public static void init() {
        PixelmonPlus.getLogger().info("Attempting to initialize the PixelmonPlus configuration files...");
        try {
            if(Files.notExists(Paths.get(plugin.dir + "/popups.conf")))
                PixelmonPlus.getContainer().getAsset("popups.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/cosmetics.conf")))
                PixelmonPlus.getContainer().getAsset("cosmetics.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/capes.conf")))
                PixelmonPlus.getContainer().getAsset("capes.conf").get().copyToDirectory(plugin.dir.toPath());

            load();

        } catch (IOException exception) {
            PixelmonPlus.getLogger().error("An error occurred while initializing the configuration files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }
    }

    public static void load() {
        try {
            popupsConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "popups.conf"))
                    .build().load().getValue(TypeToken.of(ConfigPopups.class));
            cosmeticsConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "cosmetics.conf"))
                    .build().load().getValue(TypeToken.of(ConfigCosmetics.class));
            capesConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "capes.conf"))
                    .build().load().getValue(TypeToken.of(ConfigCapes.class));
        } catch (IOException | ObjectMappingException exception){
            PixelmonPlus.getLogger().error("An error occurred while loading the configuration files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }

    }

    public static void reload() {
        init();
    }

    public static ConfigPopups getPopupsConfig() {
        return popupsConfig;
    }

    public static ConfigCosmetics getCosmeticsConfig() {
        return cosmeticsConfig;
    }

    public static ConfigCapes getCapesConfig() {
        return capesConfig;
    }

}
