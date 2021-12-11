package io.github.stampede2011.pixelmonplus.utils;

import com.pixelmongenerations.api.pokemon.PokemonSpec;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.Evolution;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.conditions.EvoCondition;
import com.pixelmongenerations.core.enums.EnumSpecies;

/**
 * Gets a custom instance of the Evolution class to be able to manually trigger custom evolutions.
 */
public class EvolutionGetter extends Evolution {

    public EvolutionGetter (EnumSpecies from, PokemonSpec to, EvoCondition... conditions) {

        super(from, to, conditions);

    }

}
