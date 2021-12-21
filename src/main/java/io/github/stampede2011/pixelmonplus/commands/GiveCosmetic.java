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

public class GiveCosmetic implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne(Text.of("player")).get();
        String cosmeticID = args.<String>getOne(Text.of("cosmetic-id")).get();

        if (ConfigManager.getCosmeticsConfig().cosmetics.containsKey(cosmeticID)) {

            ConfigCosmetics.Cosmetic cosmetic = ConfigManager.getCosmeticsConfig().cosmetics.get(cosmeticID);

            if (cosmetic != null) {
                PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).orElse(null);

                if (storage.cosmeticData.addCosmetic(new CosmeticEntry(CosmeticCategory.valueOf(cosmetic.category), cosmetic.name, cosmetic.modelId, cosmetic.textureId))) {
                    src.sendMessage(Utilities.toText("&aSuccessfully gave &2" + player.getName() + " &athe cosmetic &4" + cosmeticID + "&c!"));
                } else {
                    src.sendMessage(Utilities.toText("&cError while attempting to give &4" + player.getName() + " &ccosmetic &4" + cosmeticID + "&c!"));
                }

            }

        } else {
            src.sendMessage(Utilities.toText("&cCould not find the &4" + cosmeticID + " &ccosmetic!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.givecosmetic.base")
                .executor(new GiveCosmetic())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Utilities.toText("player"))),
                        GenericArguments.withSuggestions(GenericArguments.string(Utilities.toText("cosmetic-id")), ConfigManager.getCosmeticsConfig().cosmetics.keySet())
                )
                .build();
    }

}