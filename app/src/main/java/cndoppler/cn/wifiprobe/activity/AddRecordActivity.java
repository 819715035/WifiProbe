package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.ToastUtils;

import static cndoppler.cn.wifiprobe.activity.RecordListActivity.ADD_PATIENT_RESULT_CODE;

public class AddRecordActivity extends BaseActivity {

    private Button addPatientInfoBtn;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_add_record);
    }

    @Override
    public void initWidget() {
        addPatientInfoBtn = findViewById(R.id.add_patient_info_btn);
        addPatientInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatientInfo();
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 添加病人信息
     */
    private void addPatientInfo() {
        Patient patient = new Patient();
        patient.setBirthday(new Date().getTime());
        patient.setBody("血管");
        patient.setBodyHeight(172);
        patient.setDeparment("血管科");
        patient.setDoctor("xx主任");
        patient.setName("xxx");
        patient.setNumber("123456789");
        patient.setPathogeny("未知病因");
        patient.setWeight(55);
        patient.setSex(0);
        if (patient.save()){
            ToastUtils.showToastShort(this,"保存成功");
            Intent intent = new Intent();
            intent.putExtra("patient",patient);
            setResult(RecordListActivity.ADD_PATIENT_RESULT_CODE,intent);
            finish();
        }else{
            ToastUtils.showToastShort(this,"保存失败");
        }
    }
}
