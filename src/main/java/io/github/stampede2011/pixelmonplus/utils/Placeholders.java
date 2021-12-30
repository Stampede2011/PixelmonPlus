package io.github.stampede2011.pixelmonplus.utils;

import com.pixelmongenerations.client.render.layers.PixelmonLayerCape;
import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.api.PixelmonPlusAPI;
import me.rojo8399.placeholderapi.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;

import javax.annotation.Nullable;

public class Placeholders {

    private static final PixelmonPlus plugin = PixelmonPlus.getInstance();
    private static PlaceholderService placeholderService;
    private static Placeholders INSTANCE;

    public static void init() {
        if (Sponge.getServiceManager().isRegistered(PlaceholderService.class)) {
            placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService.class);

            INSTANCE = new Placeholders();

            placeholderService.loadAll(INSTANCE, PixelmonPlus.getInstance())
                    .stream()
                    .map(builder -> builder
                            .tokens("cape")
                            .plugin(PixelmonPlus.getInstance())
                            .description(PixelmonPlus.DESCRIPTION)
                            .author(PixelmonPlus.AUTHORS)
                            .version(PixelmonPlus.VERSION))
                    .forEach(builder -> {
                        try {
                            builder.buildAndRegister();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

            PixelmonPlus.getLogger().error("PlaceholderAPI has been detected and integrated with PixelmonPlus!");
        }
    }

    @Placeholder(id = "pixelmonplus")
    public Object pixelmonplus(@Source User player, @Token(fix = true) @Nullable String token) throws NoValueException {
        if (token == null) {
            return "error: arguments required";
        }

        if (token.startsWith("cape")) {
            return PixelmonPlusAPI.getPlayerCape(player.getUniqueId());
        }
        throw new NoValueException();
    }

}
