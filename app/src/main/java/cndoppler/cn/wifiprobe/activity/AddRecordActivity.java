package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.DateUtils;
import cndoppler.cn.wifiprobe.utils.ToastUtils;

import static cndoppler.cn.wifiprobe.activity.RecordListActivity.ADD_PATIENT_RESULT_CODE;

public class AddRecordActivity extends BaseActivity {

    private Button addPatientInfoBtn;
    private ImageView backIv;
    private TextView titleTv;
    private TextView numberTv;
    private EditText nameEt;
    private RadioGroup sexRg;
    private TextView birthdayTv;
    private DatePicker birthdayDp;
    private String patientNum;
    private int sex;
    private int year;
    private int month;
    private int day;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_add_record);
    }

    @Override
    public void initWidget() {
        addPatientInfoBtn = findViewById(R.id.add_patient_info_btn);
        backIv = findViewById(R.id.back_iv);
        titleTv = findViewById(R.id.title_tv);
        numberTv = findViewById(R.id.number_tv);
        nameEt = findViewById(R.id.name_et);
        sexRg = findViewById(R.id.sex_rg);
        birthdayTv = findViewById(R.id.birthday_tv);
        birthdayDp = findViewById(R.id.birthday_dp);

    }

    @Override
    public void initData() {
        titleTv.setText("新增病人档案");
        patientNum = DateUtils.dateAndTime();
        numberTv.setText(patientNum);
        sexRg.check(R.id.man_rb);
        //默认生日为今天
        Calendar c =Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH); 
        day = c.get(Calendar.DAY_OF_MONTH);
        setListener();
    }

    private void setListener() {

        //添加用户信息
        addPatientInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatientInfo();
            }
        });
        //返回上一页
        backIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        //监听性别单选
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i){
                    case R.id.man_rb:
                        sex = 0;
                        break;
                    case R.id.woman_rb:
                        sex = 1;
                        break;
                    case R.id.unknown_rb:
                        sex = 2;
                        break;
                }
            }
        });

        birthdayDp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                AddRecordActivity.this.year=year;
                AddRecordActivity.this.month=monthOfYear;
                AddRecordActivity.this.day=dayOfMonth;
                birthdayTv.setText("出生日期："+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        });

    }

    public String getName(){
        String name;
        if (TextUtils.isEmpty(nameEt.getText().toString().trim())){
            name = "临时病人";
        }else{
            name = nameEt.getText().toString().trim();
        }
        return name;
    }

    public long getBirthday(){
        Calendar c =Calendar.getInstance();
        c.set(year,month,day);
        return c.getTimeInMillis();
    }


    /**
     * 添加病人信息
     */
    private void addPatientInfo() {

        Patient patient = new Patient();
        patient.setNumber(patientNum);
        patient.setName(getName());
        patient.setSex(sex);
        patient.setBirthday(getBirthday());
        patient.setAge(new Date().getYear()-year+1900);
        patient.setDate(new Date().getTime());
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
