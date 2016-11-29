package fontana;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoTextView extends TextView {
    public RobotoTextView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    public RobotoTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RobotoTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto-regular.ttf");
            setTypeface(roboto);
        }
    }
}
