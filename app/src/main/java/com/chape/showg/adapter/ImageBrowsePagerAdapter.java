package com.chape.showg.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.chape.showg.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 */

public class ImageBrowsePagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> urls;

    public ImageBrowsePagerAdapter(Context context,List<String> urls) {
        this.context=context;
        this.urls=urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
   //   View view = View.inflate(context, R.layout.item_image_browser, null);
     //  PhotoView photoView=(PhotoView) view.findViewById(R.id.pv_show_image);
      //  PhotoView photoView=new PhotoView(container.getContext());
        PhotoView photoView=new PhotoView(container.getContext());
        String picUrl = urls.get(position);
        Uri u = Uri.parse(picUrl);
        Picasso.with(container.getContext())
                .load(u)
                .into(photoView);
        container.addView(photoView);
       // container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(photoView);
        //return view;
        return photoView;
    }
}
