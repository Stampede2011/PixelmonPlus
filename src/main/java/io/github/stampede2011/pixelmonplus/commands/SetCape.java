package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.core.event.EntityPlayerExtension;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.storage.StorageData;
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

public class SetCape implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne(Text.of("player")).get();
        String capeID = args.<String>getOne(Text.of("cape-id")).get();

        if (ConfigManager.getCapesConfig().capes.containsKey(capeID)) {

            ConfigCapes.Cape cape = ConfigManager.getCapesConfig().capes.get(capeID);

            if (cape != null) {
                EntityPlayerExtension.updatePlayerCape((EntityPlayerMP) player, cape.textureId);
                StorageManager.setCape(player.getUniqueId(), capeID);

                src.sendMessage(Utilities.toText("&aSuccessfully set &2" + player.getName() + "'s &acape to &2" + capeID + "&a!"));
            }

        } else {
            src.sendMessage(Utilities.toText("&cCould not find the &4" + capeID + " &ccape!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.setcape.base")
                .executor(new SetCape())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Utilities.toText("player"))),
                        GenericArguments.withSuggestions(GenericArguments.string(Utilities.toText("cape-id")), ConfigManager.getCapesConfig().capes.keySet())
                )
                .build();
    }

}