package ug.door.activity.login;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.OnClick;
import ug.door.R;
import ug.door.Util.MyUtil;
import ug.door.Util.SharedPreferencesUtil;
import ug.door.Util.UtilToast;
import ug.door.activity.base.BaseAppCompatActivity;
import ug.door.activity.setUp.SetUpActivity;
import ug.door.view.ClearEditTextWhite;
import ug.door.view.LoadingDialog;

/**
 * Created by admin on 2016/9/26.
 */
public class LoginActivity extends BaseAppCompatActivity implements LoginContract.View {
    @Bind(R.id.username)
    ClearEditTextWhite tv_username;
    @Bind(R.id.password)
    ClearEditTextWhite tv_password;
    @Bind(R.id.checkbox)
    ImageView checkbox;
    private boolean isBackKeyPressed = false;
    private LoginContract.Presenter presenter;
    private String username;
    private String password;
    @Override
    protected void init() {
        new LoginPresenter(this);
//        String tag = SharedPreferencesUtil.getStringByKey("tag", LoginActivity.this);
//        initLoginContent(tag);
        String device_id = MyUtil.getDeviceID(this);//获取设备号
        Log.i("sss","device_id" + device_id);
//        try {
//            Runtime.getRuntime().exec("/system/bin/changedisplaymode.sh");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void initLoginContent(String tag) {
        if (tag != null) {
            checkbox.setImageResource(R.drawable.login_beixuan);
        } else {
            checkbox.setImageResource(R.drawable.login_weixuan);
        }
        String local_name = SharedPreferencesUtil.getStringByKey("username", LoginActivity.this);
        if (local_name != null) {
            tv_username.setText(local_name);
        }
        String local_pwd = SharedPreferencesUtil.getStringByKey("pwd", LoginActivity.this);
        if (local_pwd != null) {
            tv_password.setText(local_pwd);
        }
    }

    @OnClick({ R.id.tv_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                username = tv_username.getText().toString();
                password = tv_password.getText().toString();
                if (username.equals("")) {
                    UtilToast.showToast(this,"请输入用户名！");
                    return;
                } else if (password.equals("")) {
                    UtilToast.showToast(this,"请输入密码！");
                    return;
                } else {
                  //  LoadingDialog.showWindow(this);
                    //判断有误网络
//                    if (MyUtil.isNetworkAvailable(getApplicationContext())) {
//                        presenter.login(username, password);
//                    } else {
//                       // showToast("无网络链接");
//                        checkLogin(username, password);
//                    }
                    checkLogin(username, password);
                }
                break;
//            case R.id.checkbox:
//                if (v.getTag().toString().equals("true")) {
//                    v.setTag("false");
//                    checkbox.setImageResource(R.drawable.login_weixuan);
//                } else {
//                    v.setTag("true");
//                    checkbox.setImageResource(R.drawable.login_beixuan);
//                }
//                break;

        }
    }

    private void checkLogin(String username, String password) {
        SimpleDateFormat format =new SimpleDateFormat("yyyyMMdd");
        String paw = format.format(new Date());
        paw = paw + "9527";
       Log.i("ssss",paw + "ssss" + password);
//            String flag = checkbox.getTag().toString();
//            SharedPreferencesUtil.save("checkbox_tag", flag, this);
//            if (flag.equals("true")) {
//                SharedPreferencesUtil.save("username", tv_username.getText().toString(), this);
//                SharedPreferencesUtil.save("pwd", tv_password.getText().toString(), this);
//                SharedPreferencesUtil.save("tag", "true", this);
//            } else {
//                SharedPreferencesUtil.removeKey(this, "username");
//                SharedPreferencesUtil.removeKey(this, "pwd");
//                SharedPreferencesUtil.removeKey(this, "tag");
//            }
            if(!username.equals("hzug")){
                UtilToast.showToast(this,"用户名错误！");
                return;
            }else if(!password.equals(paw)){
                UtilToast.showToast(this,"密码错误！");
                return;
            }
           startActivity(new Intent(this, SetUpActivity.class));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isBackKeyPressed) {
                finish();
            } else {
                isBackKeyPressed = true;
                UtilToast.showToast(this,"再按一次退出程序！");
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    public void run() {
                        isBackKeyPressed = false;
                    }
                };
                timer.schedule(timerTask, 2000);//600毫秒后无再次点击，则复位
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void loginErrorMsg(String msg) {
        if (LoadingDialog.isShowing()) {
            LoadingDialog.dismiss();
        }
        checkLogin(username, password);
       // showToast(msg);
    }

}
