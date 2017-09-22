package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PatientAdapter;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.LogUtils;

public class RecordListActivity extends BaseActivity {

    private Button addRecordBtn;
    private ListView patientLv;
    private List<Patient> patients;
    private PatientAdapter adapter;
    private TextView noRecordTv;
    public static final int UPDATE_REQUEST_CODE = 2;
    public static final int UPDATA_RESULT_CODE = 3;
    public static final int ADD_PATIENT_CODE = 0;
    public static final int ADD_PATIENT_RESULT_CODE = 1;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_record_list);
    }

    @Override
    public void initWidget() {
        addRecordBtn = findViewById(R.id.add_record_btn);
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordListActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,ADD_PATIENT_CODE);
            }
        });
        patientLv = findViewById(R.id.patient_lv);
        patientLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("patient",patients.get(i));
                openActivity(ReadRecordctivity.class,bundle);
            }
        });
        noRecordTv = findViewById(R.id.norecord_tv);
    }

    @Override
    public void initData() {
        getPatientData();
        if (patients!=null && patients.size()>0){
            setListviewData();
        }
    }

    /**
     * 删除病人信息
     * @param patient
     */
    private void delectPatientData(Patient patient) {
        DataSupport.delete(Patient.class,patient.getId());
        patients.remove(patient);
        adapter.notifyDataSetChanged();
        if (patients!=null && patients.size()<=0){
            noRecordTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取到本地数据库病人的信息
     */
    public void getPatientData(){
        patients = DataSupport.findAll(Patient.class);
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
        }else if (resultCode == UPDATA_RESULT_CODE){
            Patient patient = (Patient) data.getSerializableExtra("patient");
            int position = data.getIntExtra("position",0);
            patients.remove(position);
            patients.add(position,patient);
            for (int i = 0;i<patients.size();i++){
                LogUtils.d(patients.get(i).toString());
            }
            adapter.notifyDataSetChanged();
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

            @Override
            public void onUpdateListener(Patient patient,int position) {
                updatePatient(patient,position);
            }
        });
        patientLv.setAdapter(adapter);
        noRecordTv.setVisibility(View.GONE);
    }

    /**
     * 更新病员数据
     * @param patient
     */
    private void updatePatient(Patient patient,int position) {
        Intent intent = new Intent(RecordListActivity.this,UpdateRecordActivity.class);
        intent.putExtra("patient",patient);
        intent.putExtra("position",position);
        LogUtils.e(patient.toString());
        startActivityForResult(intent,UPDATE_REQUEST_CODE);
    }
}
