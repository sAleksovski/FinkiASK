package mk.ukim.finki.tr.finkiask.ui.result;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import mk.ukim.finki.tr.finkiask.R;

public class Circle extends View {

    private static final int START_ANGLE_POINT = -90;

    private final Paint paint;
    private final RectF rect;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Circle,
                0, 0);

        int size = -1;

        try {
            size = a.getInteger(R.styleable.Circle_size, -1);
        } finally {
            a.recycle();
        }

        final int strokeWidth = 30;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.BLUE);

        float px = 300;

        if (size != -1) {
            Resources r = getResources();
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, r.getDisplayMetrics());
        }

        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, px - strokeWidth, px - strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}