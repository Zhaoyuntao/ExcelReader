package com.test.test2app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.text.EmojiCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.test.test2app.R;
import com.test.test2app.textview.ZTestTextView;
import com.zhaoyuntao.androidutils.tools.S;
import com.zhaoyuntao.androidutils.tools.thread.TP;
import com.zhaoyuntao.androidutils.tools.thread.ZRunnable;

import java.text.BreakIterator;

public class MainActivity_95_textview extends AppCompatActivity {

    ZTestTextView zTextView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_95_textview);
        editText=findViewById(R.id.edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                zTextView.setText(s, TextView.BufferType.SPANNABLE);
                S.s("s:"+s);
            }
        });
        String text="\uD83D\uDC4D\uD83C\uDFFF";
        zTextView = findViewById(R.id.text);
        zTextView.setText(text + text + text + text + text + text + text + text + text, TextView.BufferType.SPANNABLE);
//        zTextView.setText(text);
//        TP.init();
//        TP.runOnUiDelayedSafely(new ZRunnable(this) {
//            @Override
//            protected void todo() {
//                zTextView.show();
//            }
//        },1000);
    }


}
