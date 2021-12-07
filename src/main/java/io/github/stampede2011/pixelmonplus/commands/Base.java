package io.github.stampede2011.pixelmonplus.commands;

import io.github.stampede2011.pixelmonplus.utils.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Base implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        List<Text> content = new ArrayList<>();

        if (src.hasPermission("pixelmonplus.command.reload.base"))
            content.add(Utilities.toText("&b/pp reload &8- &fReloads the plugin"));
        if (src.hasPermission("pixelmonplus.command.claimcosmetics.base"))
            content.add(Utilities.toText("&b/pp claimcosmetics &8- &fClaims all cosmetics you have permission for"));
        if (src.hasPermission("pixelmonplus.command.givecosmetic.base"))
            content.add(Utilities.toText("&b/pp givecosmetic &8- &fGive a cosmetic to a player"));
        if (src.hasPermission("pixelmonplus.command.popup.base"))
            content.add(Utilities.toText("&b/pp popup &8- &fSend a popup overlay to players"));
        if (src.hasPermission("pixelmonplus.command.setcape.base"))
            content.add(Utilities.toText("&b/pp setcape &8- &fSets a players cape texture"));
        if (src.hasPermission("pixelmonplus.command.setcosmetic.base"))
            content.add(Utilities.toText("&b/pp setcosmetic &8- &fEquip a specific cosmetic for a player"));

        if (content.isEmpty()) {
            content.add(Utilities.toText("&cYou do not have permission to use this command!"));
            return CommandResult.success();
        }

        PaginationList.builder()
                .title(Utilities.toText("&cPixelmon&fPlus"))
                .padding(Utilities.toText("&8&m-&r"))
                .contents(content)
                .linesPerPage(10)
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
                .child(SetCape.build(), "setcape")
                .build();
    }

}
