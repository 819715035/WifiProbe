package cndoppler.cn.wifiprobe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.DateUtils;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class PatientAdapter extends BaseAdapter {

    private Context context;
    private List<Patient> patients;
    private OnPatientItemClickListener listener;

    public PatientAdapter(Context context, List<Patient> patients, OnPatientItemClickListener listener) {
        this.context = context;
        this.patients = patients;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return patients.size();
    }

    @Override
    public Object getItem(int i) {
        return patients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup viewGroup) {
        Holder holder;
        if (converView == null){
            holder = new Holder();
            converView = View.inflate(context, R.layout.item_patient,null);
            holder.patientTv = converView.findViewById(R.id.patient_tv);
            holder.doctorTv = converView.findViewById(R.id.doctor_tv);
            holder.delectBtn = converView.findViewById(R.id.delect_btn);
            converView.setTag(holder);
        }else{
            holder = (Holder) converView.getTag();
        }
        if (position%2==0){
            converView.setBackgroundColor(Color.parseColor("#88cccccc"));
        }else{
            converView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        final Patient patient = patients.get(position);
        holder.patientTv.setText("病人ID："+patient.getNumber()+"   名字："+patient.getName()+"   年龄："+patient.getAge());
        holder.doctorTv.setText("建表时间："+ DateUtils.formatDatetime(new Date(patient.getDate())));
        holder.delectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onDelectListener(patient);
                }
            }
        });
        return converView;
    }

    class Holder{
        private TextView patientTv;
        private TextView doctorTv;
        private Button delectBtn;
    }

    public interface OnPatientItemClickListener{
        public void onDelectListener(Patient patient);
    }
}
