package io.github.stampede2011.pixelmonplus.commands;

import com.pixelmongenerations.core.Pixelmon;
import com.pixelmongenerations.core.network.packetHandlers.GenerationsPlaySound;
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

public class PlaySound implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<Player> player = args.getOne(Text.of("player"));
        Optional<String> all = args.getOne(Text.of("all"));

        String soundId = args.<String>getOne(Text.of("sound-id")).get();

        if (all.isPresent()) {
            Pixelmon.NETWORK.sendToAll(new GenerationsPlaySound(soundId + ".ogg"));
            src.sendMessage(Utilities.toText("&aSuccessfully played the sound &2" + soundId + " &ato all online players!"));
        } else if (player.isPresent()) {
            Pixelmon.NETWORK.sendTo(new GenerationsPlaySound(soundId + ".ogg"), (EntityPlayerMP) player.get());
            src.sendMessage(Utilities.toText("&aSuccessfully played &2" + player.get().getName() + " &athe sound &2" + soundId + "&a!"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("pixelmonplus.command.playsound.base")
                .executor(new PlaySound())
                .arguments(
                        GenericArguments.firstParsing(GenericArguments.literal(Text.of("all"),  "@a"), GenericArguments.player(Text.of("player"))),
                        GenericArguments.string(Text.of("sound-id"))
                )
                .build();
    }

}