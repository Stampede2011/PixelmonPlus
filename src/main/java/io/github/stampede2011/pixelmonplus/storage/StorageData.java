package io.github.stampede2011.pixelmonplus.storage;

import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class StorageData {

    @Setting
    public Map<UUID, UserData> userData = new HashMap<>();

    @ConfigSerializable
    public static class UserData {

        @Setting
        public String cape;

    }
}
