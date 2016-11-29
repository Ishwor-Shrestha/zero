package fontana;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class StrikeThroughRobotoTextView extends TextView {
    private Paint paint;
    private boolean addStrike = false;
    private int color = Color.BLACK;

    public StrikeThroughRobotoTextView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    public StrikeThroughRobotoTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public StrikeThroughRobotoTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto-regular.ttf");
            setTypeface(roboto);
        }

        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(getResources().getDisplayMetrics().density * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (addStrike) {
            canvas.drawLine(0, (getHeight() / 2) + 3, getWidth(),
                    (getHeight() / 2) + 3, paint);
        }
    }

    public void setStrikeThrough(boolean addStrike) {
        this.addStrike = addStrike;
    }

    public void setStrikeColor(int color) {
        this.color = color;
    }
}
