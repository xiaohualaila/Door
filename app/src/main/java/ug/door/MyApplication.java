package ug.door;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import ug.door.Util.SharedPreferencesUtil;
import ug.door.model.User;


/**
 * Created by xyuxiao on 2016/9/23.
 */
public class MyApplication extends Application {
    private User user;//用户登录后的信息
    private static MyApplication application;
    public static MyApplication getInstence(){
        return application;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate()
    {
        super.onCreate();
        application=this;
    }

    synchronized public User getUser() {

        if(user!=null){
            if(isSameDay(user.getLoginDate(),new Date())){
                return user;
            }else{
                System.out.println("不是同一天登陆");
                user=null;
                SharedPreferencesUtil.removeKey(this, "");
            }
            return user;
        }else{
            String user_str=SharedPreferencesUtil.getStringByKey("worker", this);
            if(user_str!=null && !(user_str.trim().equals(""))){
             //   user=(User) MyUtil.StringToObj(user_str);

                if(isSameDay(user.getLoginDate(),new Date())){
                    System.out.println("使用本地缓存");
                    return user;
                }else{
                    System.out.println("不是同一天登陆");
                    user=null;
                    SharedPreferencesUtil.removeKey(this, "worker");
                }
            }else{
                System.out.println("查询本地数据为空");
            }
        }
        return user;
    }
    @SuppressLint("SimpleDateFormat")
    private boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }
    public void setUser(User user) {
        this.user = user;
        String worker_str = "";
        if(user != null) {
       //    worker_str= MyUtil.ObjToString(user);
        }
        SharedPreferencesUtil.save("worker", worker_str, this);
    }

    /**
     * 获取应用版本号
     * @return
     */
    public int getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
