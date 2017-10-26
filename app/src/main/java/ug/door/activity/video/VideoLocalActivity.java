package ug.door.activity.video;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ug.door.R;
import ug.door.Util.FileUtil;
import ug.door.activity.base.BaseAppCompatActivity;
import ug.door.model.Video;
import ug.door.view.RoundImageView;

/**
 * Created by ThinkPad on 2017/10/25.
 */

public class VideoLocalActivity extends BaseAppCompatActivity {
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<Video> mDatas;

    @Override
    protected void init() {
        tv_title.setText("已下载视频");
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_local_video;
    }

    protected void initData()
    {
        mDatas = new ArrayList<Video>();
        Video video ;
        List<String>  hdmiVideoPath = FileUtil.getPaths(FileUtil.getPath());
        for (int i = 0; i < hdmiVideoPath.size(); i++)
        {
            video = new Video();
            video.setVideo_path(hdmiVideoPath.get(i));
            String videoName =hdmiVideoPath.get(i);
            int index = videoName.lastIndexOf("/");
            videoName = videoName.substring(index + 1,videoName.length());
            video.setVideo_name(videoName);
            mDatas.add(video);
        }
    }

    @OnClick({R.id.btn_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void showDialog(final String path){
        LemonHello.getWarningHello("删除！","确定要删除这段视频广告吗？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", Color.RED, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        File file =new File(path);
                            if(file.exists()){
                                new File(path).delete();
                            }
                        lemonHelloView.hide();
                        initData();
                    }
                }))
                .show(VideoLocalActivity.this);
    }

    public static Bitmap getVideoThumbnail(String filePath, int width_, int height_, int kind) {
        Bitmap bitmap = null;
        try {
            bitmap = ThumbnailUtils.createVideoThumbnail(filePath,kind);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width_, height_,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null) return null;
        return bitmap;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    VideoLocalActivity.this).inflate(R.layout.video_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            holder.img.setImageBitmap(getVideoThumbnail(mDatas.get(position).getVideo_path(),150,150,96*96));
            holder.name.setText(mDatas.get(position).getVideo_name());
            holder.video_down_btn.setText("删除");
            holder.video_down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(mDatas.get(position).getVideo_path());
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            @Bind(R.id.img)
            RoundImageView img;
            @Bind(R.id.video_name)
            TextView name;
            @Bind(R.id.video_down_btn)
            TextView video_down_btn;
            public MyViewHolder(View view)
            {
                super(view);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
