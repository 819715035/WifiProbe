package cndoppler.cn.wifiprobe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.utils.DateUtils;

/**
 * Created by admin on 2017/9/25.
 */

public class CheckProgremAdapter extends BaseAdapter
{
    private Context context;
    private List<CheckProgrem> checkProgrems;

    public CheckProgremAdapter(Context context, List<CheckProgrem> checkProgrems)
    {
        this.context = context;
        this.checkProgrems = checkProgrems;
    }

    @Override
    public int getCount()
    {
        return checkProgrems.size();
    }

    @Override
    public Object getItem(int i)
    {
        return checkProgrems.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View converview, ViewGroup viewGroup)
    {
        Holder holder;
        if (converview==null){
            holder = new Holder();
            converview = View.inflate(context, R.layout.item_checkprogrem,null);
            holder.positionTv = converview.findViewById(R.id.num_tv);
            holder.bodyTv = converview.findViewById(R.id.body_tv);
            holder.dateTv = converview.findViewById(R.id.date_tv);
            converview.setTag(holder);
        }else {
            holder = (Holder) converview.getTag();
        }
        CheckProgrem cp = checkProgrems.get(position);
        holder.positionTv.setText(position+1+"、");
        holder.bodyTv.setText(cp.getBody());
        holder.dateTv.setText(DateUtils.formatDatetime(new Date(cp.getDate())));
        return converview;
    }

    class Holder{
        private TextView positionTv;
        private TextView bodyTv;
        private TextView dateTv;
    }
}
