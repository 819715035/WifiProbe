package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PicAdapter;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;

public class ReadRecordctivity extends BaseActivity {

    private View backIv;
    private TextView titleTv;
    private TextView patientTv;
    private RecyclerView picRv;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_read_recordctivity);

    }

    @Override
    public void initWidget() {
        backIv = findViewById(R.id.back_iv);
        titleTv = findViewById(R.id.title_tv);
        patientTv = findViewById(R.id.patient_tv);
        picRv = findViewById(R.id.pic_rv);
        setListener();
    }

    private void setListener()
    {
        backIv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        titleTv.setText("查看病历");
        Intent intent = getIntent();
        Patient patient = (Patient) intent.getSerializableExtra("patient");
        if (patient!=null){
            patientTv.setText(patient.toString());
        }
        List<CheckProgrem> cp = DataSupport.findAll(CheckProgrem.class,true);
        for (int i=0;i<cp.size();i++){
            patientTv.append(cp.get(i).getBody());
        }
        picRv.setAdapter(new PicAdapter(this));
        picRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
}
