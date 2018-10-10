package com.fnhelper.photo.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.interfaces.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 相册信息
 */
public class AlbumInfoActivity extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.head_pic)
    SimpleDraweeView headPic;
    @BindView(R.id.vip_type)
    ImageView vipType;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.album_name)
    TextView albumName;
    @BindView(R.id.album_name_rl)
    RelativeLayout albumNameRl;
    @BindView(R.id.connect_tel)
    TextView connectTel;
    @BindView(R.id.connect_tel_rl)
    RelativeLayout connectTelRl;
    @BindView(R.id.wx_num)
    TextView wxNum;
    @BindView(R.id.wx_num_rl)
    RelativeLayout wxNumRl;
    @BindView(R.id.album_introduce)
    TextView albumIntroduce;
    @BindView(R.id.album_introduce_rl)
    RelativeLayout albumIntroduceRl;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_album_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("相册信息");
        comRight.setVisibility(View.GONE);
        headPic.setImageURI(Constants.sHeadImg);
        userName.setText(Constants.sTsNickNameoken);


    }

    @Override
    protected void onResume() {
        super.onResume();
        albumName.setText(Constants.album_name);
        connectTel.setText(Constants.sPhone);
        wxNum.setText(Constants.wx_num);
        albumIntroduce.setText(Constants.album_introduce);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        albumNameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumInfoActivity.this, ModifyAlbumInfoAc.class);
                i.putExtra("which", ModifyAlbumInfoAc.M_ALBUM_NAME);
                startActivity(i);
            }
        });
        connectTelRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumInfoActivity.this, ModifyAlbumInfoAc.class);
                i.putExtra("which", ModifyAlbumInfoAc.M_CONNECT_TEL);
                startActivity(i);
            }
        });
        wxNumRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumInfoActivity.this, ModifyAlbumInfoAc.class);
                i.putExtra("which", ModifyAlbumInfoAc.M_WX_NUM);
                startActivity(i);
            }
        });
        albumIntroduceRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumInfoActivity.this, ModifyAlbumInfoAc.class);
                i.putExtra("which", ModifyAlbumInfoAc.M_ALBUM_INTRODUCE);
                startActivity(i);
            }
        });
    }
}
