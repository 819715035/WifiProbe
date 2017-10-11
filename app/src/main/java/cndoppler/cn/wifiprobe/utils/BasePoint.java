package cndoppler.cn.wifiprobe.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by admin on 2017/10/11 0011.
 */

public abstract class BasePoint extends Point
{

    public BasePoint(int x, int y)
    {
        super(x, y);
    }

    //绘制测量图形
    abstract void drawMeasure(int startX, int startY, int endX, int endY, Canvas canvas, Paint paint);

    public void drawTag(int x,int y,Canvas canvas,Paint paint){
        canvas.drawLine(x-10,y,x+10,y,paint);
        canvas.drawLine(x,y-10,x,y+10,paint);
    }

    public void startDraw(int startX, int startY, int endX, int endY, Canvas canvas, Paint paint){
        drawTag(startX,startY,canvas,paint);
        drawMeasure(startX,startY,endX,endY,canvas,paint);
        drawTag(endX,endY,canvas,paint);
    }
}
