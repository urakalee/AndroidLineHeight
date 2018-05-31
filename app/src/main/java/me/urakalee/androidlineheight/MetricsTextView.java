package me.urakalee.androidlineheight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author liqiang
 */
public class MetricsTextView extends TextView {

    public MetricsTextView(Context context) {
        super(context);
        init();
    }

    public MetricsTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MetricsTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint = new Paint();

    private void init() {
        paint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        FontMetrics metrics = getPaint().getFontMetrics();
        float ascent = metrics.ascent - metrics.top;
        paint.setColor(Color.parseColor("#ff0000"));
        canvas.drawLine(0, ascent, canvas.getWidth(), ascent, paint);
        float baseline = Math.abs(metrics.top);
        paint.setColor(Color.parseColor("#00ff00"));
        canvas.drawLine(0, baseline, canvas.getWidth(), baseline, paint);
        float descent = baseline + metrics.descent;
        paint.setColor(Color.parseColor("#0000ff"));
        canvas.drawLine(0, descent, canvas.getWidth(), descent, paint);
        float bottom = baseline + metrics.bottom;
        paint.setColor(Color.parseColor("#ff0000"));
        canvas.drawLine(0, bottom, canvas.getWidth(), bottom, paint);
    }
}
