package io.github.stampede2011.pixelmonplus.listeners;

import com.pixelmongenerations.api.events.LevelUpEvent;
import com.pixelmongenerations.api.pokemon.PokemonSpec;
import com.pixelmongenerations.common.entity.pixelmon.stats.evolution.types.LevelingEvolution;
import com.pixelmongenerations.core.enums.EnumSpecies;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigFakemon;
import io.github.stampede2011.pixelmonplus.utils.EvolutionConditionGetter;
import io.github.stampede2011.pixelmonplus.utils.EvolutionGetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LevelUp {

    @SubscribeEvent
    public void onLevelUp(LevelUpEvent event) throws ObjectMappingException {
        PixelmonPlus.getLogger().error("! DEBUG: Attempting level up " + event.toString());

        String pokemon = event.getPokemon().getSpecies().getPokemonName();
        String level = event.getPokemon().getBaseStats().pixelmonName;
        String customTexture = event.getPokemon().getBaseStats().pixelmonName;

        for (Map.Entry<EnumSpecies, List<ConfigFakemon.Fakemon>> entry : ConfigManager.getFakemonCache().entrySet()) {

            PixelmonPlus.getLogger().info("! DEBUG: pokemon= " + pokemon + " enumSpecies=" + entry.getKey());

            if (entry.getKey().getPokemonName().equalsIgnoreCase(pokemon)) {

                String evolveInto = "Magikarp";
                if (!EnumSpecies.hasPokemon(evolveInto)) {

                    PixelmonPlus.getLogger().error("No Pokemon with name: " + evolveInto);
                    return;

                }
                PokemonSpec spec = PokemonSpec.from(evolveInto);
                spec.setForm(0);

                LevelingEvolution levelingEvolution = new LevelingEvolution(event.getPokemon().getSpecies(), spec, event.getLevel(), new EvolutionConditionGetter());

                Timer evoTimer = new Timer();
                evoTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getPokemon().getEntity().startEvolution(levelingEvolution);
                    }
                }, 500);

                return;
            }

        }


    }

}
