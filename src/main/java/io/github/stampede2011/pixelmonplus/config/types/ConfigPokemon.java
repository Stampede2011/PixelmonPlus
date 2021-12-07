package io.github.stampede2011.pixelmonplus.config.types;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class ConfigPokemon {

    @Setting
    public List<Pokemon> pokemon = new ArrayList<>();

    @ConfigSerializable
    public static class Pokemon {

        @Setting
        public String fromPokemon;

        @Setting
        public int fromForm;

        @Setting
        public int level;

        @Setting
        public String toPokemon;

        @Setting
        public int toForm;

    }

}
