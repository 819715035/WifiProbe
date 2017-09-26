package cndoppler.cn.wifiprobe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.activity.ReadPicActivity;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.PicData;

/**
 * Created by admin on 2017/9/25.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<PicData> picDatas;

    public PicAdapter(Context context,CheckProgrem checkProgrem)
    {
        this.picDatas = checkProgrem.getPic();
        this.context = context;
    }

    @Override
    public PicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = View.inflate(context, R.layout.item_pic,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicAdapter.MyViewHolder holder, final int position)
    {
        final PicData pic = picDatas.get(position);
        holder.iv.setImageResource(pic.getPath());
        holder.iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context,ReadPicActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("picdata",picDatas);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return picDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            iv = itemView.findViewById(R.id.pic_iv);
        }
    }
}
