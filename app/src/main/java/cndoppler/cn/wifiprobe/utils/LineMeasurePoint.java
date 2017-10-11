package cndoppler.cn.wifiprobe.utils;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class LineMeasurePoint extends BasePoint
{
    public LineMeasurePoint(int x, int y)
    {
        super(x, y);
    }

    @Override
    public void drawMeasure(int startX, int startY, int endX, int endY, Canvas canvas, Paint paint)
    {
        canvas.drawLine(startX,startY,endX,endY,paint);
    }
}
