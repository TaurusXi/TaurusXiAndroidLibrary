package org.taurusxi.taurusxicommon.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.taurusxi.taurusxicommon.R;


/**
 * Created on 15/3/4.
 *
 * @author xicheng
 * @email 505591443@qq.com
 * @github https://github.com/TaurusXi
 */
public class TrigonFrameLayout extends FrameLayout {

    private static final float DEFAULT_ANGLE_SIZE = 1.0f;
    private static final int DEFAULT_ANGLE_COLOR = Color.WHITE;
    private Path path;
    private Paint paint;
    private Context context;

    private float angleSize;
    private int angleColor;

    public TrigonFrameLayout(Context context) {
        this(context, null, 0);
    }

    public TrigonFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrigonFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setBackgroundResource(android.R.color.transparent);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public TrigonFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.setBackgroundResource(android.R.color.transparent);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    public float getAngleSize() {
        return angleSize;
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TrigonFrameLayout, defStyle, 0);
        angleSize = a.getFloat(R.styleable.TrigonFrameLayout_angle_size, DEFAULT_ANGLE_SIZE);
        angleColor = a.getColor(R.styleable.TrigonFrameLayout_angle_color, DEFAULT_ANGLE_COLOR);
        a.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(angleColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        path = new Path();
        path.moveTo(0, 0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("layout_width 必须明确大小");
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * angleSize);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.close();
        canvas.drawPath(path, paint);
    }
}
