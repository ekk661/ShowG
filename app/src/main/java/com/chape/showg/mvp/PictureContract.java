package com.chape.showg.mvp;

/**
 * 作者： Administrator on 2018/2/21.
 * 邮箱：773156959@qq.com
 */

public interface PictureContract {
    interface View extends BaseView<Presenter>{
     void setData();
     void showProgress();
     void hideProgress();

    }
    interface Presenter extends BasePresenter{
    void getData();
    }
}
