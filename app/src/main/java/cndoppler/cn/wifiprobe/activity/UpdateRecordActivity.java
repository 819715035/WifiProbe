package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.LogUtils;

public class UpdateRecordActivity extends BaseActivity {

    private Button updateBtn;
    private Patient patient;
    private int position;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_update_record);
    }

    @Override
    public void initWidget() {
        updateBtn = findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patient.update(patient.getId());
                Intent intent = new Intent();
                intent.putExtra("patient",patient);
                intent.putExtra("position",position);
                setResult(RecordListActivity.UPDATA_RESULT_CODE,intent);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        patient = (Patient) intent.getSerializableExtra("patient");
        patient.setName("病人");
        LogUtils.e(patient.toString());
    }

}
