package ug.door.Util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zxl on 2016/1/7.
 */
public class FileDownloader {
    /** 连接url */
    private String urlstr;
    /** sd卡目录路径 */
    private String sdcard;
    /** http连接管理类 */
    private HttpURLConnection urlcon;

    public FileDownloader(String url) {
        this.urlstr = url;
        //获取设备sd卡目录
        this.sdcard = FileUtil.getPath();
        urlcon = getConnection();
    }

    /*
     * 获取http连接处理类HttpURLConnection
     */
    private HttpURLConnection getConnection()
    {
        URL url;
        HttpURLConnection urlcon = null;
        try {
            url = new URL(urlstr);
            urlcon = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlcon;
    }

    /*
     * 获取连接文件长度。
     */
    public int getLength() {
        return urlcon.getContentLength();
    }

    /**
     * 下载文件到sd卡
     * @param filename
     * @param handler
     * @return 0:下载失败  1：下载成功
     */
    public void down2sd(String filename, DownloadHandler handler)
    {
        File file = new File(sdcard);
        if (!file.exists()) {
            file.mkdirs();
            //创建文件夹
        }
        //获取文件全名
        file = new File(sdcard + File.separator + filename);
        System.out.println("----------------" + file.getAbsolutePath());
        FileOutputStream fos = null;
        try {
            InputStream is = urlcon.getInputStream();
            //创建文件
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int numRead;
            while ((numRead = is.read(buf)) != -1) {
                fos.write(buf, 0, numRead);
                //同步更新数据
                handler.setSize(buf.length);
            }
            fos.flush();
            is.close();
        } catch (Exception e) {
            handler.downloadFailed(e.getMessage());
            return;
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        handler.downloadSuccess(file.getAbsolutePath());
    }

    /*
     * 内部回调接口类
     */
    public interface DownloadHandler
    {
        public void setSize(int size);

        public void downloadSuccess(String filename);

        public void downloadFailed(String msg);
    }
}
