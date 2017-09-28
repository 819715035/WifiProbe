package cndoppler.cn.wifiprobe.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Button;

import cndoppler.cn.wifiprobe.R;
import cndoppler.cn.wifiprobe.utils.BaseActivity;
import cndoppler.cn.wifiprobe.utils.LogUtils;
import cndoppler.cn.wifiprobe.utils.ToastUtils;
import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

public class MainActivity extends BaseActivity implements Probe.SystemListener, Probe.BatteryListener, Probe.TemperatureListener
{

    private Button recordBtn;
    private Button scanBtn;
    private Probe probe;
    private static String cfgRoot = "cfg";

    @Override
    public void setContent() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initWidget() {
        recordBtn = findViewById(R.id.record_btn);
        scanBtn = findViewById(R.id.scan_btn);
        setListener();
    }


    @Override
    public void initData() {
        //初始化无线探头
        probe = WifiProbe.init(cfgRoot, getAssets());
        //探头系统监听
        probe.setSystemListener(this);
        //电池监听
        probe.setBatteryListener(this);
        //温度监听
        probe.setTemperatureListener(this);
        if (probe.isRequesting()) {
            ToastUtils.showToastShort(this,"Processing previous request.");
            return;
        } else if (!probe.isConnected()) {
            ToastUtils.showToastShort(this,"connecting to probe");
            //初始化
            probe.initialize();
        }
    }
    private void setListener()
    {
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到档案界面
                openActivity(RecordListActivity.class);
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override

            public void onClick(View view)
            {
                if (probe.isConnected()) {
                    //跳转到扫描页面
                    openActivity(ScanActivity.class);
                    return;
                }
                if (!probe.isRequesting()) {
                    ToastUtils.showToastShort(MainActivity.this,"connecting to probe");
                    probe.initialize();
                }
            }
        });
    }

    //链接成功
    @Override
    public void onInitialized()
    {
        ToastUtils.showToastShort(this,"cennected");
    }
    //链接失败
    @Override
    public void onInitializationError(String message)
    {
        ToastUtils.showToastShort(this,"connect failed: " + message);
    }

    @Override
    public void onInitialingLowVoltageError(String message)
    {

    }

    @Override
    public void onSystemError(String message)
    {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //disConnectWifi();//断开wifi连接
        //closeWifi();
        LogUtils.e("ondestroy");
    }

    public void disConnectWifi(){
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        int netId = mWifiInfo.getNetworkId();
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
        mWifiInfo = null;
    }

    public void closeWifi(){
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(false);
    }

    @Override
    public void onBatteryLevelChanged(int newBatteryLevel)
    {
        ToastUtils.showToastShort(this,"现在的电量为： " + newBatteryLevel + "%");
    }

    @Override
    public void onBatteryLevelTooLow(int BatteryLevel)
    {
        ToastUtils.showToastShort(this,"Battery level too low, now is " + BatteryLevel + "%");
    }

    @Override
    public void onTemperatureChanged(int newTemperature)
    {
        ToastUtils.showToastShort(this,"温度："+newTemperature+" °");
    }

    @Override
    public void onTemperatureOverHeated(int temperature)
    {
        ToastUtils.showToastShort(this,"Temperature over heated, now is " + temperature + " °");
    }
}
