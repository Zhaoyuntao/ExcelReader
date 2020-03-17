/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emojicon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;


import com.test.test2app.R;
import com.test.test2app.utils.RTLUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com).
 */
public class EmojiconTextView extends AppCompatTextView {

    static final int DEFAULT_MAX_EMOJICON_MATCH_LENGTH = 2560;
    static final int DEFAULT_MAX_AUTO_LINK_MATCH_LENGTH = 3000;

    private int mEmojiconSize;
    private boolean rtlSupport;
    private OnLayoutListener mOnLayoutListener;

    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;
    private UrlSpanClickListener mUrlSpanClickListener;
    private ProcessColorSpanCallback mColorSpanCallback = null;

    private int mMaxEmojiconMatchLength = DEFAULT_MAX_EMOJICON_MATCH_LENGTH;
    private int mMaxAutoLinkMatchLength = DEFAULT_MAX_AUTO_LINK_MATCH_LENGTH;
    private int mMaxTextLength = -1;

    private long mTouchDownloadTime;
    private long CLICK_GAP = ViewConfiguration.getLongPressTimeout();

    private List<Pair<Integer, Integer>> mList;

    public void setHighLight(List<Pair<Integer, Integer>> list) {
        mList = list;
    }

    public static interface OnLayoutListener {
        void onLayouted(EmojiconTextView view, boolean changed, int left, int top, int right, int bottom);
    }

    public interface UrlSpanClickListener {
        void onClick(View view, String url, String content);
    }

    public boolean isRtlSupport() {
        return rtlSupport;
    }

    public void setRtlSupport(boolean rtlSupport) {
        this.rtlSupport = rtlSupport;
    }

    private static class UrlClickableSpan extends ClickableSpan {

        private String mUrl;
        private String mContent;
        UrlSpanClickListener mClickListener;

        UrlClickableSpan(String url, String content, UrlSpanClickListener listener) {
            mUrl = url;
            mContent = content;
            mClickListener = listener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            int color = Color.argb(255, 0, 94, 222);
            ds.setColor(color);
            ds.linkColor = color;
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }

        @Override
        public void onClick(View widget) {
            if (mClickListener != null) {
                mClickListener.onClick(widget, mUrl, mContent);
            }
        }
    }

    public interface ProcessColorSpanCallback {
        void processColorSpan(SpannableStringBuilder builder);
    }

    public EmojiconTextView(Context context) {
        super(context);
        init(null);
    }

