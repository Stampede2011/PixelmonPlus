package io.github.stampede2011.pixelmonplus.api;

import io.github.stampede2011.pixelmonplus.storage.StorageData;
import io.github.stampede2011.pixelmonplus.storage.StorageManager;

import java.util.UUID;

public class PixelmonPlusAPI {

    // Get the player's current active cape
    public static String getPlayerCape(UUID uuid) {
        StorageData.UserData userData = StorageManager.getUserData(uuid, false);
        return (userData != null) ? userData.cape : null;
    }

}
