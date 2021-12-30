package io.github.stampede2011.pixelmonplus.storage;

import com.google.common.reflect.TypeToken;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class StorageManager {

    private static final PixelmonPlus plugin = PixelmonPlus.getInstance();

    private static ConfigurationLoader<CommentedConfigurationNode> storageLoader;
    private static CommentedConfigurationNode storageNode;
    private static StorageData storageData;

    public static void init() {
        PixelmonPlus.getLogger().info("Attempting to initialize the PixelmonPlus storage files...");
        try {
            if(Files.notExists(Paths.get(plugin.dir + "/storage.conf")))
                PixelmonPlus.getContainer().getAsset("storage.conf").get().copyToDirectory(plugin.dir.toPath());

            load();

        } catch (IOException exception) {
            PixelmonPlus.getLogger().error("An error occurred while initializing the storage files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }
    }

    public static void load() {
        PixelmonPlus.getLogger().info("Attempting to load the PixelmonPlus storage files...");
        try {
            storageLoader = HoconConfigurationLoader.builder()
                    .setPath(Paths.get(plugin.dir + File.separator +  "storage.conf"))
                    .build();
            storageNode = storageLoader.load();
            storageData = storageNode.getValue(TypeToken.of(StorageData.class));
        } catch (IOException | ObjectMappingException exception){
            PixelmonPlus.getLogger().error("An error occurred while loading the storage files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }

    }

    public static void reload() {
        init();
    }

    public static void save() {
        try {
            storageNode.setValue(TypeToken.of(StorageData.class), storageData);
            storageLoader.save(storageNode);
        } catch (IOException | ObjectMappingException exception) {
            PixelmonPlus.getLogger().error("An error occurred while saving the storage files!");
            PixelmonPlus.getLogger().error(String.valueOf(exception));
        }
    }

    public static StorageData.UserData getUserData(UUID uuid, boolean create) {
        StorageData.UserData userData = storageData.userData.get(uuid);

        if (create && userData == null) {
            userData = new StorageData.UserData();
            storageData.userData.put(uuid, userData);
            save();
        }

        return userData;
    }

    public static void setCape(UUID uuid, String capeId) {
        StorageData.UserData userData = getUserData(uuid, true);
        userData.cape = capeId;

        save();
    }

    public static void removeCape(UUID uuid) {
        StorageData.UserData userData = getUserData(uuid, false);
        if (userData != null) {
            userData.cape = "";

            save();
        }
    }

}
