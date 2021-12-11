package io.github.stampede2011.pixelmonplus.config;

import com.google.common.reflect.TypeToken;
import com.pixelmongenerations.core.enums.EnumSpecies;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import io.github.stampede2011.pixelmonplus.config.types.ConfigFakemon;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final PixelmonPlus plugin = PixelmonPlus.getInstance();;

    private static ConfigPopups popupsConfig;
    private static ConfigCosmetics cosmeticsConfig;
    private static ConfigCapes capesConfig;
    private static ConfigFakemon fakemonConfig;

    private static Map<EnumSpecies, List<ConfigFakemon.Fakemon>> fakemonCache;

    public static void init() {
        PixelmonPlus.getLogger().info(String.valueOf(plugin.dir));
        try {
            if(Files.notExists(Paths.get(plugin.dir + "/popups.conf")))
                PixelmonPlus.getContainer().getAsset("popups.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/cosmetics.conf")))
                PixelmonPlus.getContainer().getAsset("cosmetics.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/capes.conf")))
                PixelmonPlus.getContainer().getAsset("capes.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/fakemon.conf")))
                PixelmonPlus.getContainer().getAsset("fakemon.conf").get().copyToDirectory(plugin.dir.toPath());

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
            fakemonConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "fakemon.conf"))
                    .build().load().getValue(TypeToken.of(ConfigFakemon.class));
        } catch (IOException | ObjectMappingException exception){
            PixelmonPlus.getLogger().error("An error occurred while loading the configuration files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }

        fakemonCache = new HashMap<>();

        if (fakemonConfig != null) {
            for (ConfigFakemon.Fakemon fakemon : fakemonConfig.fakemon) {
                PixelmonPlus.getLogger().info("! DEBUG: fakemon=" + fakemon);
                if (EnumSpecies.hasPokemonAnyCase(fakemon.pokemon)) {
                    PixelmonPlus.getLogger().info("! DEBUG: fakemon=" + fakemon + " pokemon=" + fakemon.pokemon);
                    EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(fakemon.pokemon);


                    if (!(fakemonCache.containsKey(pokemon))) {
                        PixelmonPlus.getLogger().info("! DEBUG: fakemon=" + fakemon + " pokemon=" + fakemon.pokemon + " containsKey=true");
                        fakemonCache.put(pokemon, new ArrayList<>());
                    }

                    fakemonCache.get(pokemon).add(fakemon);
                }
            }
        }

    }

    public static void reload() {
        load();
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

    public static ConfigFakemon getFakemonConfig() {
        return fakemonConfig;
    }

    public static Map<EnumSpecies, List<ConfigFakemon.Fakemon>> getFakemonCache() {
        return fakemonCache;
    }
}
