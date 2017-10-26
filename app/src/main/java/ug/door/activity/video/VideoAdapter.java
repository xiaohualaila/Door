package ug.door.activity.video;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import ug.door.R;
import ug.door.Util.FileUtil;
import ug.door.model.Videos;
import ug.door.view.RoundImageView;


/**
 * Created by 付博文 on 2017/8/10.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    List<Videos.UrlBean> videos_list = new ArrayList<>();
    private Context mContext;
    private OnDownFile onDownFile;

    public void addAllData(List<Videos.UrlBean> dataList) {
        this.videos_list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.videos_list.clear();
    }

    public VideoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Videos.UrlBean video = videos_list.get(position);
        Glide.with(mContext).load(video.getPhoto()).into(holder.img);
        List<String>  hdmiVideoPath = FileUtil.getPaths(FileUtil.getPath());
        for (int i=0;i<hdmiVideoPath.size();i++){
            String path = hdmiVideoPath.get(i);
            int index = path.lastIndexOf("/");
            path = path.substring(index + 1,path.length());
            if(path.equals(video.getName())){
                holder.video_down_btn.setText("已下载");
                holder.video_down_btn.setBackgroundColor(Color.parseColor("#979898"));
                holder.video_down_btn.setEnabled(false);
            }else {
                holder.video_down_btn.setEnabled(true);
                RxView.clicks(holder.video_down_btn).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onDownFile.onDownVideo(video.getAd(),video.getName());
                    }
                });
            }
        }
        holder.name.setText(video.getName());

    }

    @Override
    public int getItemCount() {
        return videos_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        RoundImageView img;
        @Bind(R.id.video_name)
        TextView name;
        @Bind(R.id.video_down_btn)
        TextView video_down_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //详情按钮回调
    public interface OnDownFile{
        void onDownVideo(String videoUrl,String video_name);
    }

    public void setOnDownFile(OnDownFile onDownFile) {
        this.onDownFile = onDownFile;
    }
}
