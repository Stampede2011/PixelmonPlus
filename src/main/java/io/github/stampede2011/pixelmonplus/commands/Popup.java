package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.core.Pixelmon;
import com.pixelmongenerations.core.network.packetHandlers.customOverlays.EnqueuePopup;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
import io.github.stampede2011.pixelmonplus.utils.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class Popup implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<Player> player = args.getOne(Text.of("player"));
        Optional<String> all = args.getOne(Text.of("all"));

        String popupID = args.<String>getOne(Text.of("popup-id")).get();
        Optional<String> message = args.getOne(Text.of("message"));

        if (ConfigManager.getPopupsConfig().popups.containsKey(popupID)) {

            ConfigPopups.Popup popup = ConfigManager.getPopupsConfig().popups.get(popupID);

            if (popup != null) {

                EnqueuePopup enqueuePopup = new EnqueuePopup(new com.pixelmongenerations.api.ui.Popup(
                        popup.textureId,
                        popup.width,
                        popup.height,
                        message.orElse(popup.text).replace("&", "\u00a7"),
                        popup.textColor,
                        popup.textOutline,
                        popup.textOffsetX,
                        popup.textOffsetY
                ));

                if (all.isPresent()) {
                    Pixelmon.NETWORK.sendToAll(enqueuePopup);
                    src.sendMessage(Utilities.toText("&aSuccessfully sent the popup &2" + popupID + " &ato all online players!"));
                } else if (player.isPresent()) {
                    Pixelmon.NETWORK.sendTo(enqueuePopup, (EntityPlayerMP) player.get());
                    src.sendMessage(Utilities.toText("&aSuccessfully sent &2" + player.get().getName() + " &athe popup &2" + popupID + "&a!"));
                } else {
                    src.sendMessage(Utilities.toText("&cError while attempting to send the popup &4" + popupID + "&c!"));
                }

            }

        } else {
            src.sendMessage(Utilities.toText("&cCould not find the &4" + popupID + " &cpopup!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.popup.base")
                .executor(new Popup())
                .arguments(
                        GenericArguments.firstParsing(GenericArguments.literal(Text.of("all"),  "@a"), GenericArguments.player(Text.of("player"))),
                        GenericArguments.withSuggestions(GenericArguments.string(Text.of("popup-id")), ConfigManager.getPopupsConfig().popups.keySet()),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("message")))
                )
                .build();
    }

}