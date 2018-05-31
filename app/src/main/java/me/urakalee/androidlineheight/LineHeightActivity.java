package me.urakalee.androidlineheight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import me.urakalee.androidlineheight.FontSizeSelectView.FontSizeSelectViewDelegate;

/**
 * @author liqiang
 */
public class LineHeightActivity extends AppCompatActivity {

    private FontSizeSelectView fontSelect;

    private ImageView image1Line1;
    private ImageView image1Line2;
    private ImageView image1Line3;
    private ImageView image1Line4;
    private ImageView imageMultiLine;

    private LineHeightTextView text1Line1;
    private LineHeightTextView text1Line2;
    private LineHeightTextView text1Line3;
    private LineHeightTextView text1Line4;
    private LineHeightTextView textMultiLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_height);
        fontSelect = findViewById(R.id.fontSelect);
        fontSelect.setDelegate(new FontSizeSelectViewDelegate() {
            @Override
            public void onFontSizeChange(int fontSizeInDp, int lineSpaceInDp) {
                LineHeightActivity.this.onFontSizeChange(fontSizeInDp, lineSpaceInDp);
            }
        });
        image1Line1 = findViewById(R.id.image1Line1);
        image1Line2 = findViewById(R.id.image1Line2);
        image1Line3 = findViewById(R.id.image1Line3);
        image1Line4 = findViewById(R.id.image1Line4);
        imageMultiLine = findViewById(R.id.imageMultiLine);
        text1Line1 = findViewById(R.id.text1Line1);
        text1Line2 = findViewById(R.id.text1Line2);
        text1Line3 = findViewById(R.id.text1Line3);
        text1Line4 = findViewById(R.id.text1Line4);
        textMultiLine = findViewById(R.id.textMultiLine);
    }

    private void onFontSizeChange(int fontSizeInDp, int lineHeightInDp) {
        String line1Id = (lineHeightInDp == 0)
                ? String.format("size%d_1line", fontSizeInDp)
                : String.format("size%d_1line_height%d", fontSizeInDp, lineHeightInDp);
        int resId1Line = getResources().getIdentifier(line1Id,
                "drawable", getPackageName());
        String multiLineId = (lineHeightInDp == 0)
                ? String.format("size%d_multiline", fontSizeInDp)
                : String.format("size%d_multiline_height%d", fontSizeInDp, lineHeightInDp);
        int resIdMultiLine = getResources().getIdentifier(multiLineId,
                "drawable", getPackageName());
        image1Line1.setImageResource(resId1Line);
        image1Line2.setImageResource(resId1Line);
        image1Line3.setImageResource(resId1Line);
        image1Line4.setImageResource(resId1Line);
        text1Line1.setVisibility(resId1Line == 0 ? View.GONE : View.VISIBLE);
        text1Line2.setVisibility(resId1Line == 0 ? View.GONE : View.INVISIBLE);
        text1Line3.setVisibility(resId1Line == 0 ? View.GONE : View.VISIBLE);
        text1Line4.setVisibility(resId1Line == 0 ? View.GONE : View.INVISIBLE);
        imageMultiLine.setImageResource(resIdMultiLine);
        text1Line1.setText(String.format("%d一行", fontSizeInDp));
        text1Line1.setTextSizeAndLineHeightInDp(fontSizeInDp, lineHeightInDp);
        text1Line2.setText(String.format("%d一行", fontSizeInDp));
        text1Line2.setTextSizeAndLineHeightInDp(fontSizeInDp, lineHeightInDp);
        text1Line3.setText(String.format("%d一行", fontSizeInDp));
        text1Line3.setTextSizeAndLineHeightInDp(fontSizeInDp, lineHeightInDp);
        text1Line4.setText(String.format("%d一行", fontSizeInDp));
        text1Line4.setTextSizeAndLineHeightInDp(fontSizeInDp, lineHeightInDp);
        textMultiLine.setText(String.format("%d多行\n%d多行\n%d多行\n%d多行",
                fontSizeInDp, fontSizeInDp, fontSizeInDp, fontSizeInDp));
        textMultiLine.setTextSizeAndLineHeightInDp(fontSizeInDp, lineHeightInDp);

        fontSelect.postDelayed(new Runnable() {
            @Override
            public void run() {
                int image1LineHeight = image1Line1.getHeight();
                int text1LineHeight = text1Line1.getHeight();
                Toast.makeText(LineHeightActivity.this,
                        String.format("diff: %d", image1LineHeight - text1LineHeight),
                        Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }
}
