package cndoppler.cn.wifiprobe.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class OvalMeasurePoint extends BasePoint
{
    public OvalMeasurePoint(int x, int y)
    {
        super(x, y);
    }

    @Override
    public void drawMeasure(int startX, int startY, int endX, int endY, Canvas canvas, Paint paint)
    {
        paint.setColor(Color.parseColor("#0000ff"));
        canvas.drawOval(startX,startY,endX,endY,paint);
    }
}
