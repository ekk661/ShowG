package com.chape.showg.mvp.data.source.remote;

import com.chape.showg.base.Picture;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * 作者： Administrator on 2018/2/21.
 * 邮箱：773156959@qq.com
 */

public interface DataSource {
    Observable<List<Picture>> getData();
}
