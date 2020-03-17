package emojicon;

import android.content.Context;

public class EmojiLibrary {
    static boolean sLoaded = false;

    // init emoji library
    public static void init(Context context) {
        if (!sLoaded) {
            synchronized (EmojiLibrary.class) {
                if (!sLoaded) {
                    try {
                        EmojiconHandler.init(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        sLoaded = true;
                    }
                }
            }
        }
    }
}
