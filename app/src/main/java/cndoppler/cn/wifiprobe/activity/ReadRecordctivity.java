package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.CheckProgremAdapter;
import cndoppler.cn.wifiprobe.adapter.PicAdapter;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;

public class ReadRecordctivity extends BaseActivity {

    private View backIv;
    private TextView titleTv;
    private TextView patientTv;
    private RecyclerView picRv;
    private ListView check_lv;
    private TextView nocheck_tv;
    private Patient patient;
    private TextView nopicTv;
    private CheckProgrem picdatas;

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
        check_lv = findViewById(R.id.check_lv);
        nocheck_tv = findViewById(R.id.nocheck_tv);
        nopicTv = findViewById(R.id.nopic_tv);
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
        check_lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                setPicdataAdapter((int) l);
            }
        });
    }

    /**
     * 设置图片
     */
    private void setPicdataAdapter(int id)
    {
        picdatas = DataSupport.find(CheckProgrem.class,patient.getCheckProgrems().get(id).getId(),true);
        if (picdatas!=null && picdatas.getPic()!=null && picdatas.getPic().size()>0)
        {
            nopicTv.setVisibility(View.GONE);
        }else{
            nopicTv.setVisibility(View.VISIBLE);
        }
        picRv.setAdapter(new PicAdapter(this,picdatas));
        picRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void initData() {
        titleTv.setText("查看病历");
        Intent intent = getIntent();
        Patient p = (Patient) intent.getSerializableExtra("patient");
        patient = DataSupport.find(Patient.class, p.getId(), true);
        if (patient!=null){
            patientTv.setText(patient.toString());
            getCheckProgrem();
        }
    }

    /**
     *
     *设置检查项目的适配器
     */
    private void getCheckProgrem()
    {
        if(patient.getCheckProgrems()!=null&&patient.getCheckProgrems().size()>0){
            check_lv.setAdapter(new CheckProgremAdapter(this,patient.getCheckProgrems()));
            setPicdataAdapter(0);
            nocheck_tv.setVisibility(View.GONE);
        }else{
            nocheck_tv.setVisibility(View.VISIBLE);
        }
    }
}
