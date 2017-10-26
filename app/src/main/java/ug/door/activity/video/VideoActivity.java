package ug.door.activity.video;

import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ug.door.R;
import ug.door.Util.FileUtil;
import ug.door.Util.UpdateApp;
import ug.door.Util.UtilToast;
import ug.door.activity.MainActivity;
import ug.door.activity.base.BaseAppCompatActivity;
import ug.door.activity.hdmi.MyService;
import ug.door.model.Videos;
import ug.door.retrofit.Api;
import ug.door.retrofit.ConnectUrl;
import ug.door.view.LoadingDialog;

/**
 * Created by ThinkPad on 2017/10/24.
 */

public class VideoActivity extends BaseAppCompatActivity implements VideoAdapter.OnDownFile {
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.list_pullRecycler)
    PullLoadMoreRecyclerView mListPullRecycler;
    private VideoAdapter videoAdapter;

    @Override
    protected void init() {
        tv_title.setText("广告下载");
        initPullRecycler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    private void initPullRecycler() {
        mListPullRecycler.setLinearLayout();
        mListPullRecycler.setIsRefresh(true);
        mListPullRecycler.setFooterViewText(getString(R.string.loaging));
        videoAdapter = new VideoAdapter(this);
        mListPullRecycler.setAdapter(videoAdapter);
        getData();
        mListPullRecycler.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                //下拉
                videoAdapter.clearData();
                getData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        videoAdapter.setOnDownFile(this);
    }

    private void getData() {
        Api.getBaseApi(ConnectUrl.VIDEO_URL)
                .videosList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Videos>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Videos videos) {
                   //     Log.i("sss",jsonObject.toString());

                        try {
                            if (videos.getResult().isSuccess()) {
                                videoAdapter.addAllData(videos.getUrl());
                                mListPullRecycler.setPullLoadMoreCompleted();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDownVideo(String videoUrl,String video_name) {
//        downloadFile3(videoUrl,video_name);.
        UpdateApp up = new UpdateApp(this);
        up.doUpdate(videoUrl,video_name);
        up.setOnDownSuccess(new UpdateApp.OnDownSuccess() {
            @Override
            public void onDownSuccess() {
                videoAdapter.clearData();
                getData();
            }
        });
    }

    @OnClick({R.id.btn_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    //下载视频
    private void downloadFile3(String videoUrl, final String video_name){
        LoadingDialog.showWindow(this);
        //下载路径，如果路径无效了，可换成你的下载路径
        if(TextUtils.isEmpty(videoUrl)){
            UtilToast.showToast(this,"无法下载！");
            return;
        }
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD","startTime="+startTime);
        Request request = new Request.Builder().url(videoUrl).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                Log.i("DOWNLOAD","download failed");
                if(LoadingDialog.isShowing()){
                    LoadingDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String path = FileUtil.getPath()+ File.separator + video_name + ".mp4";
                    File dest = new File(path);
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    Log.i("DOWNLOAD","download success");
                    Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));
                    if(LoadingDialog.isShowing()){
                        LoadingDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("DOWNLOAD","download failed");
                } finally {
                    if(bufferedSink != null){
                        bufferedSink.close();
                    }
                }
            }
        });
    }
}
