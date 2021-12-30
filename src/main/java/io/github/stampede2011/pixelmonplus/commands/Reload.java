package io.github.stampede2011.pixelmonplus.commands;

import io.github.stampede2011.pixelmonplus.PixelmonPlus;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.storage.StorageManager;
import io.github.stampede2011.pixelmonplus.utils.Utilities;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public class Reload implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        ConfigManager.reload();
        StorageManager.reload();

        PixelmonPlus.getLogger().info("PixelmonPlus has been successfully reloaded!");
        src.sendMessage(Utilities.toText("&aPixelmonPlus has been successfully reloaded!"));

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.reload.base")
                .executor(new Reload())
                .build();
    }

}