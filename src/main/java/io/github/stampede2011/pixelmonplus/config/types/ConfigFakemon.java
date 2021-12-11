package io.github.stampede2011.pixelmonplus.config.types;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.item.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFakemon {

    @Setting
    public List<Fakemon> fakemon = new ArrayList<>();

    @ConfigSerializable
    public static class Fakemon {

        @Setting
        public String pokemon;

        @Setting
        public int form;

        @Setting
        public String customTexture;

        @Setting
        public Evolution evolution;

        @ConfigSerializable
        public static class Evolution {

            @Setting
            public int form;

            @Setting
            public String customTexture;

            @Setting
            public int level;

            @Setting
            public ItemType heldItem;

            @Setting
            public String biome;

            @Setting
            public String time;

        }

    }
}
