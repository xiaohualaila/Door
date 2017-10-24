package ug.door.activity;


import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import ug.door.R;
import ug.door.Util.FileUtil;
import ug.door.Util.UtilToast;
import ug.door.activity.base.BaseAppCompatActivity;
import ug.door.activity.hdmi.MyService;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void init() {
//        String path = FileUtil.getPath();
//        File file = new File(path);
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        permiss();
        //测试播放路径
//        String path = FileUtil.getPath();
//        List<String> ss = FileUtil.getPaths(path);
//        for (int i=0;i<ss.size();i++){
//            Log.i("sss",ss.get(i));
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void permiss(){
        PermissionGen.needPermission(this, 200, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        });
    }


    @PermissionSuccess(requestCode = 200)
    public void toLocation() {
        Intent bindIntent = new Intent(MainActivity.this, MyService.class);
        startService(bindIntent);
    }

    @PermissionFail(requestCode = 200)
    public void toLocationFail() {
        UtilToast.showToast(this, "请打开权限！");
    }




}