package com.my.eventbustest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusPostActivity extends AppCompatActivity {

    @Bind(R.id.tv_event_info)
    TextView mTvEventInfo;
    @Bind(R.id.btn_post_normal)
    Button mBtnPostNormal;
    @Bind(R.id.btn_post_sticky_event)
    Button mBtnPostStickyEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_post);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_post_normal)
    public void postNormalEvent(){
        EventBus.getDefault().post(new MessageEvent("另外界面的主线程，普通消息", String.valueOf(SystemClock.currentThreadTimeMillis())));
        finish();
    }

    @OnClick(R.id.btn_post_sticky_event)
    public void postStickyEvent(){
        EventBus.getDefault().postSticky(new MessageEvent("另外界面的主线程，普通消息", String.valueOf(SystemClock.currentThreadTimeMillis())));
        finish();
    }
}
