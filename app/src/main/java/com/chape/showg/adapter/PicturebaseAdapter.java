package com.chape.showg.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chape.showg.R;
import com.chape.showg.base.Picture;

import java.util.List;

/**
 * Created by Administrator on 2018/2/10.
 */

public class PicturebaseAdapter extends BaseQuickAdapter<Picture,BaseViewHolder> {
    public PicturebaseAdapter(int layoutResId, List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Picture item) {
        Glide.with(mContext).load(item.getPic_url()).into((ImageView)helper.getView(R.id.pic_image));
    }
}
