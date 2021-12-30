package io.github.stampede2011.pixelmonplus.listeners;

import com.pixelmongenerations.core.event.EntityPlayerExtension;
import io.github.stampede2011.pixelmonplus.config.ConfigManager;
import io.github.stampede2011.pixelmonplus.config.types.ConfigCapes;
import io.github.stampede2011.pixelmonplus.storage.StorageData;
import io.github.stampede2011.pixelmonplus.storage.StorageManager;
import io.github.stampede2011.pixelmonplus.utils.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnect {

    @Listener
    public void onConnection(ClientConnectionEvent.Join event, @Root Player player) {
        StorageData.UserData userData = StorageManager.getUserData(player.getUniqueId(), false);

        if (userData != null) {
            if (userData.cape != null && !userData.cape.equals("")) {
                ConfigCapes.Cape cape = ConfigManager.getCapesConfig().capes.get(userData.cape);

                if (cape != null)
                    EntityPlayerExtension.updatePlayerCape((EntityPlayerMP) player, cape.textureId);
            }
        }
    }

}
