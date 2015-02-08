package com.doodeec.weather.android.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Oval image view displays image with clipped oval outline
 * Compatible with pre-Lollipop devices
 *
 * @author Dusan Bartos
 */
public class OvalImageView extends ImageView {
    private int mRadius = 10;
    private Path mClipPath;

    public OvalImageView(Context context) {
        this(context, null);
    }

    public OvalImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mClipPath = new Path();
        mClipPath.addRoundRect(new RectF(0, 0, w, h), mRadius, mRadius, Path.Direction.CW);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        this.invalidate();
    }
}
