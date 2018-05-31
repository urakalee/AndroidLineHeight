package me.urakalee.androidlineheight;

import android.content.Context;
import android.graphics.Paint.FontMetricsInt;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 使用该组件可以基本上完美解决 sketch 中默认行高条件下文本还原 ui 稿的问题
 *
 * <p />
 * 原理:<br />
 * 1. 单行文字高度和 ui 稿差一个 additionalPaddingBottom<br />
 * 2. 两行文字的高度 = 单行文字的高度 + 单行文字设置 {@link #setIncludeFontPadding(boolean)} 为 false 的高度.
 * 所以多行文本需要增加 lineSpaceExtra = topSpace + bottomSpace + additionalPaddingBottom<br />
 * 3. 5.0 以下系统需要特殊处理 paddingBottom, 因为会增加额外的 lineSpaceExtra
 * 4. 对于 lineHeight, 与 defaultHeightLine 的差值等分成两部分, 分别加到 paddingTop 和 paddingBottom 上
 *
 * <p />
 * 局限:<br />
 * * density = 1.5 手机部分字号行高能对齐, 但文本的起始绘制位置可能对不上, 受 {@link #setSingleLine(boolean)} 影响.
 * 实验结果 15dp 字号在 {@link #setSingleLine(boolean)} 为 true 时偏差尤其大<br />
 * * vivo 部分手机(如 X9 6.x), 锤子手机, 虽然是 5.0 及以上系统, 但多行还是会增加额外的 lineSpace, 单行不会
 *
 * <p />
 * 注意:<br />
 * * 使用该组件时不应设定 paddingTop 和 paddingBottom, 不应通过自定义属性或 {@link #setTextSizeInDp(int)}
 * 和 {@link #setTextSizeAndLineHeightInDp(int, int)} 之外的方法设置字号和行间距<br />
 * * 如果需要对齐其它字号, 需要在三倍屏手机上运行 {@link LineHeightActivity}, 获得单行图片与文字的高度差, 加入 {@link #paddingBottomMap}
 *
 * @author liqiang
 */
public class LineHeightTextView extends TextView {

    public LineHeightTextView(Context context) {
        super(context);
    }

    public LineHeightTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineHeightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float density = getResources().getDisplayMetrics().density;

    public void setTextSizeInDp(int sizeInDp) {
        setTextSizeAndLineHeightInDp(sizeInDp, 0);
    }

    public void setTextSizeAndLineHeightInDp(int sizeInDp, int lineHeightInDp) {
        // 如果没传 lineHeightInDp 或 没有 sizeInDp 对应的 defaultLineHeight, 则 lineHeight 相关值均 为 0
        int defaultLineHeight = defaultLineHeightMap.get(sizeInDp, 0);
        int lineHeightExtra = (lineHeightInDp == 0) ? 0
                : (defaultLineHeight == 0) ? 0 : (int) ((lineHeightInDp - defaultLineHeight) * density);
        int topHeight = (lineHeightExtra == 0) ? 0 : lineHeightExtra / 2;
        int bottomHeight = lineHeightExtra - topHeight;
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizeInDp);
        FontMetricsInt metrics = getPaint().getFontMetricsInt();
        int topSpace = metrics.ascent - metrics.top;
        int bottomSpace = metrics.bottom - metrics.descent;
        int lineSpace = topSpace + bottomSpace + lineHeightExtra;
        int additionalPaddingBottom =
                (int) Math.floor(paddingBottomMap.get(sizeInDp, 0f) * density);
        setLineSpacing(lineSpace + additionalPaddingBottom, 1);
        int paddingBottom = additionalPaddingBottom;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            paddingBottom = -lineSpace;
        }
        setPadding(getPaddingLeft(), topHeight, getPaddingRight(), bottomHeight + paddingBottom);
    }

    private static SparseIntArray defaultLineHeightMap = new SparseIntArray();

    /**
     * sketch 中字号对应的默认行高
     */
    static {
        defaultLineHeightMap.put(10, 14);
        defaultLineHeightMap.put(11, 16);
        defaultLineHeightMap.put(12, 17);
        defaultLineHeightMap.put(13, 18);
        defaultLineHeightMap.put(14, 20);
        defaultLineHeightMap.put(15, 21);
        defaultLineHeightMap.put(16, 22);
        defaultLineHeightMap.put(17, 24);
        defaultLineHeightMap.put(19, 26);
        defaultLineHeightMap.put(30, 42);
    }

    private static SparseArray<Float> paddingBottomMap = new SparseArray<>();

    /**
     * density 为 3 时的实验值, 作为计算 additionalLineSpace 的基数
     */
    static {
        paddingBottomMap.put(10, 1f / 3);
        paddingBottomMap.put(11, 4f / 3);
        paddingBottomMap.put(12, 2f / 3);
        paddingBottomMap.put(13, 1f / 3);
        paddingBottomMap.put(14, 3f / 3);
        paddingBottomMap.put(15, 2f / 3);
        paddingBottomMap.put(16, 1f / 3);
        paddingBottomMap.put(17, 4f / 3);
        paddingBottomMap.put(19, 1f / 3);
        paddingBottomMap.put(22, 2f / 3);
        paddingBottomMap.put(30, 5f / 3);
    }
}
