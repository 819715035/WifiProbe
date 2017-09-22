package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.ToastUtils;

public class ReadRecordctivity extends BaseActivity {

    @Override
    public void setContent() {
        setContentView(R.layout.activity_read_recordctivity);
        Intent intent = getIntent();
        Patient patient = (Patient) intent.getSerializableExtra("patient");
        ToastUtils.showToastShort(this,patient.toString());
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initData() {

    }
}
