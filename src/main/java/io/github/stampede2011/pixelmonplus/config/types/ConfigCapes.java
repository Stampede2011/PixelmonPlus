package io.github.stampede2011.pixelmonplus.config.types;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class ConfigCapes {

    @Setting
    public Map<String, Cape> capes = new HashMap<>();

    @ConfigSerializable
    public static class Cape {

        @Setting
        public String textureId;

    }

}
