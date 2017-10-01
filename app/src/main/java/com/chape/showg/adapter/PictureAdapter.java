package com.chape.showg.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chape.showg.R;
import com.chape.showg.base.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> implements View.OnClickListener {
    private List<Picture> mPictureList;
    private Context mContext;

    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener=null;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            imageView=(ImageView) view.findViewById(R.id.pic_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.pic,parent,false);
        ViewHolder vh=new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture picture=mPictureList.get(position);

        //Glide.with(mContext).load(picture.getPic_url()).into(holder.imageView);
        // Glide有BUG刷新不显示；
        Picasso.with(mContext).load(picture.getPic_url()).into(holder.imageView);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }
    public PictureAdapter(List<Picture> PictureList,Context context) {
    this.mContext=context;
      this.mPictureList=PictureList;
    }

    @Override
    public int getItemCount() {
        return mPictureList.size();
    }

}
