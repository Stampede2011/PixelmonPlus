package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.common.cosmetic.CosmeticCategory;
import com.pixelmongenerations.common.cosmetic.CosmeticEntry;
import com.pixelmongenerations.common.cosmetic.EnumCosmetic;
import com.pixelmongenerations.core.Pixelmon;
import com.pixelmongenerations.core.network.packetHandlers.customOverlays.EnqueuePopup;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCosmetics;
import io.github.stampede2011.pixelmonplus.config.types.ConfigPopups;
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
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.Map;

public class ClaimCosmetics implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (src instanceof Player) {
            PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) src).orElse(null);

            int claimCount = 0;
            for (EnumCosmetic cosmetic : EnumCosmetic.values()) {
                if (!cosmetic.isExclusive() && src.hasPermission("pixelmonplus.cosmetic." + cosmetic.getName().toLowerCase())) {
                    if (storage.cosmeticData.addCosmetic(CosmeticEntry.of(cosmetic))) {
                        claimCount++;
                    }
                }
            }

            for (Map.Entry<String, ConfigCosmetics.Cosmetic> entry : ConfigManager.getCosmeticsConfig().cosmetics.entrySet()) {
                if (entry.getValue().claimable) {
                    if (src.hasPermission("pixelmonplus.cosmetic." + entry.getKey())) {
                        if (storage.cosmeticData.addCosmetic(new CosmeticEntry(CosmeticCategory.valueOf(entry.getValue().category), entry.getValue().name, entry.getValue().modelId, entry.getValue().textureId))) {
                            claimCount++;
                        }
                    }
                }
            }

            if (claimCount > 0) {
                src.sendMessage(Utilities.toText("&aYou claimed &4" + claimCount + " &anew cosmetics!"));
            } else {
                src.sendMessage(Utilities.toText("&cNo new cosmetics have been claimed"));
            }
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.claimcosmetics.base")
                .executor(new ClaimCosmetics())
                .build();
    }

}