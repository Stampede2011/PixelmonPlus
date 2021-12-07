package io.github.stampede2011.pixelmonplus.config.types;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class ConfigPopups {

    @Setting
    public Map<String, Popup> popups = new HashMap<>();

    @ConfigSerializable
    public static class Popup {

        @Setting
        public String textureId;

        @Setting
        public int width;

        @Setting
        public int height;

        @Setting
        public String text;

        @Setting
        public int textColor;

        @Setting
        public boolean textOutline;

        @Setting
        public int textOffsetX;

        @Setting
        public int textOffsetY;

    }

}
