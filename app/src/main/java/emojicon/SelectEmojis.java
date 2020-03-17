
package emojicon;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import emojicon.emoji.EmojiCodeNameParser;
import emojicon.emoji.Emojicon;

public class SelectEmojis {

    private static final List<Emojicon> sData = new LinkedList<Emojicon>();
    private static boolean sInited = false;

    private static void init(Context context) {
        if (sInited) {
            return;
        }
        sInited = true;
        List<String> selectedEmojis = EmojiCodeNameParser.parseSelectedEmojis(context);
        if (selectedEmojis == null || selectedEmojis.isEmpty()) {
            return;
        }
        int size = selectedEmojis.size();
        for (int i = 0; i < size; i++) {
            String emojiName = selectedEmojis.get(i);
            Emojicon emojicon = Emojicon.fromName(emojiName);
            if (emojicon != null) {
                sData.add(emojicon);
            }
        }
    }

    public synchronized static Emojicon[] getData(Context context) {
        init(context);
        return sData.toArray(new Emojicon[0]);
    }
}
