package com.giyer.noogle.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.SubscriberExceptionEvent;

import static com.giyer.noogle.util.LogUtil.logD;
import static com.giyer.noogle.util.LogUtil.makeLogTag;

/**
 * Created by giyer7 on 3/8/17.
 */

public abstract class BaseEventBusActivity extends AppCompatActivity implements BaseEventBusFragment.BaseEventBusFragmentListener {

    private static final String TAG = makeLogTag(BaseEventBusActivity.class);

    public void onEvent(final SubscriberExceptionEvent event) {
        EventBus.getDefault().removeAllStickyEvents();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                logD(TAG, "Crash in onEvent() in " + event.causingSubscriber.getClass().getSimpleName() + " while handling " + event.causingEvent.getClass().getSimpleName());
                throw new RuntimeException(event.throwable);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public abstract void showUIBlockingProgress();

    public abstract void hideUIBlockingProgress();

    public abstract void showHorizontalProgressBar();

    public abstract void hideHorizontalProgressBar();

//    @Subscribe
//    public void onMessageEvent(RequestMessage requestMessage) {
//    }
}
