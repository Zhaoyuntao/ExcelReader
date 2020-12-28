package com.test.test2app.faceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.test.test2app.R;
import com.test.test2app.fastrecordviewnew.UiUtils;
import com.test.test2app.utils.StringUtils;
import com.zhaoyuntao.androidutils.tools.S;

import pl.droidsonroids.gif.GifImageView;

/**
 * created by zhaoyuntao
 * on 2020/7/13
 * description:
 */
public class BlueFaceView extends GifImageView {

    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    private Path path;
    private int originWidth;
    private int originHeight;
    private Drawable defaultFace;
    private Drawable drawableLast;
    private int defaultFaceId;
    private String defaultFirstName;
    private String defaultLastName;

    private String firstName;
    private String lastName;
    private float radiusRoundConner;
    private boolean isGroup;
    private RectF rectF;
    private float percent;
    private float percentText;
    private int colorBack;

    //Status icon.
    private Drawable statusDrawable;

    private boolean hideStatus;
    private RectF rectFStatus;
    private Path pathStatus;
    private int widthOfStatus;
    private int heightOfStatus;
    private float radiusStatusRoundConner;
    private int xOffset;
    private int yOffset;
    private float[] radiusArray;

    private boolean useRoundBreach;
    private Paint paint;

    public BlueFaceView(Context context) {
        super(context);
        init(null);
    }

