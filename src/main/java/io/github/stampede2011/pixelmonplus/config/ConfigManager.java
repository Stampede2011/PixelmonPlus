package io.github.stampede2011.pixelmonplus.config;

import com.google.common.reflect.TypeToken;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPokemon;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConfigManager {

    private static final PixelmonPlus plugin = PixelmonPlus.getInstance();;

    private static ConfigPopups popupsConfig;
    private static ConfigCosmetics cosmeticsConfig;
    private static ConfigCapes capesConfig;
    private static ConfigPokemon pokemonConfig;

    public static void init() {
        PixelmonPlus.getLogger().info(String.valueOf(plugin.dir));
        try {
            if(Files.notExists(Paths.get(plugin.dir + "/popups.conf")))
                PixelmonPlus.getContainer().getAsset("popups.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/cosmetics.conf")))
                PixelmonPlus.getContainer().getAsset("cosmetics.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/capes.conf")))
                PixelmonPlus.getContainer().getAsset("capes.conf").get().copyToDirectory(plugin.dir.toPath());

            if(Files.notExists(Paths.get(plugin.dir + "/pokemon.conf")))
                PixelmonPlus.getContainer().getAsset("pokemon.conf").get().copyToDirectory(plugin.dir.toPath());

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
                    .build()
                    .load()
                    .getValue(TypeToken.of(ConfigPopups.class));
            cosmeticsConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "cosmetics.conf"))
                    .build()
                    .load()
                    .getValue(TypeToken.of(ConfigCosmetics.class));
            capesConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "capes.conf"))
                    .build()
                    .load()
                    .getValue(TypeToken.of(ConfigCapes.class));
            pokemonConfig = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "pokemon.conf"))
                    .build()
                    .load()
                    .getValue(TypeToken.of(ConfigPokemon.class));
        } catch (IOException | ObjectMappingException exception){
            PixelmonPlus.getLogger().error("An error occurred while loading the configuration files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
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

    public static ConfigPokemon getPokemonConfig() {
        return pokemonConfig;
    }

}
