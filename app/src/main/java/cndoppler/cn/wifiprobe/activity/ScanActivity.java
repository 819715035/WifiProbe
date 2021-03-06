package cndoppler.cn.wifiprobe.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Date;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.bean.CheckProgrem;
import cndoppler.cn.wifiprobe.bean.Patient;
import cndoppler.cn.wifiprobe.bean.PicData;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.BasePopuWindows;
import cndoppler.cn.wifiprobe.utils.BitmapUtils;
import cndoppler.cn.wifiprobe.utils.SDCUtils;
import cndoppler.cn.wifiprobe.utils.ToastUtils;
import cndoppler.cn.wifiprobe.widgets.UsImageView;
import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

public class ScanActivity extends BaseActivity implements Probe.ScanListener,Probe.CineBufferListener,
        Probe.BatteryListener, Probe.TemperatureListener,View.OnClickListener{

    private Probe probe;//探头类
    private Button mToggleScan;
    private Button mBMode;//b模式
    private Button mCMode;//cf模式
    private Button mFit;//fit模式
    private UsImageView mImageView;//扫描成像控件
    private TextView mCineBufferCount; //gine提示
    private SeekBar mSeekBarGain;//gain滚动条
    private SeekBar mSeekBarDr;//dynamic range滚动条
    private SeekBar mSeekBarTgc1;//tgc1滚动条
    private SeekBar mSeekBarTgc2;//tgc2滚动条
    private SeekBar mSeekBarTgc3;//tgc3滚动条
    private SeekBar mSeekBarTgc4;//tgc4滚动条
    private SeekBar mSeekBarPersistence;//persistence滚动条
    private SeekBar mSeekBarEnhancement;//enhancement滚动条
    private Button mResetAllTgc;//重置tgc按钮
    private NumberPicker mNumberPicker;//数字选择器
    private TextView mWedthTv,mPowerTv,mDepthTv,mGainTv,mDrTv,mGrayMapTv,mPersistenceTv,mEnhancelevelTv,mTgc1tV,mTgc2Tv,mTgc3Tv,mTgc4Tv,mColorGainTv,mColorPersistenceTv
            ,mColorPrfTv,mColorThresholTv,mFrameRateTv,mFreqTv;
    private TextView mCGainTv,mCPersistenceTv,mCPrfTv,mCThresHoldTv;
    private TextView mPatientIdTv,mPatientNameTv,mPatientAgeTv;
    private SeekBar mDepthSb,mColorGainSb,mColorPersistenceSb,mColorPrfSb,mColorThresholdSb;
    private Button saveimgBtn;
    private Patient patient;
    private CheckProgrem cp;
    private Button mMeasureBtn;
    private BasePopuWindows popuWindows;
    private Button removeCurrentPointBtn;
    private RadioGroup measureRg;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_scan);
    }

    @Override
    public void initWidget() {
        // 得到探头对象
        probe = WifiProbe.getDefault();
        probe.setScanListener(this);
        probe.setCineBufferListener(this);
        probe.setBatteryListener(this);
        probe.setTemperatureListener(this);
        mToggleScan = mFindViewById(R.id.toogle_scan);
        mToggleScan.setOnClickListener(this);
        mBMode = mFindViewById(R.id.bMode);
        mBMode.setOnClickListener(this);
        mCMode = mFindViewById(R.id.cMode);
        mCMode.setOnClickListener(this);
        mFit = mFindViewById(R.id.fit);
        mFit.setOnClickListener(this);
        mImageView = mFindViewById(R.id.image_view);
        mCineBufferCount = mFindViewById(R.id.cine_buffer_count);
        saveimgBtn = findViewById(R.id.saveimg_btn);
        saveimgBtn.setOnClickListener(this);
        mWedthTv = mFindViewById(R.id.wedth_tv);
        mWedthTv.setText("温度："+probe.getTemperature()+" °");
        mPowerTv = mFindViewById(R.id.power_tv);
        mPowerTv.setText("电量："+probe.getBatteryLevel()+"%");
        mDepthTv = mFindViewById(R.id.depth_tv);
        mGainTv = mFindViewById(R.id.gain_tv);
        mDrTv = mFindViewById(R.id.dr_tv);
        mGrayMapTv = mFindViewById(R.id.graymap_tv);
        mPersistenceTv = mFindViewById(R.id.persistence_tv);
        mEnhancelevelTv = mFindViewById(R.id.enhancelevel_tv);
        mTgc1tV = mFindViewById(R.id.tg1_tv);
        mTgc2Tv= mFindViewById(R.id.tg2_tv);
        mTgc3Tv = mFindViewById(R.id.tg3_tv);
        mTgc4Tv = mFindViewById(R.id.tg4_tv);
        mColorGainTv = mFindViewById(R.id.colorgain_tv);
        mColorPersistenceTv = mFindViewById(R.id.colorPersistence_tv);
        mColorPrfTv = mFindViewById(R.id.colorprf_tv);
        mColorThresholTv = mFindViewById(R.id.colorThresHold_tv);
        mSeekBarGain = mFindViewById(R.id.seekBarGain);
        mSeekBarGain.setProgress(probe.getGain());
        mSeekBarGain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setGain(progress);
                mGainTv.setText("增益："+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarDr = mFindViewById(R.id.seekBarDr);
        mSeekBarDr.setProgress(probe.getDr());
        mSeekBarDr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setDr(progress);
                mDrTv.setText("动态范围："+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarTgc1 = mFindViewById(R.id.seekBarTgc1);
        mSeekBarTgc1.setProgress(probe.getTgc1());
        mSeekBarTgc1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc1(progress);
                mTgc1tV.setText("tgc1:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarTgc2 = mFindViewById(R.id.seekBarTgc2);
        mSeekBarTgc2.setProgress(probe.getTgc2());
        mSeekBarTgc2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc2(progress);
                mTgc2Tv.setText("tgc2:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarTgc3 = mFindViewById(R.id.seekBarTgc3);
        mSeekBarTgc3.setProgress(probe.getTgc3());
        mSeekBarTgc3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc3(progress);
                mTgc3Tv.setText("tgc3:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarTgc4 = mFindViewById(R.id.seekBarTgc4);
        mSeekBarTgc4.setProgress(probe.getTgc4());
        mSeekBarTgc4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc4(progress);
                mTgc4Tv.setText("tgc4:"+progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mResetAllTgc = mFindViewById(R.id.resetAllTgc);
        mResetAllTgc.setOnClickListener(this);
        mSeekBarPersistence = mFindViewById(R.id.seekBarPersistence);
        mSeekBarPersistence.setProgress(probe.getPersistence());
        mSeekBarPersistence.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setPersistence(progress);
                mPersistenceTv.setText("帧相关"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarEnhancement = mFindViewById(R.id.seekBarEnhancement);
        mSeekBarEnhancement.setProgress(probe.getEnhanceLevel());
        mSeekBarEnhancement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setEnhanceLevel(progress);
                mEnhancelevelTv.setText("增强等级："+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mNumberPicker = mFindViewById(R.id.np);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(probe.getGrayMapMaxValue());
        mNumberPicker.setWrapSelectorWheel(false);
        mNumberPicker.setValue(probe.getGrayMap());
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                probe.setGrayMap(newVal);
                mGrayMapTv.setText("灰度图："+newVal);
            }
        });
        mDepthSb = mFindViewById(R.id.seekBarDepth);
        mDepthSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i){
                    case 0:
                        probe.setDepth(Probe.EnumDepth.LinearDepth_32);
                        break;
                    case 1:
                        probe.setDepth(Probe.EnumDepth.LinearDepth_63);
                        break;
                    case 2:
                        probe.setDepth(Probe.EnumDepth.ConvexDepth_126);
                        break;
                    case 3:
                        probe.setDepth(Probe.EnumDepth.ConvexDepth_189);
                        break;
                }
                mDepthTv.setText("深度："+probe.getDepth());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mColorGainSb = mFindViewById(R.id.seekBarColorGain);
        mColorGainSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                probe.setColorGain(i);
                mColorGainTv.setText("彩色增益："+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mColorPersistenceSb = mFindViewById(R.id.seekBarColorPersistence);
        mColorPersistenceSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                probe.setColorPersistence(i);
                mColorPersistenceTv.setText("彩色帧相关："+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mColorPrfSb = mFindViewById(R.id.seekBarColorPrf);
        mColorPrfSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch(i){
                    case 0:
                        probe.setColorPrf(2);
                        mColorPrfTv.setText("prf:2kHZ");
                        break;
                    case 1:
                        probe.setColorPrf(4);
                        mColorPrfTv.setText("prf:4kHZ");
                        break;
                    case 2:
                        probe.setColorPrf(5);
                        mColorPrfTv.setText("prf:5kHZ");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mColorThresholdSb = mFindViewById(R.id.seekBarColorThreshold);
        mColorThresholdSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch(i){
                    case 0:
                        probe.setColorThreshold(1);
                        mColorThresholTv.setText("colorthreshol:"+1);
                        break;
                    case 1:
                        probe.setColorThreshold(2);
                        mColorThresholTv.setText("colorthreshol:"+2);
                        break;
                    case 2:
                        probe.setColorThreshold(3);
                        mColorThresholTv.setText("colorthreshol:"+3);
                        break;
                    case 3:
                        probe.setColorThreshold(4);
                        mColorThresholTv.setText("colorthreshol:"+4);
                        break;
                    case 4:
                        probe.setColorThreshold(5);
                        mColorThresholTv.setText("colorthreshol:"+5);
                        break;
                    case 5:
                        probe.setColorThreshold(6);
                        mColorThresholTv.setText("colorthreshol:"+6);
                        break;
                    case 6:
                        probe.setColorThreshold(7);
                        mColorThresholTv.setText("colorthreshol:"+7);
                        break;
                    case 7:
                        probe.setColorThreshold(8);
                        mColorThresholTv.setText("colorthreshol:"+8);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mFrameRateTv = mFindViewById(R.id.frameRate_tv);
        mFrameRateTv.setText("帧频率:"+probe.getFrameRate());
        mFreqTv = mFindViewById(R.id.freq_tv);
        mFreqTv.setText("频率："+probe.getFreq()+"hz");
        mCGainTv = findViewById(R.id.cgain_tv);
        mCPersistenceTv = findViewById(R.id.cPersistence_tv);
        mCPrfTv = findViewById(R.id.cPrf_tv);
        mCThresHoldTv = findViewById(R.id.cThresHold_tv);
        mPatientIdTv = findViewById(R.id.patient_tv);
        mPatientNameTv = findViewById(R.id.patientname_tv);
        mPatientAgeTv = findViewById(R.id.patientage_tv);
        mMeasureBtn = findViewById(R.id.measure_btn);
        mMeasureBtn.setOnClickListener(this);
        View showView = View.inflate(this,R.layout.item_measure,null);
        popuWindows = new BasePopuWindows(this,BasePopuWindows.TYPE_MATCH_PARENT,showView,false);
        removeCurrentPointBtn = showView.findViewById(R.id.back_btn);
        measureRg = showView.findViewById(R.id.measure_rg);
        setListener();
    }

    private void setListener()
    {
        removeCurrentPointBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mImageView.removeDistanceCurrentPoint();
            }
        });
        measureRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i)
            {
                switch (i){
                    case R.id.dmeasure_rb:
                        mImageView.setMeasureType(mImageView.DISTANCE);
                        break;
                    case R.id.rmeasure_rb:
                        mImageView.setMeasureType(mImageView.RECTANGLE);
                        break;
                    case R.id.cmeasure_rb:
                        mImageView.setMeasureType(mImageView.OVAL);
                        break;
                }
            }
        });
    }

    @Override
    public void initData()
    {

    }

    private void switchFit() {
        mImageView.switchFit();
        if (mImageView.isFitWidth()) {
            mFit.setText(R.string.fit_height);
        } else {
            mFit.setText(R.string.fit_width);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("patient");
        //添加病人信息
        if (patient == null){
            patient = new Patient();
        }else {
            patient = DataSupport.find(Patient.class,patient.getId(),true);
        }
        mPatientIdTv.setText("病人ID："+patient.getNumber());
        mPatientNameTv.setText("病人名字："+patient.getName());
        mPatientAgeTv.setText("岁数："+patient.getAge());
    }

    @Override
    protected void onStop() {
        super.onStop();
        probe.stopScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (probe.getMode() == Probe.EnumMode.MODE_C) {
            //切换到b模式
            probe.swithToBMode();
        }
        if (probe.getMode() == Probe.EnumMode.MODE_B && !probe.isLive()) {
            //开始扫描
            probe.startScan();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onModeSwitched(Probe.EnumMode mode) {
        if (mode == Probe.EnumMode.MODE_B) {
            ToastUtils.showToastShort(this,"switched to B mode");
        }
        else if (mode == Probe.EnumMode.MODE_C) {
            ToastUtils.showToastShort(this,"switched to Color mode");
        }
    }

    @Override
    public void onModeSwitchingError() {
        ToastUtils.showToastShort(this,"Switch mode failed");
    }

    @Override
    public void onConnectionError() {
        ToastUtils.showToastShort(this,"Connection Closed");
        //连接错误时，跳转到主页
        //openActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onScanStarted() {
        //开始扫描
        mToggleScan.setText(R.string.freeze);
        ToastUtils.showToastShort(this,"Scan Started");
    }

    @Override
    public void onScanStopped() {
        //停止扫描
        mToggleScan.setText(R.string.live);
        ToastUtils.showToastShort(this,"Scan Stopped");
    }

    @Override
    public void onNewFrameReady(Probe.Frame frame, Bitmap bitmap) {
        if (frame.mode == probe.getMode()) {
            mImageView.setImageBitmap(bitmap);
        }
        Probe.CModeFrameData cModeFrameData = frame.cModeFrameData;
        if (cModeFrameData != null) {
            mImageView.setParams(cModeFrameData.originXPx, cModeFrameData.originYPx,
                    cModeFrameData.rPx);
        }
    }

    /**
     * 設定depth成功
     *
     * @param newDepth newDepth
     */
    @Override
    public void onDepthSet(Probe.EnumDepth newDepth) {

    }

    @Override
    public void onDepthSetError() {

    }

    @Override
    public void onImageBufferOverflow() {
        ToastUtils.showToastShort(this,"This hardware is not capable of image processing.");
    }

    @Override
    public void onCineBufferCountIncreased(int newCineBufferCount) {
        mCineBufferCount.setText(String.valueOf(newCineBufferCount));
    }

    @Override
    public void onCineBufferCleared() {
        mCineBufferCount.setText(String.valueOf(0));
    }

    @Override
    public void onBatteryLevelChanged(int newBatteryLevel) {
        mPowerTv.setText("电量："+newBatteryLevel+"%");
    }

    @Override
    public void onBatteryLevelTooLow(int BatteryLevel) {
        ToastUtils.showToastShort(this,"Battery level too low, now is " + BatteryLevel + "%");
    }

    @Override
    public void onTemperatureChanged(int newTemperature) {
        mWedthTv.setText("温度："+newTemperature+" °");
    }

    @Override
    public void onTemperatureOverHeated(int temperature) {
        ToastUtils.showToastShort(this,"Temperature over heated, now is " + temperature + " °");
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.toogle_scan:
                //冻结或激活扫描
                if (probe.isLive())
                {
                    //停止扫描
                    probe.stopScan();
                }else {
                    //开始扫描
                    probe.startScan();
                    mFreqTv.setText("频率："+probe.getFreq()+"hz");
                }
                break;
            //b模式
            case R.id.bMode:
                //切换到b模式
                probe.swithToBMode();
                hiddenOrShowCFModeLayout(true);
                break;
            case R.id.cMode:
                //切换到cf模式
                probe.swithToCMode();
                hiddenOrShowCFModeLayout(false);
                break;
            case R.id.fit:
                //切换到fit模式
                switchFit();
                break;
            case R.id.resetAllTgc:
                //重置所有tgc
                probe.resetAllTgc();
                mSeekBarTgc1.setProgress(probe.getTgc1());
                mSeekBarTgc2.setProgress(probe.getTgc2());
                mSeekBarTgc3.setProgress(probe.getTgc3());
                mSeekBarTgc4.setProgress(probe.getTgc4());
                break;
            case R.id.saveimg_btn:
                //保存图片
                Bitmap currentBitmap = BitmapUtils.convertViewToBitmap(mImageView);
                if (BitmapUtils.getBitmapSize(currentBitmap) < SDCUtils.getSDFreeSize())
                {
                    String path = BitmapUtils.saveBitmapInExternalStorage(BitmapUtils.convertViewToBitmap(mImageView), this);
                    savePatientInfo(path);
                }else {
                    ToastUtils.showToastLong(this,"内存不足，请删除文件后再保存");
                }
                break;
            case R.id.measure_btn:
                //测量
                if (popuWindows.mPopupWindow.isShowing()){
                    popuWindows.dismiss();
                }else{
                    popuWindows.showAsRightTop(mMeasureBtn,0,0);
                }
                break;
        }
    }


    /**
     * 隐藏或显示cf模式的控件
     */
    private void hiddenOrShowCFModeLayout(boolean hiddlen)
    {
        hiddenLayout(hiddlen,mColorGainSb,
                mColorPersistenceSb,
                mColorPrfSb,
                mColorThresholdSb,
                mCGainTv,
                mCPersistenceTv,
                mCPrfTv,
                mCThresHoldTv,
                mColorGainTv,
                mColorPersistenceTv,
                mColorPrfTv,
                mColorThresholTv);
    }

    private void hiddenLayout(boolean hiddlen,View... views)
    {
        int tag;
        if (hiddlen){
            tag = View.GONE;
        }else{
            tag = View.VISIBLE;
        }
        for (int i=0;i<views.length;i++){
            views[i].setVisibility(tag);
        }
    }

    /**
     * 添加病人信息到数据库
     * @param path
     */
    private void savePatientInfo(String path)
    {
        File file = new File(path);
        if (file.exists() && file.isFile()){
            //保存图片
            PicData picdata = new PicData();
            picdata.setDate(new Date().getTime());
            picdata.setPath(path);
            picdata.save();
            //检查部位
            if (cp ==null){
                cp = new CheckProgrem();
            }
            cp.setBody("血管");
            cp.setDate(new Date().getTime());
            cp.getPic().add(picdata);
            cp.save();
            patient.getCheckProgrems().add(cp);
            patient.save();
        }else{
            ToastUtils.showToastShort(this,"截图失败，请重试");
        }
    }
}
