package cndoppler.cn.wifiprobe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PatientAdapter;
import cndoppler.cn.wifiprobe.adapter.SearchTextviewAdapter;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
public class RecordListActivity extends BaseActivity {

    private Button addRecordBtn;
    private ListView patientLv;
    private ArrayList<Patient> patients;
    private PatientAdapter adapter;
    private TextView noRecordTv;
    private ImageView backIv;
    public static final int ADD_PATIENT_CODE = 0;
    public static final int ADD_PATIENT_RESULT_CODE = 1;
    private AutoCompleteTextView searchAt;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_record_list);
    }

    @Override
    public void initWidget() {
        addRecordBtn = findViewById(R.id.add_record_btn);
        patientLv = findViewById(R.id.patient_lv);
        noRecordTv = findViewById(R.id.norecord_tv);
        backIv = findViewById(R.id.back_iv);
        searchAt = findViewById(R.id.search_record_at);
        setlistener();
    }

    private void setlistener()
    {
        backIv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        patientLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startReadPatientActivity(patients.get(i));
            }
        });
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordListActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,ADD_PATIENT_CODE);
            }
        });
        searchAt.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                startReadPatientActivity((Patient) adapterView.getItemAtPosition((int) l));
                searchAt.setText("");
            }
        });
    }

    /**
     * 跳转到查看病人界面
     * @param patient
     */
    private void startReadPatientActivity(Patient patient)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("patient",patient);
        openActivity(ReadRecordctivity.class,bundle);
    }

    @Override
    public void initData() {
        getPatientData();
        if (patients!=null && patients.size()>0){
            setListviewData();
            searchAt.setAdapter(new SearchTextviewAdapter(patients,RecordListActivity.this));
        }
    }

    /**
     * 删除病人信息
     * @param patient
     */
    private void delectPatientData(final Patient patient) {

        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("您确定要删除病人及其图片吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        DataSupport.delete(Patient.class,patient.getId());
                        patients.remove(patient);
                        adapter.notifyDataSetChanged();
                        if (patients!=null && patients.size()<=0){
                            noRecordTv.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .create().show();
    }

    /**
     * 获取到本地数据库病人的信息
     */
    public void getPatientData(){
        patients = (ArrayList<Patient>) DataSupport.findAll(Patient.class,true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ADD_PATIENT_RESULT_CODE){
            Patient patient = (Patient) data.getSerializableExtra("patient");
            //更新列表
            if (patient!=null){
                patients.add(patient);
                if (adapter==null){
                    setListviewData();
                }
                noRecordTv.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                patientLv.setSelection(patients.size()-1);
            }
        }
    }

    /**
     * 为listview绑定数据
     */
    private void setListviewData() {
        adapter = new PatientAdapter(this, patients, new PatientAdapter.OnPatientItemClickListener() {
            @Override
            public void onDelectListener(Patient patient) {
                delectPatientData(patient);
            }
        });
        patientLv.setAdapter(adapter);
        noRecordTv.setVisibility(View.GONE);
    }
}
