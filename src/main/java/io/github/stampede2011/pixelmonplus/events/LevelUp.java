package io.github.stampede2011.pixelmonplus.events;

import com.pixelmongenerations.api.events.EvolveEvent;
import com.pixelmongenerations.api.pokemon.PokemonSpec;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.Evolution;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.conditions.EvoCondition;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.types.LevelingEvolution;
import com.pixelmongenerations.common.entity.pixelmon.stats.links.PokemonLink;
import com.pixelmongenerations.common.pokedex.Pokedex;
import com.pixelmongenerations.core.Pixelmon;
import com.pixelmongenerations.core.enums.EnumSpecies;
import com.pixelmongenerations.core.network.packetHandlers.evolution.EvolutionStage;
import com.pixelmongenerations.core.network.packetHandlers.evolution.EvolvePokemon;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPokemon;
import io.github.stampede2011.pixelmonplus.utils.EvoConditionGetter;
import io.github.stampede2011.pixelmonplus.utils.EvolutionGetter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LevelUp {

    @SubscribeEvent
    public void onLevelUp(com.pixelmongenerations.api.events.LevelUpEvent event) {
        System.out.println("DEBUG: 1");
        int lvl = 16;
        if (lvl == event.getLevel()) {
            System.out.println("DEBUG: 3");

            EnumSpecies species = EnumSpecies.getFromDex(new Random().nextInt(Pokedex.pokedexSize)).get();
            System.out.println("DEBUG: 4");
            PokemonSpec to = PokemonSpec.from(species.getPokemonName());
            System.out.println("DEBUG: 5");
            EvoCondition condition = new EvoConditionGetter();
            System.out.println("DEBUG: 6");
            Evolution evo = new EvolutionGetter(EnumSpecies.getFromNameAnyCase(event.getPokemon().getEntity().getName()), to, condition);
            System.out.println("DEBUG: 7");
            //MinecraftForge.EVENT_BUS.post(new EvolveEvent.PreEvolve(event.getPlayer(), event.getPokemon().getEntity(), evo));
            event.getPokemon().getEntity().startEvolution(evo);
            System.out.println("DEBUG: 8");

        }

//        for (ConfigPokemon.Pokemon pokemon : ConfigManager.getPokemonConfig().pokemon) {
//
//
//            PokemonLink pokemonLink = event.getPokemon();
//
//            String pokemonName = pokemonLink.getSpecies().getPokemonName();
//            int pokemonForm = pokemonLink.getForm();
//            int pokemonLevel = event.getLevel();
//
//            PixelmonPlus.getLogger().info("DEBUG: pokemonName=" + pokemonName + " | fromPokemon=" + pokemon.fromPokemon);
//
//            if (!pokemonName.equalsIgnoreCase(pokemon.fromPokemon)) {
//                PixelmonPlus.getLogger().info("DEBUG: Pokemon " + pokemonName + " name != check");
//                return;
//            }
//
//            PixelmonPlus.getLogger().info("DEBUG: pokemonForm=" + pokemonForm + " | fromForm=" + pokemon.fromForm);
//
//            if (pokemonForm != pokemon.fromForm) {
//                PixelmonPlus.getLogger().info("DEBUG: Pokemon " + pokemonName + " form != check");
//                return;
//            }
//
//            PixelmonPlus.getLogger().info("DEBUG: pokemonLevel=" + pokemonLevel + " | level=" + pokemon.level);
//
//            if (pokemonLevel != pokemon.level) {
//                PixelmonPlus.getLogger().info("DEBUG: Pokemon " + pokemonName + " level != check");
//                return;
//            }
//
//            EnumSpecies species = EnumSpecies.getFromDex(new Random().nextInt(Pokedex.pokedexSize)).get();
//            PokemonSpec to = PokemonSpec.from(species.getPokemonName());
//            EvoCondition condition = new EvoConditionGetter();
//            Evolution evo = new EvolutionGetter(EnumSpecies.getFromNameAnyCase(event.getPokemon().getEntity().getName()), to, condition);
//            //MinecraftForge.EVENT_BUS.post(new EvolveEvent.PreEvolve(event.getPlayer(), event.getPokemon().getEntity(), evo));
//            event.getPokemon().getEntity().startEvolution(evo);

//            LevelingEvolution levelingEvolution = new LevelingEvolution(pokemonLink.getSpecies(), PokemonSpec.from(EnumSpecies.getFromNameAnyCase(pokemon.toPokemon)), pokemon.level);
//
//            PixelmonPlus.getLogger().info("DEBUG: levelingEvolution " + levelingEvolution);
//
//            levelingEvolution.setForm(pokemon.toForm);
//
//            pokemonLink.getEntity().startEvolution(levelingEvolution);
//
//        }

    }

}
