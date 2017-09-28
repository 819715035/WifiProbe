package cndoppler.cn.wifiprobe.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.ScreenUtils;

/**
 * Created by admin on 2017/09/26 0026.
 */

public class PicViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<PicData> picDatas;

    public PicViewPagerAdapter(Context context,ArrayList<PicData> picDatas)
    {
        this.picDatas = picDatas;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return picDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imageView = new ImageView(context);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ScreenUtils.getScreenHeight(context);
        params.height = ScreenUtils.getScreenHeight(context);
        imageView.setLayoutParams(params);
        imageView.setImageURI(Uri.parse(picDatas.get(position).getPath()));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }
}
