
package emojicon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.test2app.R;

import java.util.Locale;

public class PointView extends LinearLayout {

    private LayoutInflater mInflater;
    private Drawable mIndicatorCurrent = null;
    private Drawable mIndicatorOther = null;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        mIndicatorOther = getResources().getDrawable(R.drawable.orca_emoji_screen_indicator_other);
        mIndicatorOther.setBounds(0, 0, mIndicatorOther.getIntrinsicWidth(), mIndicatorOther.getIntrinsicHeight());
        mIndicatorCurrent = getResources().getDrawable(R.drawable.orca_emoji_screen_indicator_current);
        mIndicatorCurrent
                .setBounds(0, 0, mIndicatorCurrent.getIntrinsicWidth(), mIndicatorCurrent.getIntrinsicHeight());
        setDrawingCacheEnabled(true);
    }

    /**
     * get system layout direction
     *
     * @return
     */
    public static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }

    public void setCurrentPosition(int currentScreen) {

        int screenCount = getChildCount();
        if (currentScreen >= screenCount) {
            return;
        }

        for (int i = 0; i < getChildCount(); i++) {
            ImageView childView = (ImageView) getChildAt(i);
            if (childView == null) {
                continue;
            }
            if (i == currentScreen) {
                childView.setImageDrawable(mIndicatorCurrent);
            } else {
                childView.setImageDrawable(mIndicatorOther);
            }
        }
    }

    private void addPage() {
        final ImageView childView = (ImageView) mInflater.inflate(R.layout.emojicon_indicator_item, this, false);
        childView.setImageDrawable(mIndicatorOther);
        childView.getDrawable().setDither(false);
        addView(childView);
    }

    public void setCount(int pageCount, int currentPage) {
        removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            addPage();
        }
        setCurrentPosition(currentPage);
    }
}
