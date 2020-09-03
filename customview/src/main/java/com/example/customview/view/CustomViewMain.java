package com.example.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.customview.R;

public class CustomViewMain extends View {

    private RectF rectF;
    private int cx, cy, radiusSmallCircle;
    private float eventGetX, eventGetY, radiusCircle;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_0 = 0;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_60 = 60;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_90 = 90;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_180 = 180;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_270 = 270;
    private final int DIVIDING_A_CIRCLE_INTO_DEGREES_360 = 360;
    private Paint paintGreen;
    private Paint paintRed;
    private Paint paintYellow;
    private Paint paintBlue;
    private Paint paintCircleSmall;
    private Paint neutralColor;
    private onTouchListener onTouchActionListener;
    private boolean leftTopCorner;
    private boolean rightTopCorner;
    private boolean leftBottomCorner;
    private boolean rightBottomCorner;

    public CustomViewMain(Context context) {
        super(context);
        init();
    }

    public CustomViewMain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }

    public CustomViewMain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
        init();
    }

    private void getAttrs(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StyleView);
            radiusSmallCircle = typedArray.getDimensionPixelSize(R.styleable.StyleView_radiusCircle, 0);
            typedArray.recycle();
        }
    }

    private void init() {
        rectF = new RectF(0, 0, 1050, 1050);
        paintGreen = new Paint();
        paintRed = new Paint();
        paintYellow = new Paint();
        paintBlue = new Paint();
        paintCircleSmall = new Paint();
        neutralColor = new Paint();
    }

    public interface onTouchListener {
        void onTouch(int x, int y);
    }

    public void setOnTouchActionListener(onTouchListener onTouchActionListener) {
        this.onTouchActionListener = onTouchActionListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w / 2;
        cy = h / 2;
        radiusSmallCircle = w / 6;
        radiusCircle = cx;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        creationObjectsPaint();
        creationElementsCircle(canvas);
        smallCircle(canvas, cx, cy, radiusSmallCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onTouchActionListener != null) {
                eventGetX = event.getX();
                eventGetY = event.getY();
                onTouchActionListener.onTouch((int) eventGetX, (int) eventGetY);
                setPaint();
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    private void smallCircle(Canvas canvas, float cx, float cy, float radius) {
        paintCircleSmall.setColor(ContextCompat.getColor(getContext(), R.color.colorPurple));
        paintCircleSmall.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radius, paintCircleSmall);
    }

    private void drawDivisionCircle(Canvas canvas, RectF rectF, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
        canvas.drawArc(rectF, startAngle, sweepAngle, useCenter, paint);
    }

    private void setPaint() {
        if ((cx - eventGetX) <= radiusCircle && (cy - eventGetY) <= radiusCircle) {
            if ((eventGetX >= (cx - radiusSmallCircle) && (eventGetX <= cx + radiusSmallCircle)) && (eventGetY >= (cy - radiusSmallCircle) && (eventGetY <= (cy + radiusSmallCircle)))) {
                leftTopCorner = true;
                rightTopCorner = true;
                leftBottomCorner = true;
                rightBottomCorner = true;

            } else if ((eventGetX >= cx && eventGetX <= (cx * 2)) && (eventGetY >= 0 && eventGetY <= cy)) {//вверхний правый
                rightTopCorner = true;

            } else if ((eventGetX >= 0 && eventGetX <= cx) && (eventGetY >= cy && eventGetY <= (cy * 2))) {//нижний левый
                leftBottomCorner = true;

            } else if (((cx * 2) >= eventGetX && eventGetX >= cx) && (eventGetY >= cy && eventGetY <= (cy * 2))) {// нижний правый
                rightBottomCorner = true;
            } else if ((eventGetX >= 0 && eventGetX <= cx) && (eventGetY <= cy && eventGetY >= 0)) {//вверхний левый
                leftTopCorner = true;
            }
        }
    }

    private void creationObjectsPaint() {
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);

        paintRed.setColor(Color.RED);
        paintRed.setStyle(Paint.Style.FILL);

        paintYellow.setColor(Color.YELLOW);
        paintYellow.setStyle(Paint.Style.FILL);

        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);

        neutralColor.setColor(ContextCompat.getColor(getContext(), R.color.colorGray));
        neutralColor.setStyle(Paint.Style.FILL);
    }

    private void creationElementsCircle(Canvas canvas) {
        if (rightBottomCorner) {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_0, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, neutralColor);
            rightBottomCorner = false;
        } else {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_0, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, paintBlue);
        }

        if (leftBottomCorner) {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_90, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, neutralColor);
            leftBottomCorner = false;
        } else {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_90, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, paintRed);
        }

        if (leftTopCorner) {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_180, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, neutralColor);
            leftTopCorner = false;
        } else {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_180, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, paintGreen);
        }

        if (rightTopCorner) {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_270, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, neutralColor);
            rightTopCorner = false;
        } else {
            drawDivisionCircle(canvas, rectF, DIVIDING_A_CIRCLE_INTO_DEGREES_270, DIVIDING_A_CIRCLE_INTO_DEGREES_90, true, paintYellow);
        }
    }
}