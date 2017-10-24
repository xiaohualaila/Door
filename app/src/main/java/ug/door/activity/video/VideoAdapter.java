package ug.door.activity.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;
import ug.door.R;
import ug.door.model.Videos;




/**
 * Created by 付博文 on 2017/8/10.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    List<Videos> videos_list = new ArrayList<>();
    private Context mContext;
    private OnDownFile onDownFile;

    public void addAllData(List<Videos> dataList) {
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
        final Videos videos = videos_list.get(position);
        Glide.with(mContext).load(videos.getImgUrl()).into(holder.img);
        holder.name.setText(videos.getName());
        RxView.clicks(holder.video_down_btn).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onDownFile.onDownVideo(videos.getVideoUrl(),videos.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView video_down_btn;



        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.video_name);
            video_down_btn = (TextView) itemView.findViewById(R.id.video_down_btn);

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
