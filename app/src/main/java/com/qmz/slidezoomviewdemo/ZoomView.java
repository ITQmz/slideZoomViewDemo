package com.qmz.slidezoomviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by 15757 on 2017/12/26.
 */

public class ZoomView extends View implements SeekBar.OnSeekBarChangeListener {
    private Paint paintDefault;
    private Paint paintSelect;

    private int defaultSize;
    private int selectSize;

    private int height;
    private int width;

    private int paddingTop=50;
    private int paddingBottom=20;
    private float paddingLeft=30;
    private int paddingRight=10;

    private SeekBar seekBar;

    private String[] StringNum={"A","B","C","D","E","F","G","H"};

    private int mcurrentProgress;
    private int mcurrentPointIndex;

    private int viewWidht;
    private int viewHeight;

    private float defaultWordWidth;
    private float speceWordWidth=50;
    private float selectWoreWidth;

    private float textHeight;

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintDefault=new Paint();
        defaultSize=60;
        paintDefault.setTextSize(defaultSize);
        defaultWordWidth=paintDefault.measureText(StringNum[0]);



        paintSelect=new Paint();
        selectSize=120;
        paintSelect.setTextSize(120);
        selectWoreWidth=paintSelect.measureText(StringNum[0]);

        Rect bounds=new Rect();
        paintSelect.getTextBounds(StringNum[0],0,1,bounds);

        textHeight=bounds.bottom-bounds.top;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(seekBar==null||StringNum==null||StringNum.length==0){
            return;
        }
        float textWidht=0;

        float suntext=defaultWordWidth*(StringNum.length-1)+selectWoreWidth;
        speceWordWidth=(viewWidht-paddingLeft-paddingRight-suntext)/StringNum.length;

        for(int i=0;i<mcurrentPointIndex;i++){
            canvas.drawText(StringNum[i],paddingLeft+textWidht,viewHeight/2+textHeight/2,paintDefault);
            textWidht+=defaultWordWidth+speceWordWidth;
        }

        canvas.drawText(StringNum[mcurrentPointIndex],paddingLeft+textWidht,viewHeight/2+textHeight/2,paintSelect);
        textWidht+=speceWordWidth+selectWoreWidth;

        for(int i=mcurrentPointIndex+1;i<StringNum.length;i++){
            canvas.drawText(StringNum[i],paddingLeft+textWidht,viewHeight/2+textHeight/2,paintDefault);
            textWidht+=defaultWordWidth+speceWordWidth;
        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int height=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        if(heightMode==MeasureSpec.AT_MOST){
            height= (int) getDefaultHeight();
        }

        if(heightMode==MeasureSpec.EXACTLY){
            if(height<getDefaultHeight()){
                height= (int) getDefaultHeight();
            }
        }
        viewWidht=width;
        viewHeight=height;
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height=h;
        width=w;
    }

    public void setSeekBar(SeekBar seekBar){
        this.seekBar=seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        postInvalidate();

    }

    private static final String TAG = "ZoomView";
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.mcurrentProgress=progress;
        Log.d(TAG, "onProgressChanged: "+progress);
        int dura=seekBar.getMax()/StringNum.length;
        int tempPointIndex=0;
        for (int i=StringNum.length-1;i>=0;i--){
            if(progress>i*dura){
                tempPointIndex =i;
                Log.d(TAG, "tempPointIndex: "+tempPointIndex);
                 break;
            }
        }

        Log.d(TAG, "tempPointIndex:======== "+tempPointIndex);
        Log.d(TAG, "mcurrentPointIndex: "+mcurrentPointIndex);
        if(tempPointIndex!=mcurrentPointIndex){
            mcurrentPointIndex=tempPointIndex;

            postInvalidate();
        }



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public float getDefaultHeight() {

        return textHeight+paddingTop+paddingBottom;
    }
}
