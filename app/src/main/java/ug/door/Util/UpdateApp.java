package ug.door.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import ug.door.R;


/**
 * Created by zxl on 2016/1/7.
 */
public class UpdateApp {
    private Context context;
    private OnDownSuccess onDownSuccess;
    public UpdateApp(Context context) {
        this.context = context;

    }

    public void doUpdate(final String url, final String fileName) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.activity_downloading, null );
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(false);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        final ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.load);
        final int MSG_SET_SIZE = 0;
        final int MSG_DOWNLOAD_FAILED = 1;
        final int MSG_DOWNLOAD_SUCCESS = 2;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.arg1) {
                    case MSG_SET_SIZE :
                        int pro = progressBar.getProgress() + msg.arg2;
                        progressBar.setProgress(pro);
                        break;
                    case MSG_DOWNLOAD_FAILED :
                        Toast.makeText(context, "出错了：" + msg.getData().getString("failedMsg"), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case MSG_DOWNLOAD_SUCCESS :
                        dialog.dismiss();
                       //// TODO: 2017/10/24 下载完成
                        onDownSuccess.onDownSuccess();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                //另起线程执行下载，安卓最新sdk规范，网络操作不能再主线程。
                FileDownloader downloader = new FileDownloader(url);
                progressBar.setMax(downloader.getLength());

                /**
                 * 下载文件到sd卡，虚拟设备必须要开始设置sd卡容量
                 * downhandler是Download的内部类，作为回调接口实时显示下载数据
                 */
                downloader.down2sd(fileName, new FileDownloader.DownloadHandler() {
                    @Override
                    public void setSize(int size) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = MSG_SET_SIZE;
                        msg.arg2 = size;
                        msg.sendToTarget();
                    }

                    @Override
                    public void downloadFailed(String failedMsg) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = MSG_DOWNLOAD_FAILED;
                        Bundle bundle = new Bundle();
                        bundle.putString("failedMsg", failedMsg);
                        msg.setData(bundle);
                        msg.sendToTarget();
                    }

                    public void downloadSuccess(String fileName) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = MSG_DOWNLOAD_SUCCESS;
                        Bundle bundle = new Bundle();
                        bundle.putString("fileName", fileName);
                        msg.setData(bundle);
                        msg.sendToTarget();
                    }
                });
            }
        }).start();
    }

    //详情按钮回调
    public interface OnDownSuccess{
        void onDownSuccess();
    }

    public void setOnDownSuccess(OnDownSuccess onDownSuccess) {
        this.onDownSuccess = onDownSuccess;
    }
}
