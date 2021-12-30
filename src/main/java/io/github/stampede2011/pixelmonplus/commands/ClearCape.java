package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.core.event.EntityPlayerExtension;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.storage.StorageManager;
import io.github.stampede2011.pixelmonplus.utils.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class ClearCape implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne(Text.of("player")).get();

        EntityPlayerExtension.updatePlayerCape((EntityPlayerMP) player, "");
        StorageManager.removeCape(player.getUniqueId());

        src.sendMessage(Utilities.toText("&aSuccessfully cleared &2" + player.getName() + "'s &acape!"));

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.clearcape.base")
                .executor(new ClearCape())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Utilities.toText("player")))
                )
                .build();
    }

}