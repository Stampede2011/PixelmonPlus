package io.github.stampede2011.pixelmonplus.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Utilities {

    public static Text toText(String msg) {
        return TextSerializers.FORMATTING_CODE.deserialize(msg);
    }

    public static String fromText(Text msg) {
        return TextSerializers.FORMATTING_CODE.serialize(msg);
    }

}
