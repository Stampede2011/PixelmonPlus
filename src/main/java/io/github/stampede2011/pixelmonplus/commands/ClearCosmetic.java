package io.github.stampede2011.pixelmonplus.commands;

import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClearCosmetic implements CommandExecutor {

    private static final List<String> CATEGORIES = Stream.of(CosmeticCategory.values()).map(CosmeticCategory::name).map(String::toLowerCase).collect(Collectors.toList());

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player player = args.<Player>getOne(Text.of("player")).get();
        Optional<String> all = args.getOne(Text.of("all"));
        Optional<String> category = args.<String>getOne(Text.of("category"));

        PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).orElse(null);

        if (all.isPresent()) {
            for (CosmeticCategory iter : CosmeticCategory.values())
                storage.cosmeticData.removeCosmetic(iter);

            src.sendMessage(Utilities.toText("&aSuccessfully cleared all of &2" + player.getName() + "'s &aactive cosmetics!"));
        } else if (CATEGORIES.contains(category.get().toLowerCase())) {
            storage.cosmeticData.removeCosmetic(CosmeticCategory.getFromIndexSafe(CATEGORIES.indexOf(category.get().toLowerCase())));
            src.sendMessage(Utilities.toText("&aSuccessfully cleared &2" + player.getName() + "'s " + category.get() + " &acosmetic!"));
        } else {
            src.sendMessage(Utilities.toText("&cCould not find the category &4" + category + "&c!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.clearcosmetic.base")
                .executor(new ClearCosmetic())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Utilities.toText("player"))),
                        GenericArguments.firstParsing(GenericArguments.literal(Text.of("all"),  "all"), GenericArguments.withSuggestions(GenericArguments.string(Utilities.toText("category")), CATEGORIES))
                )
                .build();
    }

}