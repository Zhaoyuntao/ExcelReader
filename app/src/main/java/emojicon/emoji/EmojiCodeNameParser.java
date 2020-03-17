
package emojicon.emoji;

import android.content.Context;

import com.test.test2app.R;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class EmojiCodeNameParser {

    public static class EmojiCodeName {
        public int mCodeHex;
        public String mName;
    }

    public static List<EmojiCodeName> parseAllEmojis(Context context) {
        LinkedList<EmojiCodeName> list = new LinkedList<EmojiCodeName>();
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(R.raw.all_emojis);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String[] splitedItem = new String[2];
            while ((line = br.readLine()) != null) {
                int pos = line.indexOf('|');
                if (pos < 0 || pos >= line.length()) {
                    continue;
                }

                int pos2 = line.indexOf('|', pos + 1);
                if (pos2 >= 0) {
                    continue; // multiple
                }

                splitedItem[0] = line.substring(0, pos);
                splitedItem[1] = line.substring(pos + 1);

                // Log.i("YC", "src=[" + line + "] - [" + splitedItem[0] + "] - [" + splitedItem[1] + "]");

                if (splitedItem != null && splitedItem.length == 2) {
                    EmojiCodeName codeName = new EmojiCodeName();
                    codeName.mCodeHex = Integer.parseInt(splitedItem[0], 16);
                    codeName.mName = splitedItem[1];
                    list.add(codeName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            silentlyClose(is);
            silentlyClose(br);
        }
        return list;
    }

    public static List<String> parseSelectedEmojis(Context context) {
        LinkedList<String> list = new LinkedList<String>();
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(R.raw.select_emojis);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            silentlyClose(is);
            silentlyClose(br);
        }
        return list;
    }

    private static void silentlyClose(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
