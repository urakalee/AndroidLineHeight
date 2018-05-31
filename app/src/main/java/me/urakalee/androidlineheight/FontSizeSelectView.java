package me.urakalee.androidlineheight;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * @author liqiang
 */
public class FontSizeSelectView extends AutoCompleteTextView {

    public FontSizeSelectView(Context context) {
        super(context);
        init();
    }

    public FontSizeSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontSizeSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAdapter(new ArrayAdapter<>(getContext(), R.layout.view_font_size, new String[]{
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "15+4",
                "16",
                "17",
                "19",
                "22",
                "30"
        }));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDropDown();
                    return true;
                } else {
                    return false;
                }
            }
        });
        addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] array = charSequence.toString().split("\\+");
                String fontSize = array[0];
                String lineSpace = array.length > 1 ? array[1] : "0";
                if (delegate != null) {
                    try {
                        delegate.onFontSizeChange(Integer.parseInt(fontSize), Integer.parseInt(lineSpace));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private FontSizeSelectViewDelegate delegate;

    public void setDelegate(FontSizeSelectViewDelegate delegate) {
        this.delegate = delegate;
    }

    public interface FontSizeSelectViewDelegate {

        void onFontSizeChange(int fontSizeInDp, int lineSpaceInDp);
    }
}
