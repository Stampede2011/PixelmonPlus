package io.github.stampede2011.pixelmonplus;

import com.google.inject.Inject;
import io.github.stampede2011.pixelmonplus.commands.Base;
import io.github.stampede2011.pixelmonplus.commands.ClaimCosmetics;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.utils.Utilities;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;

@Plugin(id = io.github.stampede2011.pixelmonplus.PixelmonPlus.ID,
        name = io.github.stampede2011.pixelmonplus.PixelmonPlus.NAME,
        authors = io.github.stampede2011.pixelmonplus.PixelmonPlus.AUTHORS,
        description = io.github.stampede2011.pixelmonplus.PixelmonPlus.DESCRIPTION,
        version = io.github.stampede2011.pixelmonplus.PixelmonPlus.VERSION,
        dependencies = {@Dependency(id = "pixelmon", optional = false)}
)
public class PixelmonPlus {

    public static final String ID = "pixelmonplus";
    public static final String NAME = "PixelmonPlus";
    public static final String AUTHORS = "Stampede2011";
    public static final String DESCRIPTION = "Adds additional content to Pixelmon Generations!";
    public static final String VERSION = "1.0.1";

    @Inject
    private Logger logger;

    private static PixelmonPlus instance;

    @Inject
    @ConfigDir(sharedRoot = false)
    public File dir;

    @Inject
    private PluginContainer container;

    @Listener
    public void onGamePreinit(GamePreInitializationEvent e) {
        instance = this;

        io.github.stampede2011.pixelmonplus.PixelmonPlus.getLogger().info(String.valueOf(dir));

        ConfigManager.init();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent e) {
        Sponge.getCommandManager().register(instance, Base.build(), "pixelmonplus", "pixelplus", "pp");
        Sponge.getCommandManager().register(instance, ClaimCosmetics.build(), "claimcosmetics", "claimcos");

        Sponge.getServer().getConsole().sendMessage(Utilities.toText("\n"
                + "&c  ____  _          _                      &f ____  _ \n"
                + "&c |  _ \\(_)_  _____| |_ __ ___   ___  _ __ &f|  _ \\| |_   _ ___ \n"
                + "&c | |_) | \\ \\/ / _ \\ | '_ ` _ \\ / _ \\| '_ \\&f| |_) | | | | / __| \n"
                + "&c |  __/| |>  <  __/ | | | | | | (_) | | | &f|  __/| | |_| \\__ \\ \n"
                + "&c |_|   |_/_/\\_\\___|_|_| |_| |_|\\___/|_| |_&f|_|   |_|\\__,_|___/ \n"
                + "\n"
                + "&b" + io.github.stampede2011.pixelmonplus.PixelmonPlus.NAME + " " + io.github.stampede2011.pixelmonplus.PixelmonPlus.VERSION + " has been enabled!\n"));
    }

    @Listener
    public void onReload(GameReloadEvent e) {
        ConfigManager.reload();

        io.github.stampede2011.pixelmonplus.PixelmonPlus.getLogger().info("PixelmonPlus has been successfully reloaded!");
    }

    public static PixelmonPlus getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return instance.logger;
    }

    public static PluginContainer getContainer() {
        return instance.container;
    }

}
