package com.my.eventbustest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.eventbustest.qrcode.QRCodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_event_info_priority)
    TextView mTvEventInfoPriority;
    @Bind(R.id.tv_event_info)
    TextView mTvEventInfo;
    @Bind(R.id.btn_cancel_delivery)
    Button mBtnCancelDelivery;
    @Bind(R.id.qrcodeImg)
    ImageView mQRCodeImg;
    @Bind(R.id.btn_turn_next)
    Button btnTurnNext;
    @Bind(R.id.btn_turn_sticky)
    Button btnTurnSticky;
    @Bind(R.id.btn_register_normal)
    Button btnRegisterNormal;
    @Bind(R.id.btn_unregister_normal)
    Button btnUnregisterNormal;
    @Bind(R.id.btn_createcqrode)
    Button btnCreatecqrode;
    private boolean mCancelDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 5)
    public void readMessageFirst(MessageEvent messageEvent) {
        mTvEventInfoPriority.setText("\n" + messageEvent.getMessage() + ", " + messageEvent.getTime());
        if (mCancelDelivery) {
            EventBus.getDefault().cancelEventDelivery(messageEvent);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void readMessage(MessageEvent messageEvent) {
        mTvEventInfo.setText("\n" + messageEvent.getMessage() + ", " + messageEvent.getTime());
    }

    @OnClick(R.id.btn_register_normal)
    public void registerNormal() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @OnClick(R.id.btn_unregister_normal)
    public void unregisterNormal() {
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_cancel_delivery)
    public void cancelDelivery() {
        mCancelDelivery = !mCancelDelivery;
        mBtnCancelDelivery.setText(mCancelDelivery ? "已拦截事件传递" : "点击拦截事件传递");
    }

    @OnClick(R.id.btn_turn_next)
    public void turnToNext() {
        startActivity(new Intent(this, EventBusPostActivity.class));
    }

    @OnClick(R.id.btn_turn_sticky)
    public void turnToSticky() {
        startActivity(new Intent(this, EventBusStickyActivity.class));
    }

    @OnClick(R.id.btn_createcqrode)
    public void createQRCode() {
        Bitmap bitmap = QRCodeUtils.createQRCode("www.baidu.com", 500);
        mQRCodeImg.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
