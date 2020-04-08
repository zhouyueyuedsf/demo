package com.example.demo.views.importantview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;


import com.example.demo.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by xuyang on 2016/11/14.
 */
public class AutoFitSizeEditText extends AppCompatEditText {

    private static final int TEXT_SIZE_GAP = 2;

    // No limit (Integer.MAX_VALUE means no limit)
    private static final int NO_LIMIT_LINES = Integer.MAX_VALUE;

    // Flag for text and/or size changes to force a resize
    private boolean mNeedsResize = false;

    // Text size for hint.
    private float mHintTextSize;

    // Temporary upper bounds on the starting text size
    private float mMaxTextSize;

    // Lower bounds for text size
    private float mMinTextSize;

    // Text view line spacing multiplier
    private float mSpacingMult = 1.0f;

    // Text view additional line spacing
    private float mSpacingAdd = 0.0f;
    private OnSizeChangedListener onSizeChangedListener;
    private int mFitSizeLine;
    /**
     * 改变pixel模式后恢复line模式所用
     */
    private int mOriginFitSizeLine;

    // Default constructor override
    public AutoFitSizeEditText(Context context) {
        super(context);
        init(context, null);
    }

    // Default constructor when inflating from XML file
    public AutoFitSizeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    // Default constructor override
    public AutoFitSizeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * Resize text after measuring
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed || mNeedsResize) {
            int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
            int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
            resizeText(widthLimit, heightLimit);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * When text changes, set the force resize flag to true and reset the text size.
     */
    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        mNeedsResize = true;
        resizeText();
    }

    /**
     * If the text view size changed, set the force resize flag to true
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mNeedsResize = true;
        }

        if (onSizeChangedListener != null) {
            onSizeChangedListener.onSizeChangedListener(w, h, oldw, oldh);
        }
    }

    @Override
    public void setTextSize(float size) {
        throw new RuntimeException("Don't call setTextSize for AutoFitSizeEditText. "
                + "You probably want setMaxTextSize instead.");
    }

    @Override
    public void setTextSize(int unit, float size) {
        throw new RuntimeException("Don't call setTextSize for AutoFitSizeEditText. "
                + "You probably want setMaxTextSize instead.");
    }

    /**
     * Override the set line spacing to update our internal reference values
     */
    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        mSpacingMult = mult;
        mSpacingAdd = add;
    }

    private void init(Context context, AttributeSet attrs) {
        float textSize = getTextSize();
        mHintTextSize = textSize;
        mMaxTextSize = textSize;
        mMinTextSize = textSize;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFitSizeEditText);
            mHintTextSize = typedArray.getDimension(R.styleable.AutoFitSizeEditText_hintTextSize, textSize);
            mMinTextSize = typedArray.getDimension(R.styleable.AutoFitSizeEditText_minTextSize, textSize);
            mOriginFitSizeLine = mFitSizeLine = typedArray.getInteger(R.styleable.AutoFitSizeEditText_fitLine, NO_LIMIT_LINES);
            typedArray.recycle();
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float preTextSize = getTextSize();
                if (s.length() == 0) {
                    AutoFitSizeEditText.super.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHintTextSize);
                } else {
                    AutoFitSizeEditText.super.setTextSize(TypedValue.COMPLEX_UNIT_PX, preTextSize);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setCursorListener();
    }

    /**
     * Set the hint text size.
     *
     * @param hintTextSize px
     */
    public void setHintTextSize(float hintTextSize) {
        mHintTextSize = hintTextSize;
        requestLayout();
        invalidate();
    }

    /**
     * Set the text size.
     *
     * @param maxTextSize sp
     */
    public void setMaxTextSize(float maxTextSize) {
        super.setTextSize(maxTextSize);
        mMaxTextSize = getTextSize();
    }

    /**
     * Set the lower text size limit and invalidate the view
     *
     * @param minTextSize px
     */
    public void setMinTextSize(float minTextSize) {
        mMinTextSize = minTextSize;
        requestLayout();
        invalidate();
    }

    /**
     * Resize the text size with default width and height
     */
    public void resizeText() {
        int heightLimit = getHeight() - getPaddingBottom() - getPaddingTop();
        int widthLimit = getWidth() - getPaddingLeft() - getPaddingRight();
        resizeText(widthLimit, heightLimit);
    }

    /**
     * Resize the text size with specified width and height
     *
     * @param width
     * @param height
     */
    public void resizeText(int width, int height) {
        CharSequence text = getText();
        // Do not resize if the view does not have dimensions or there is no text, or min text size equals max text size.
        if (text == null || text.length() == 0 || height <= 0 || width <= 0 || getTextSize() == 0 || mMaxTextSize == mMinTextSize) {
            return;
        }

        if (getTransformationMethod() != null) {
            text = getTransformationMethod().getTransformation(text, this);
        }

        float targetTextSize = getTextSize();
        // Get the text view's paint object
        TextPaint textPaint = getPaint();
        // Get the required text height
        int textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        // fit with line, or fit with TextView's height
//        int maxLines = getMaxLines(this);
        setFitSizeLine(mFitSizeLine);
//        mFitSizeLine = getMaxLines(this);
        // Do not resize if the view wrap content.
        /*if (fitLine == NO_LIMIT_LINES) {
            return;
        }*/


//        // Get the text view's paint object
//        TextPaint textPaint = getPaint();

        // fit with max line, or fit with TextView's height
        if (mFitSizeLine != NO_LIMIT_LINES) {
            boolean hasFitted = false;
            int lineCount = getTextLines(text, textPaint, width, targetTextSize);
            // Until we either fit our lines or we had reached our min text size, incrementally try smaller sizes
            while (lineCount > mFitSizeLine && targetTextSize > mMinTextSize) {
                hasFitted = true;
                targetTextSize = Math.max(targetTextSize - TEXT_SIZE_GAP, mMinTextSize);
                lineCount = getTextLines(text, textPaint, width, targetTextSize);
            }
            // Find best text size when lineCount == fitLine. when delete text ,we need recover textSize by max text size
            while (!hasFitted && lineCount == mFitSizeLine && targetTextSize < mMaxTextSize) {
                float tempSize = Math.min(targetTextSize + TEXT_SIZE_GAP, mMaxTextSize);
                lineCount = getTextLines(text, textPaint, width, tempSize);
                if (lineCount == mFitSizeLine) {
                    targetTextSize = tempSize;
                }
            }

        }else{
            // Until we either fit within our text view or we had reached our min text size, incrementally try smaller sizes
            while (textHeight > height && targetTextSize > mMinTextSize) {
                targetTextSize = Math.max(targetTextSize - 2, mMinTextSize);
                textHeight = getTextHeight(text, textPaint, width, targetTextSize);
            }
        }


        targetTextSize = targetTextSize > mMaxTextSize ? mMaxTextSize : targetTextSize;

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
        setLineSpacing(mSpacingAdd, mSpacingMult);
        // Reset force resize flag
        mNeedsResize = false;
    }

    // Set the text size of the text paint object and use a static layout to render text off screen before measuring
    private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        // modified: make a copy of the original TextPaint object for measuring
        // (apparently the object gets modified while measuring, see also the
        // docs for TextView.getPaint() (which states to access it read-only)
        TextPaint paintCopy = new TextPaint(paint);
        // Update the text paint object
        paintCopy.setTextSize(textSize);
        // Measure using a static layout
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getHeight();
    }

    private int getTextLines(CharSequence source, TextPaint paint, int width, float textSize) {
        TextPaint paintCopy = new TextPaint(paint);
        paintCopy.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getLineCount();
    }

    private static int getMaxLines(TextView view) {
        int maxLines = NO_LIMIT_LINES;
        TransformationMethod method = view.getTransformationMethod();
        if (method != null && method instanceof SingleLineTransformationMethod) {
            maxLines = 1;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // setMaxLines() and getMaxLines() are only available on android-16+
            maxLines = view.getMaxLines();
        }
        return maxLines;
    }

    public Drawable getCursorDrawable() {
        Drawable drawable = null;
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            drawable = ContextCompat.getDrawable(getContext(), fCursorDrawableRes.getInt(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_white_cursor);
        }
        return drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCursorDrawable != null) {
            mCursorDrawable.draw(canvas);
        }
    }

    private Path mCursorPath = new Path();
    private RectF mCursorRectF = new RectF();
    private Drawable mCursorDrawable;
    private CursorHandler mCursorHandler;
    private OnTouchListener mOriginTouchListener;

    private void invalidateMeizuCursor() {
        if (getLayout() == null) {
            return;
        }
        if (mCursorDrawable == null) {
            mCursorDrawable = getCursorDrawable();
        }
        if (mCursorDrawable.getBounds().isEmpty()) {
            //cursor visible.
            CharSequence text = getText();
            mCursorPath.reset();
            getLayout().getCursorPath(getSelectionStart(), mCursorPath, text);
            mCursorPath.computeBounds(mCursorRectF, false);
            float thick = (float) Math.ceil(getPaint().getStrokeWidth());
            if (thick < 1.0f) {
                thick = 1.0f;
            }
            thick /= 2.0f;
            final int horizontalPadding = getCompoundPaddingLeft();
            final int verticalPadding = getExtendedPaddingTop() + getVerticalOffset();
            int left = (int) (Math.floor(horizontalPadding + mCursorRectF.left - thick) - mCursorDrawable.getIntrinsicWidth() / 2.f);
            int top = (int) Math.floor(verticalPadding + mCursorRectF.top - thick);
            int right = left + mCursorDrawable.getIntrinsicWidth();
            int bottom = (int) Math.ceil(verticalPadding + mCursorRectF.bottom + thick);
            mCursorDrawable.setBounds(left, top, right, bottom);
            invalidate(left, top, right, bottom);
        } else {
            //cursor invisible.
            hideMeizuCursor();
        }
    }

    private void hideMeizuCursor() {
        if (mCursorDrawable != null) {
            mCursorDrawable.setBounds(0, 0, 0, 0);
            invalidate();
        }
    }

    private int getVerticalOffset() {
        final int gravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
        Layout layout = getLayout();
        int vOffset = 0;
        if (gravity != Gravity.TOP) {
            int boxHeight = getMeasuredHeight() - (getExtendedPaddingTop() + getExtendedPaddingBottom());
            int textHeight = layout.getHeight();
            if (textHeight < boxHeight) {
                if (gravity == Gravity.BOTTOM)
                    vOffset = boxHeight - textHeight;
                else // (gravity == Gravity.CENTER_VERTICAL)
                    vOffset = (boxHeight - textHeight) >> 1;
            }
        }
        return vOffset;
    }

    /**
     * cursor blink on Meizu.
     *
     * @param show
     */
    public void shouldBlinkOnMeiZu(boolean show) {
        if (!isMeizu()) {
            return;
        }
        //cancel origin cursor.
        setCursorVisible(!show);
        if (show) {
            if (mCursorHandler == null) {
                mCursorHandler = new CursorHandler(this);
            }
            mCursorHandler.start();
        } else {
            if (mCursorHandler != null) {
                mCursorHandler.cancel();
                mCursorHandler = null;
            }
            hideMeizuCursor();
        }
    }

    private void setCursorListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hideMeizuCursor();
                if (mCursorHandler != null) {
                    mCursorHandler.cancel();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCursorHandler != null) {
                    mCursorHandler.startImmediately();
                }
            }
        });
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        hideMeizuCursor();
                        if (mCursorHandler != null) {
                            mCursorHandler.cancel();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCursorHandler != null) {
                            mCursorHandler.startImmediately();
                        }
                        break;
                }
                v.onTouchEvent(event);
                return mOriginTouchListener == null || mOriginTouchListener.onTouch(v, event);
            }
        });
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        mOriginTouchListener = listener;
    }

    public static boolean isMeizu() {
        return Build.BRAND.equalsIgnoreCase("Meizu");
    }

    private static class CursorHandler extends Handler implements Runnable {
        private final static int BLINK = 500;

        private boolean cancelled;
        private WeakReference<AutoFitSizeEditText> weakReference;

        public CursorHandler(AutoFitSizeEditText editText) {
            weakReference = new WeakReference<>(editText);
        }

        public void run() {
            if (cancelled) {
                return;
            }
            removeCallbacks(CursorHandler.this);
            if (weakReference.get() != null) {
                weakReference.get().invalidateMeizuCursor();
            }
            postAtTime(this, SystemClock.uptimeMillis() + BLINK);
        }

        void cancel() {
            if (!cancelled) {
                removeCallbacks(CursorHandler.this);
                cancelled = true;
            }
        }

        void start() {
            cancelled = false;
            removeCallbacks(CursorHandler.this);
            postAtTime(this, SystemClock.uptimeMillis() + BLINK);
        }

        void startImmediately() {
            cancelled = false;
            removeCallbacks(CursorHandler.this);
            post(this);
        }
    }

    public void setOnSizeChangedListener(OnSizeChangedListener _onSizeChangedListener) {
        onSizeChangedListener = _onSizeChangedListener;
    }

    /**
     * pixel模式到line模式的恢复
     */
    public void resetFitSizeLine() {
        mFitSizeLine = mOriginFitSizeLine;
        setFitSizeLine(mFitSizeLine);
    }

    public void setFitSizeLine(int line) {
        int maxLines = getMaxLines(this);
        mFitSizeLine = line < maxLines ? line : maxLines;
    }

    public interface OnSizeChangedListener {
        void onSizeChangedListener(int w, int h, int oldw, int oldh);
    }

}
