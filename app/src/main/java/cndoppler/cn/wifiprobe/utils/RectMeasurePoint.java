package cndoppler.cn.wifiprobe.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class RectMeasurePoint extends BasePoint
{
    public RectMeasurePoint(int x, int y,int pos)
    {
        super(x, y);
        index = pos;
    }

    @Override
    public void drawMeasure(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint)
    {
        paint.setColor(Color.parseColor("#e8a94f"));
        canvas.drawRect(startX,startY,endX,endY,paint);
    }

    @Override
    float getMeasure()
    {
        float x = Math.abs(endX-startX);
        float y = Math.abs(endY - startY);
        return x*y;
    }
}
