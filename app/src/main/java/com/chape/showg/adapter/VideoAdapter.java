package com.chape.showg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chape.showg.R;
import com.chape.showg.base.Video;
import com.chape.showg.util.VideoThumbnail;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2017/10/1.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> mVideoList;
    private Context mContext;

    public VideoAdapter(List<Video> VideoList,Context context) {
      this.mVideoList=VideoList;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_video, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video=mVideoList.get(position);
        holder.jzVideoPlayer.setUp(video.getContent_url(), JZVideoPlayer.SCREEN_LAYOUT_LIST,"dd");
        /*Picasso.with(holder.jzVideoPlayer.getContext())
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(holder.jzVideoPlayer.thumbImageView);*/
        holder.jzVideoPlayer.thumbImageView.setImageBitmap(VideoThumbnail.getVideoThumbnail(video.getContent_url()));
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        JZVideoPlayerStandard jzVideoPlayer;
        public ViewHolder(View itemView){
           super(itemView);
            jzVideoPlayer=(JZVideoPlayerStandard)itemView.findViewById(R.id.videoplayer);
        }
    }
}
