package cndoppler.cn.wifiprobe.activity;

import android.view.View;
import android.widget.Button;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button recordBtn;
    private Button scanBtn;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initWidget() {
        recordBtn = findViewById(R.id.record_btn);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到档案界面
                openActivity(RecordListActivity.class);
            }
        });
        scanBtn = findViewById(R.id.scan_btn);
    }

    @Override
    public void initData() {

    }

}
