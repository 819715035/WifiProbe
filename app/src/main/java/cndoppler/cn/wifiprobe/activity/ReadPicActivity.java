package cndoppler.cn.wifiprobe.activity;

import android.widget.ImageView;

import java.util.ArrayList;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PicAdapter;
import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.BaseActivity;

public class ReadPicActivity extends BaseActivity
{
private ArrayList<PicData> picdatas;
    private ImageView picIv;
    @Override
    public void setContent()
    {
        setContentView(R.layout.activity_read_pic);
    }

    @Override
    public void initWidget()
    {
        picIv = findViewById(R.id.pic_iv);
    }

    @Override
    public void initData()
    {
        picdatas = (ArrayList<PicData>) getIntent().getSerializableExtra("picdata");
        if (picdatas!=null && picdatas.size()>0){
            picIv.setImageResource(picdatas.get(0).getPath());
        }
    }

}
