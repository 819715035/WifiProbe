package cndoppler.cn.wifiprobe.activity;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PicAdapter;
import cndoppler.cn.wifiprobe.adapter.PicViewPagerAdapter;
import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.BaseActivity;

public class ReadPicActivity extends BaseActivity
{
private ArrayList<PicData> picdatas;
    private ViewPager picVp;
    private int position;
    @Override
    public void setContent()
    {
        setContentView(R.layout.activity_read_pic);
    }

    @Override
    public void initWidget()
    {
        picVp = findViewById(R.id.pic_vp);
    }

    @Override
    public void initData()
    {
        picdatas = (ArrayList<PicData>) getIntent().getSerializableExtra("picdata");
        position = getIntent().getIntExtra("position",0);
        if (picdatas!=null && picdatas.size()>0){
            picVp.setAdapter(new PicViewPagerAdapter(this,picdatas));
            picVp.setCurrentItem(position);
        }
    }

}
