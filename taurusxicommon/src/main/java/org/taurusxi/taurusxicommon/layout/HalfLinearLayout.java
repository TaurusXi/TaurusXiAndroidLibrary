package org.taurusxi.taurusxicommon.layout;

/**
 * Created by DELL on 2014/12/8.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class HalfLinearLayout extends LinearLayout {
    public HalfLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HalfLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfLinearLayout(Context context) {
        super(context);
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("layout_width must be match_parent");
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);
        // Honor aspect ratio for height but no larger than 2x width.
        int height = width / 2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
