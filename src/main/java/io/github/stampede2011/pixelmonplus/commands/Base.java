package io.github.stampede2011.pixelmonplus.commands;

import io.github.stampede2011.pixelmonplus.utils.Utilities;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.ArrayList;
import java.util.List;

public class Base implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        List<Text> content = new ArrayList<>();

        content.add(Utilities.toText("&c/&fpp reload").toBuilder().onClick(TextActions.suggestCommand("/pp reload")).build());
        content.add(Utilities.toText("&c/&fpp claimcosmetics").toBuilder().onClick(TextActions.suggestCommand("/pp claimcosmetics")).build());
        content.add(Utilities.toText("&c/&fpp givecosmetic <player> <cosmetic-id>").toBuilder().onClick(TextActions.suggestCommand("/pp givecosmetic")).build());
        content.add(Utilities.toText("&c/&fpp setcosmetic <player> <cosmetic-id>").toBuilder().onClick(TextActions.suggestCommand("/pp setcosmetic")).build());
        content.add(Utilities.toText("&c/&fpp clearcosmetic <player> <category>").toBuilder().onClick(TextActions.suggestCommand("/pp clearcosmetic")).build());
        content.add(Utilities.toText("&c/&fpp popup <player|@a> <popup-id> [message]").toBuilder().onClick(TextActions.suggestCommand("/pp popup")).build());
        content.add(Utilities.toText("&c/&fpp setcape <player> <cape-id>").toBuilder().onClick(TextActions.suggestCommand("/pp setcape")).build());
        content.add(Utilities.toText("&c/&fpp clearcape <player>").toBuilder().onClick(TextActions.suggestCommand("/pp clearcape")).build());
        content.add(Utilities.toText("&c/&fpp playsound <player|@a> <sound-id>").toBuilder().onClick(TextActions.suggestCommand("/pp playsound")).build());

        PaginationList.builder()
                .title(Utilities.toText("&cPixelmon&fPlus"))
                .padding(Utilities.toText("&8&m-&r"))
                .contents(content)
                .linesPerPage(11)
                .sendTo(src);

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.base")
                .executor(new Base())
                .child(Reload.build(), "reload")
                .child(Popup.build(), "popup")
                .child(GiveCosmetic.build(), "givecosmetic", "givecos")
                .child(SetCosmetic.build(), "setcosmetic", "setcos")
                .child(ClaimCosmetics.build(), "claimcosmetics", "claimcos")
                .child(ClearCosmetic.build(), "clearcosmetics", "clearcos")
                .child(SetCape.build(), "setcape")
                .child(ClearCape.build(), "clearcape")
                .child(PlaySound.build(), "playsound")
                .build();
    }

}
