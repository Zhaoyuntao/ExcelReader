package com.test.test2app.textview;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zhaoyuntao.androidutils.tools.S;
import com.zhaoyuntao.androidutils.tools.TextMeasure;

import java.text.BreakIterator;

/**
 * created by zhaoyuntao
 * on 2020-04-07
 * description:
 */
public class ZTestTextView extends AppCompatTextView {

    public ZTestTextView(Context context) {
        super(context);
    }

    public ZTestTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZTestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Layout layout=getLayout();
//        S.s("lines:"+layout.getLineCount());
//        int start=layout.getLineStart(0);
//        int end=layout.getLineEnd(0);
//        S.s("start:"+start+" end:"+end);
//        S.s("measure:"+TextMeasure.measure(getText().toString(),getTextSize())[0]+" lineW:"+layout.getLineWidth(0)+" viewW:"+getMeasuredWidth());
//    }

//    public void show(){
//        Layout layout=getLayout();
//        S.s("lines:"+layout.getLineCount());
//        int start=layout.getLineStart(0);
//        int end=layout.getLineEnd(0);
//        S.s("start:"+start+" end:"+end);
//        S.s("measure:"+TextMeasure.measure(getText().toString(),getTextSize())[0]+" lineW:"+layout.getLineWidth(0)+" viewW:"+getMeasuredWidth());
//    }

    /**
     * version:2020-02-26
     *
     * @param source
     * @return
     */
    public static int getCharCount(String source, CharIterator charIterator) {
        BreakIterator breakIterator = BreakIterator.getCharacterInstance();
        breakIterator.setText(source);
        int start = breakIterator.first();
        int count = 0;
        for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {
            count++;
            if (charIterator != null) {
                String oneChar = source.substring(start, end);
                if (TextUtils.isEmpty(oneChar)) {
                    continue;
                }
                if (!charIterator.getCharString(source.substring(start, end), Character.codePointAt(source, start))) {
                    break;
                }
            }
        }
        return count;
    }

    public interface CharIterator {
        boolean getCharString(String item, int codePoint);
    }
}
