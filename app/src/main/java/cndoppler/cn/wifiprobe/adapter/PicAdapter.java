package cndoppler.cn.wifiprobe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cndoppler.cn.wifiprobe.R;

/**
 * Created by admin on 2017/9/25.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.MyViewHolder>
{
    private Context context;

    public PicAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public PicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = View.inflate(context, R.layout.item_pic,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicAdapter.MyViewHolder holder, int position)
    {
        holder.iv.setImageResource(R.drawable.icon_search);
    }

    @Override
    public int getItemCount()
    {
        return 5;
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
