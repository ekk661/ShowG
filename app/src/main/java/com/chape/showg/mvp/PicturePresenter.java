package com.chape.showg.mvp;

import android.support.annotation.NonNull;

import com.chape.showg.adapter.PicturebaseAdapter;
import com.chape.showg.base.Picture;
import com.chape.showg.mvp.data.source.Repository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * 作者： Administrator on 2018/2/21.
 * 邮箱：773156959@qq.com
 */

public class PicturePresenter implements PictureContract.Presenter {
@NonNull
private  Repository mRepository;
@NonNull
private PictureContract.View mPictureView;
@NonNull
private  CompositeDisposable mCompositeDisposable;
private List<Picture> mPictures=new ArrayList<>();
private PicturebaseAdapter mAdapter;
    public PicturePresenter(@NonNull Repository repository,@NonNull PictureContract.View pictureView){
        this.mRepository=checkNotNull(repository,"repository is null");
        this.mPictureView=checkNotNull(pictureView,"pictureView is null");
        mCompositeDisposable=new CompositeDisposable();
       mPictureView.setPresenter(this);
    }

    @Override
    public void getData() {


           mRepository.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Picture>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Picture> pictures) {
                            mPictures.addAll(pictures);
                            mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
    mCompositeDisposable.clear();
    }
}
