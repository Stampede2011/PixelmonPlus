package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.common.cosmetic.CosmeticCategory;
import com.pixelmongenerations.common.cosmetic.CosmeticEntry;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
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

public class SetCosmetic implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne(Text.of("player")).get();
        String cosmeticID = args.<String>getOne(Text.of("cosmetic-id")).get();

        if (ConfigManager.getCosmeticsConfig().cosmetics.containsKey(cosmeticID)) {

            ConfigCosmetics.Cosmetic cosmetic = ConfigManager.getCosmeticsConfig().cosmetics.get(cosmeticID);

            if (cosmetic != null) {
                PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).orElse(null);

                storage.cosmeticData.setCosmetic(CosmeticEntry.of(CosmeticCategory.valueOf(cosmetic.category), cosmetic.name, cosmetic.modelId, cosmetic.textureId));

                src.sendMessage(Utilities.toText("&aSet the requested Cosmetic!"));
            }

        } else {
            src.sendMessage(Utilities.toText("&cCould not find that Cosmetic!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.setcosmetic.base")
                .executor(new SetCosmetic())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Utilities.toText("player"))),
                        GenericArguments.withSuggestions(GenericArguments.string(Utilities.toText("cosmetic-id")), ConfigManager.getCosmeticsConfig().cosmetics.keySet())
                )
                .build();
    }

}