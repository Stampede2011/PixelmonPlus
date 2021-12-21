package io.github.stampede2011.pixelmonplus.config.types;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class ConfigCosmetics {

    @Setting
    public Map<String, Cosmetic> cosmetics = new HashMap<>();

    @ConfigSerializable
    public static class Cosmetic {

        @Setting
        public String modelId;

        @Setting
        public String name;

        @Setting
        public String textureId;

        @Setting
        public String category;

        @Setting
        public boolean claimable;

    }

}
