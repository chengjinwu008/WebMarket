package com.lanhaijiye.WebMarket.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class LineView extends View {
    private float lineWidth;
    private Paint paint;
    private float delta;
    private int x;
    private int width;
    private int height;
    private float radius;
    private int backgroundColor;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,getResources().getDisplayMetrics());
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.FILL);
        delta = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        x = 0;
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
        backgroundColor = getResources().getColor(R.color.red);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);
        x+=delta;
        if(x>=width){
            x=0;
        }
        canvas.drawLine(0, height / 2 - lineWidth / 2, width, height / 2 - lineWidth / 2, paint);
//        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x,height/2,radius,paint);
        invalidate();
    }
}
