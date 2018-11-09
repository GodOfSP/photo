package com.fnhelper.photo.index;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowVedioAc extends BaseActivity {

    @BindView(R.id.v_vedio)
    VideoView vVedio;
    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_show_vedio);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comRight.setVisibility(View.GONE);
        comTitle.setVisibility(View.GONE);


    }

    @Override
    protected void initData() {


        Uri uri = Uri.parse(getIntent().getStringExtra("path"));
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.VISIBLE);//隐藏进度条
        vVedio.setMediaController(mediaController);
        vVedio.setVideoURI(uri);
        vVedio.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video 视屏播放的时候把背景设置为透明
                            progressbar.setVisibility(View.GONE);
                            vVedio.setBackgroundColor(Color.TRANSPARENT);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        vVedio.requestFocus();
        vVedio.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
