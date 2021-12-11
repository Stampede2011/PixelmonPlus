package io.github.stampede2011.pixelmonplus.utils;

import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.conditions.EvoCondition;

/**
 * Gets apparently a custom instance of the EvoCondition class to pass into evolutions to force set condition to true to allow the evolution to happen
 */
public class EvolutionConditionGetter extends EvoCondition {

    @Override
    public boolean passes (EntityPixelmon entityPixelmon) {

        return true;

    }

}