    public BlueFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BlueFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setScaleType(ScaleType.CENTER_CROP);
        rectF = new RectF();
        rectFStatus = new RectF();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BlueFaceView);
            radiusRoundConner = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_radius, 0);
            widthOfStatus = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_widthOfStatus, 0);
            heightOfStatus = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_heightOfStatus, 0);
            xOffset = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_offsetX, 0);
            yOffset = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_offsetY, 0);
            radiusStatusRoundConner = typedArray.getDimensionPixelSize(R.styleable.BlueFaceView_BlueFaceView_radiusStatus, 0);
            percent = typedArray.getFloat(R.styleable.BlueFaceView_BlueFaceView_percent, 0.8f);
            percentText = typedArray.getFloat(R.styleable.BlueFaceView_BlueFaceView_percent, 1f);
            statusDrawable = typedArray.getDrawable(R.styleable.BlueFaceView_BlueFaceView_statusDrawable);
            hideStatus = typedArray.getBoolean(R.styleable.BlueFaceView_BlueFaceView_hideStatusDrawable, false);
            firstName = typedArray.getString(R.styleable.BlueFaceView_BlueFaceView_firstName);
            lastName = typedArray.getString(R.styleable.BlueFaceView_BlueFaceView_lastName);
            colorBack = typedArray.getColor(R.styleable.BlueFaceView_BlueFaceView_backgroundColor, -1);
            useRoundBreach = typedArray.getBoolean(R.styleable.BlueFaceView_BlueFaceView_useRoundBreach, false);
            typedArray.recycle();
        } else {
            xOffset = UiUtils.dipToPx(10);
            yOffset = UiUtils.dipToPx(10);
            percent = 0.8f;
        }
        if (!TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName)) {
            setName(firstName, lastName);
        }
        path = new Path();
        pathStatus = new Path();
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radiusStatusRoundConner);
            }
        });
        setClipToOutline(true);
    }

    public void setName(String firstName, String lastName) {
        setName(firstName, lastName, false);
    }

    public void setName(String firstName, String lastName, boolean isGroup) {
        setName(firstName, lastName, isGroup, -1);
    }

    public void setName(String firstName, String lastName, @DrawableRes int drawableResourceId) {
        setName(firstName, lastName, false, drawableResourceId);
    }

    /**
     * Set default face.
     *
     * @param firstName
     * @param lastName
     * @param isGroup   If true,default face will be a picture.
     */
    public void setName(String firstName, String lastName, boolean isGroup, @DrawableRes int drawableResourceId) {
        boolean isNeedChange = false;
        if (!TextUtils.isEmpty(firstName)) {
            if (this.defaultFirstName == null || (!this.defaultFirstName.equals(firstName))) {
                isNeedChange = true;
                this.defaultFirstName = firstName;
            }

            this.firstName = firstName.toUpperCase();
        } else {
            if (this.defaultFirstName == null) {
                this.defaultFirstName = firstName;
                isNeedChange = true;
            }
            this.firstName = firstName;
        }
        if (!TextUtils.isEmpty(lastName)) {
            if (this.defaultLastName == null || (!this.defaultLastName.equals(lastName))) {
                isNeedChange = true;
                this.defaultLastName = lastName;

            }
            this.lastName = lastName.toUpperCase();
        } else {
            if (this.defaultLastName == null) {
                this.defaultLastName = lastName;
                isNeedChange = true;
            }
            this.lastName = lastName;
        }

        if (this.isGroup != isGroup) {
            isNeedChange = true;
        }
        this.isGroup = isGroup;
        try {
            if (this.defaultFaceId == 0 || (this.defaultFaceId != drawableResourceId)) {
                isNeedChange = true;
            }
            this.defaultFaceId = drawableResourceId;
            statusDrawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
        } catch (Exception ignore) {
        }
        if (isNeedChange) {
            changeFaceType();
        }
    }

    private void changeFaceType() {
        if (!isGroup) {
            if (!TextUtils.isEmpty(firstName) && firstName.length() > 0) {
                StringUtils.getCharCount(firstName, new StringUtils.CharIterator() {
                    @Override
                    public boolean getCharString(String item, int codePoint) {
                        BlueFaceView.this.firstName = item;
                        return false;
                    }
                });
            } else {
                this.firstName = "";
            }
            if (!TextUtils.isEmpty(lastName) && lastName.length() > 0) {
                StringUtils.getCharCount(lastName, new StringUtils.CharIterator() {
                    @Override
                    public boolean getCharString(String item, int codePoint) {
                        BlueFaceView.this.lastName = item;
                        return false;
                    }
                });
            } else {
                this.lastName = "";
            }
            defaultFace = new BlueFaceDrawable(getContext(), this.firstName + this.lastName, colorBack, percentText);
        } else {
            defaultFace = getResources().getDrawable(R.drawable.default_face_group, null);
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            if (defaultFace != null) {
                drawable = defaultFace;
            } else {
                return;
            }
        }
        if (drawableLast != drawable) {
            originWidth = 0;
            originHeight = 0;
            drawableLast = drawable;
        }
        int widthCanvas = getWidth();
        int heightCanvas = getHeight();
        originWidth = (int) (getWidth() * percent);
        originHeight = (int) (getHeight() * percent);

        if (originWidth == 0 || originHeight == 0) {
            return;
        }

        if (radiusRoundConner == 0) {
            radiusRoundConner = widthCanvas * 0.2f;
        }
        if (radiusStatusRoundConner == 0) {
            radiusStatusRoundConner = widthCanvas * 0.08f;
            radiusArray = new float[]{0, 0, 0, 0, 0, 0, radiusStatusRoundConner, radiusStatusRoundConner};
        }
        if (widthOfStatus == 0 || heightOfStatus == 0) {
            widthOfStatus = (int) (widthCanvas * 0.3f);
            heightOfStatus = widthOfStatus;
        }
        if (xOffset == 0 || yOffset == 0) {
            xOffset = (int) (widthCanvas * 0.01f);
            yOffset = xOffset;
        }
        int left = (int) ((widthCanvas - originWidth) / 2);
        int right = (int) (left + originWidth);
        int top = (int) ((heightCanvas - originHeight) / 2);
        int bottom = (int) (top + originHeight);
        drawable.setBounds(left, top, right, bottom);
        canvas.setDrawFilter(paintFlagsDrawFilter);

        rectF.set(left, top, right, bottom);
        path.reset();
        path.addRoundRect(rectF, radiusRoundConner, radiusRoundConner, Path.Direction.CW);

        if (!isGroup && statusDrawable != null && !hideStatus) {
            int leftStatus = widthCanvas - widthOfStatus;
            int topStatus = 0;
            int rightStatus = widthCanvas;
            int bottomStatus = heightOfStatus;

            rectFStatus.set(leftStatus - xOffset, topStatus, rightStatus, bottomStatus + yOffset);
            statusDrawable.setBounds(leftStatus, topStatus, rightStatus, bottomStatus);
            pathStatus.reset();
            if (useRoundBreach) {
                pathStatus.addCircle((leftStatus + rightStatus) / 2f, (bottomStatus + topStatus) / 2f, (rightStatus - leftStatus) / 2f + xOffset, Path.Direction.CW);
                path.op(pathStatus, Path.Op.DIFFERENCE);
                if (paint == null) {
                    paint = new Paint();
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.color_main_blue_275edb));
                }
                canvas.drawCircle((leftStatus + rightStatus) / 2f, (bottomStatus + topStatus) / 2f, (rightStatus - leftStatus) / 2f, paint);
            } else {
                pathStatus.addRoundRect(rectFStatus, radiusArray, Path.Direction.CW);
                path.op(pathStatus, Path.Op.DIFFERENCE);
                statusDrawable.draw(canvas);
            }
        }

        canvas.clipPath(path);
        drawable.draw(canvas);
    }

    public boolean isHideStatus() {
        return hideStatus;
    }

    public void setHideStatus(boolean hideStatus) {
        this.hideStatus = hideStatus;
        postInvalidate();
    }

    public void setStatusDrawable(@DrawableRes int statusDrawableResource) {
        if (statusDrawableResource == 0) {
            this.statusDrawable = null;
        } else {
            this.statusDrawable = ContextCompat.getDrawable(getContext(), statusDrawableResource);
        }
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        setLayerType(LAYER_TYPE_NONE, null);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm != null) {
            super.setImageBitmap(bm);
        }
    }
}