    public EmojiconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mEmojiconSize = (int) getTextSize();
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiconTextView);
            mEmojiconSize = (int) a.getDimension(R.styleable.EmojiconTextView_emojiconSize, getTextSize());
            rtlSupport = a.getBoolean(R.styleable.EmojiconTextView_rtlSupport, false);
            a.recycle();
        }
        setText(getText());
    }

    public void setMaxTextLength(int length) {
        mMaxTextLength = length;
    }

    public void setMaxEmojiconMatchLength(int length) {
        mMaxEmojiconMatchLength = length;
    }

    public void setMaxAutoLinkMatchLength(int length) {
        mMaxAutoLinkMatchLength = length;
    }

    /**
     * 这个地方的源码对不上最后版本，需要自己找。
     *
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (text == null || text.length() == 0) {
            super.setText(text, type);
            return;
        }

        //当发现文字是 SpannableStringBuilder 后直接调用父类方法并返回
        if (mMaxTextLength >= 0 && text.length() > mMaxTextLength) {
            text = text.subSequence(0, mMaxTextLength);
        }

        CharSequence emojiconText = text;
        CharSequence reservedText = null;
        if (text.length() > mMaxEmojiconMatchLength) {
            emojiconText = text.subSequence(0, mMaxEmojiconMatchLength);
            reservedText = text.subSequence(mMaxEmojiconMatchLength, text.length());
        }
        SpannableStringBuilder ssb = parseEmojicon(emojiconText);
        if (reservedText != null) {
            ssb.append(reservedText);
        }
        final int autoLinkMask = getAutoLinkMask();
        final boolean linkMaskChanged = autoLinkMask != 0 && ssb != null && ssb.length() > mMaxAutoLinkMatchLength;
        // Log.e("EmojiconTextView", "auto link mask : " + autoLinkMask + " ,length : " + ssb.length() + " ,linkMaskChanged : " + linkMaskChanged);
        // 文字太长了，使用自动正则识别link会造成anr，限制一下
        // 看看这个bug吧，同学们！ https://bugs.eclipse.org/bugs/show_bug.cgi?id=342269
        if (linkMaskChanged) {
            setAutoLinkMask(0);
        }

        //搜索高亮显示
        SpannableStringBuilder spannableString = new SpannableStringBuilder(ssb);
        if (mList != null && !mList.isEmpty()) {
            int color = getContext().getApplicationContext().getResources().getColor(R.color.yc_color_FFFA00);
            spannableString.setSpan(new ForegroundColorSpan(getContext().getApplicationContext().getResources().getColor(R.color.yc_color_000000_CB)),
                    0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            for (int i = 0; i < mList.size(); i++) {
                int indexStart = mList.get(i).first;
                int indexEnd = mList.get(i).second;
                spannableString.setSpan(new BackgroundColorSpan(color), indexStart, indexEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        super.setText(spannableString, type);

        // TODO Optimize this codes.
        attachSpanClick(type);

        if (linkMaskChanged) {
            setAutoLinkMask(autoLinkMask);
        }
        rtlSupport(text);
    }

    /**
     * 左右转换支持
     *
     * @param text 输入的文本
     */
    private void rtlSupport(CharSequence text) {
        if (rtlSupport) {
            setTextDirection(RTLUtils.getStringDirection(getContext(),text) == RTLUtils.RTL ? TEXT_DIRECTION_RTL : TEXT_DIRECTION_LTR);
        }
    }

    private SpannableStringBuilder parseEmojicon(CharSequence text) {
        CharSequence newText = EmojiconHandler.changeEmojis(getContext(), text);
        SpannableStringBuilder builder = new SpannableStringBuilder(newText);
        EmojiconHandler.addEmojis(getContext(), builder, mEmojiconSize);
        if (mColorSpanCallback != null) {
            mColorSpanCallback.processColorSpan(builder);
        }
        return builder;
    }

    private boolean attachSpanClick(TextView.BufferType type) {
        if (mUrlSpanClickListener != null) {
            CharSequence content = getText();
            if (content instanceof Spannable) {
                SpannableStringBuilder sp = new SpannableStringBuilder(content);

                URLSpan[] urls = sp.getSpans(0, sp.length(), URLSpan.class);
                // sp.clearSpans();//should clear old spans
                for (URLSpan url : urls) {
                    CharSequence cs = "";
                    if (sp.getSpanStart(url) < sp.getSpanEnd(url)) {
                        cs = sp.subSequence(sp.getSpanStart(url), sp.getSpanEnd(url));
                        UrlClickableSpan myURLSpan = new UrlClickableSpan(url.getURL(), cs.toString(), mUrlSpanClickListener);
                        sp.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                String contentStr = content.toString();
                List<String> list = findToTokURL(contentStr);
                if (list != null) {
                    for (String temp : list) {
                        int index = contentStr.indexOf(temp);
                        UrlClickableSpan urlSpan = new UrlClickableSpan(temp, temp, mUrlSpanClickListener);
                        sp.setSpan(urlSpan, index, index + temp.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                }
                super.setText(sp, type);
                return true;
            }
        }
        return false;
    }

    public List<String> findToTokURL(String content) {
        List<String> termList = new ArrayList<String>();

        String patternString = "[totok]+[://]+[ui/]+[0-9A-Za-z:/[-]_#[?][=][.][&][%]]*";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            termList.add(matcher.group());
        }

        return termList;
    }

    /**
     * Set the size of emojicon in pixels.
     */
    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
    }

    public void setOnLayoutListener(OnLayoutListener listener) {
        mOnLayoutListener = listener;
    }

    public void setUrlSpanClickListener(UrlSpanClickListener listener) {
        mUrlSpanClickListener = listener;
    }

    public void setColorSpanCallback(ProcessColorSpanCallback callback) {
        mColorSpanCallback = callback;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mOnLayoutListener != null) {
            mOnLayoutListener.onLayouted(this, changed, left, top, right, bottom);
        }
    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;

        private long mTouchDownloadTime;
        private static final long CLICK_GAP = ViewConfiguration.getLongPressTimeout();

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new LocalLinkMovementMethod();

            return sInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget,
                                    Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(
                        off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        if (SystemClock.uptimeMillis() - mTouchDownloadTime < CLICK_GAP) {
                            link[0].onClick(widget);
                        }
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                        mTouchDownloadTime = SystemClock.uptimeMillis();
                    }

                    if (widget instanceof EmojiconTextView) {
                        ((EmojiconTextView) widget).linkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            if (SystemClock.uptimeMillis() - mTouchDownloadTime > CLICK_GAP) {
                return true;
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchDownloadTime = SystemClock.uptimeMillis();
        }
        return super.onTouchEvent(event);
    }
}
