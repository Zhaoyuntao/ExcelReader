package com.test.test2app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.test.test2app.BaseActivity;
import com.test.test2app.R;
import com.test.test2app.textview_spannable.BlueTextView;
import com.zhaoyuntao.androidutils.tools.S;

public class MainActivity_97_textview_spannable extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_textview_spannable);

        BlueTextView textView=findViewById(R.id.textview);
        textView.setUrlSpanClickListener(new BlueTextView.UrlSpanClickListener() {
            @Override
            public void onClick(View view, String url, String content) {
                Toast.makeText(activity(),"hello",Toast.LENGTH_SHORT).show();
            }
        });
        textView.setText("123www.baidu.com123" +
                "\uD83E\uDD7A\uD83D\uDC69\u200D❤️\u200D\uD83D\uDC8B\u200D\uD83D\uDC68 " +
                "www.google.com182" +
                "10597531"+
                "http:www.baidu.com"+
                "http://www.baidu.com");
////        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        TextView useInfo = (TextView) findViewById(R.id.textview);
//        String url_0_text = "用户协议及隐私条款";
//        useInfo.setText("开始即表示您同意遵守");
//
//        SpannableString spStr = new SpannableString(url_0_text);
//
//        spStr.setSpan(new ClickableSpan() {
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setColor(Color.WHITE);       //设置文件颜色
//                ds.setUnderlineText(true);      //设置下划线
//            }
//
//            @Override
//            public void onClick(View widget) {
//                S.s("onTextClick........");
//            }
//        }, 0, url_0_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        useInfo.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
//        useInfo.append(spStr);
//        useInfo.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }
}