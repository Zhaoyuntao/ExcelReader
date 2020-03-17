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
import android.graphics.Paint;
import android.text.BoringLayout;
import android.text.Layout;
import android.util.AttributeSet;

import com.zhaoyuntao.androidutils.tools.B;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com).
 */
public class EmojiconTextWithTimeStampView extends EmojiconTextView {

    private boolean isSelf;

    public static final BoringLayout.Metrics UNKNOWN_BORING = new BoringLayout.Metrics();

    public EmojiconTextWithTimeStampView(Context context) {
        super(context);
    }

    public EmojiconTextWithTimeStampView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiconTextWithTimeStampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Layout layout = getLayout();
        float lastLength = layout.getLineWidth(getLineCount() - 1);
        Paint paint = new Paint();
        paint.setTextSize(B.sp2px(getContext(), 12));
        float timeLength = (isSelf ? paint.measureText("12:45 下午 xxx") : paint.measureText("12:45 下午")) + B.dip2px(getContext(), 5);
        int maxLen = getMaxWidth();
        int lineHeight = getLineHeight();
        int textDirection = getTextDirection();
        int lineCount = getLineCount();
        if (lastLength + timeLength >= maxLen || (lineCount != 1 && textDirection == TEXT_DIRECTION_RTL)) {
            measuredHeight += lineHeight;
        } else if (measuredWidth + timeLength < maxLen && lastLength + timeLength > measuredWidth) {
            measuredWidth += lastLength + timeLength - measuredWidth;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }
}
