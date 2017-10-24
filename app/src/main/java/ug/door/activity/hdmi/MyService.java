package ug.door.activity.hdmi;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ug.door.R;
import ug.door.Util.FileUtil;

/**
 * Created by admin on 2017/10/18.
 */

public class MyService extends Service {
    public static final String TAG = "MyService";
 //   private MyBinder mBinder = new MyBinder();
    private DisplayManager mDisplayManager;
    private MyPresentation myPresentation;
    private SurfaceView presentSurface;
    private MediaPlayer mBackgroundPlayer;
    private int nowHdmiPosition = 0;
    private List<String> hdmiVideoPath = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        super.onCreate();
        updateContents();
        initViewSurface();
    }

    private void initViewSurface() {
        mBackgroundPlayer = new MediaPlayer();
        mBackgroundPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
       hdmiVideoPath = FileUtil.getPaths(FileUtil.getPath());
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 获取LayoutParams对象
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        // 宽度和高度为0, 可以避免主屏退出后出现的主屏触摸和点击问题。
        wmParams.width = 0;
        wmParams.height = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout presentationLayout = (LinearLayout) inflater.inflate(R.layout.two_screen, null);
        presentationLayout.setFocusable(false);
        mWindowManager.addView(presentationLayout, wmParams);
        playVideo(mBackgroundPlayer, nowHdmiPosition, true);

    }

    // 统一的播放界面。
    private void playVideo(MediaPlayer mediaPlayer, int pathIndex, boolean isFirstPlay) {

        if (isFirstPlay) {
            mediaPlayer.setOnCompletionListener(new MyVideoFinishListener());
            try {
                mediaPlayer.setDataSource(hdmiVideoPath.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //  拔hdmi会出现异常。
            }
            mediaPlayer.start();
            mediaPlayer.seekTo(0);

        } else {
            mediaPlayer.reset();
            mediaPlayer.setOnCompletionListener(new MyVideoFinishListener());
            try {
                mediaPlayer.setDataSource(hdmiVideoPath.get(pathIndex));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //  拔hdmi会出现异常。
            }
            mediaPlayer.start();
        }
    }

    // 播放结束监听。
    class MyVideoFinishListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            nowHdmiPosition++;
            if (nowHdmiPosition >= hdmiVideoPath.size()) {
                nowHdmiPosition = 0;
            }
            playVideo(mp, nowHdmiPosition, false);
        }
    }
    class MySurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            mBackgroundPlayer.setDisplay(presentSurface.getHolder());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
//        return mBinder;
    }
//    class MyBinder extends Binder {
//
//        public void startDownload() {
//            Log.d("TAG", "startDownload() executed");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // 执行具体的下载任务
//                }
//            }).start();
//        }
//    }


    // 获取显示设备。
    public void updateContents() {
        mDisplayManager = (DisplayManager) getSystemService(
                Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();
        showPresentation(displays[1]);
    }

    // 将内容显示到display上面。
    private void showPresentation(Display display) {
        myPresentation = new MyPresentation(this, display);
        myPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        myPresentation.show();
        presentSurface = myPresentation.getSurface();
        presentSurface.getHolder().addCallback(new MySurfaceCallback());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBackgroundPlayer.isPlaying()) {
            mBackgroundPlayer.stop();
        }
        mBackgroundPlayer.release();
    }
}
