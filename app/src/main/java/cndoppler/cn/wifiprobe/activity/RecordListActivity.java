package cndoppler.cn.wifiprobe.activity;

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

import java.io.File;
import java.util.ArrayList;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.adapter.PatientAdapter;
import cndoppler.cn.wifiprobe.adapter.SearchTextviewAdapter;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.LogUtils;
import cndoppler.cn.wifiprobe.utils.ToastUtils;
import leltek.viewer.model.WifiProbe;

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
        patientLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                startOpenScanActivity(i);
                return true;
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

    private void startOpenScanActivity(final int position)
    {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("您确定要为"+patients.get(position).getName()+"病人做检查吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (WifiProbe.getDefault().isConnected())
                        {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("patient", patients.get(position));
                            openActivity(ScanActivity.class, bundle);
                        }else{
                            ToastUtils.showToastShort(RecordListActivity.this,"无线探头连接异常，请连接成功后再操作");
                            WifiProbe.getDefault().initialize();
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();

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
                    public void onClick(DialogInterface dialogInterface, int position)
                    {
                        delectPatient(patient);
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
     * 删除病人信息
     * @param patient
     */
    private void delectPatient(Patient patient)
    {
        Patient p = DataSupport.find(Patient.class,patient.getId(),true);
        for (int i=0;i<p.getCheckProgrems().size();i++){
            //得到检查项目
            CheckProgrem checkProgrem = DataSupport.find(CheckProgrem.class,p.getCheckProgrems().get(i).getId(),true);
            for (int j=0;j<checkProgrem.getPic().size();j++){
                String path = checkProgrem.getPic().get(j).getPath();
                LogUtils.d("图片路径"+path);
                File file = new File(path);
                if (file.exists()){
                    file.delete();
                }
            }
        }
        DataSupport.delete(Patient.class,p.getId());
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
