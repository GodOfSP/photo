package com.fnhelper.photo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.MarkListItemBean;
import com.fnhelper.photo.beans.MarkMarkDelegate;
import com.fnhelper.photo.beans.MarkNoneDelegate;
import com.fnhelper.photo.beans.MarkNormalDelegate;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.FullyGridLayoutManager;
import com.fnhelper.photo.utils.GridImageAdapter;
import com.fnhelper.photo.utils.ImageUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tencent.mm.opensdk.utils.Log;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 添加图文
 */
public class AddNewPhotoWordActivity extends BaseActivity implements View.OnClickListener, MarkNormalDelegate.onNormalItemThings, MarkMarkDelegate.onNormalItemThings {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.word)
    EditText word;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.source_tv)
    TextView sourceTv;
    @BindView(R.id.source_iv)
    ImageView sourceIv;
    @BindView(R.id.mark_add_iv)
    ImageView markAddIv;
    @BindView(R.id.mark_add_tv)
    TextView markAddTv;
    @BindView(R.id.mark_rv)
    RecyclerView markRv;
    @BindView(R.id.save_btn)
    Button saveBtn;


    private EasyPopup mCirclePop;
    private EasyPopup mMarkPop;

    private GridImageAdapter adapter;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofAll();

    private MultiItemTypeAdapter marKListAdapter; //备注信息列表的adapter
    private ArrayList<MarkListItemBean> markList;

    /**
     * 备注类型常量
     */

    public static final int MARK_TYPE_GOOD_NUM = 601; //货号备注
    public static final int MARK_TYPE_GET_PRICE = 602; //拿货价
    public static final int MARK_TYPE_SALE_PRICE = 603; //零售价
    public static final int MARK_TYPE_PF_PRICE = 604; //批发价
    public static final int MARK_TYPE_PACK_PRICE = 605; //打包价
    public static final int MARK_TYPE_TV = 606; //备注文字
    public static final int MARK_TYPE_ALL = 600; //全部
    public static final int MARK_TYPE_NONE = 607; //没有
    //备注类型是否已经填写完成
    private Boolean[] markHaveFillList = new Boolean[]{false, false, false, false, false, false};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_new_photo_word);
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

        comTitle.setText("添加图文");

        comRight.setVisibility(View.GONE);

        initChooseMediaPop();

        initMarkList();

        initPhotoList();


    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        markAddIv.setOnClickListener(this);
        markAddTv.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    /**
     * 初始化备注信息列表相关
     */
    private void initMarkList() {

        markRv.setLayoutManager(new LinearLayoutManager(AddNewPhotoWordActivity.this, LinearLayoutManager.VERTICAL, false));
        markList = new ArrayList<>();
        markList.add(new MarkListItemBean("", "", false, "", MARK_TYPE_NONE));
        marKListAdapter = new MultiItemTypeAdapter(AddNewPhotoWordActivity.this, markList);
        marKListAdapter.addItemViewDelegate(new MarkNoneDelegate());
        marKListAdapter.addItemViewDelegate(new MarkMarkDelegate(this));
        marKListAdapter.addItemViewDelegate(new MarkNormalDelegate(this));
        markRv.setAdapter(marKListAdapter);


    }

    /**
     * 初始化媒体选择列表相关
     */
    private void initPhotoList() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(AddNewPhotoWordActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        adapter = new GridImageAdapter(AddNewPhotoWordActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddNewPhotoWordActivity.this).themeStyle(R.style.picture_QQ_style).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddNewPhotoWordActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddNewPhotoWordActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

    }


    /**
     * 初始化备注弹框
     * type--  不同备注类型显示不同弹框
     */
    private void initMarkPop(final int type, boolean isOpen) {


        mMarkPop = EasyPopup.create()
                .setContentView(AddNewPhotoWordActivity.this, R.layout.fill_mark_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(false)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView((ViewGroup) findViewById(android.R.id.content))
                .apply();


        final ClearEditText goodNumEt = mMarkPop.findViewById(R.id.good_num_et);
        final Switch goodNumSwitch = mMarkPop.findViewById(R.id.good_num_switch);
        final TextView goodNumTv = mMarkPop.findViewById(R.id.good_num_tv);
        final ConstraintLayout goodNumCl = mMarkPop.findViewById(R.id.good_num_cl);
        final ClearEditText getPriceEt = mMarkPop.findViewById(R.id.get_price_et);
        final Switch getPriceSwitch = mMarkPop.findViewById(R.id.get_price_switch);
        final TextView getPriceTv = mMarkPop.findViewById(R.id.get_price_tv);
        final ConstraintLayout getPriceCl = mMarkPop.findViewById(R.id.get_price_cl);
        final ClearEditText salePriceEt = mMarkPop.findViewById(R.id.sale_price_et);
        final Switch salePriceSwitch = mMarkPop.findViewById(R.id.sale_price_switch);
        final TextView salePriceTv = mMarkPop.findViewById(R.id.sale_price_tv);
        final ConstraintLayout salePriceCl = mMarkPop.findViewById(R.id.sale_price_cl);
        final ClearEditText pfPriceEt = mMarkPop.findViewById(R.id.pf_price_et);
        final Switch pfPriceSwitch = mMarkPop.findViewById(R.id.pf_price_switch);
        final TextView pfPriceTv = mMarkPop.findViewById(R.id.pf_price_tv);
        final ConstraintLayout pfPriceCl = mMarkPop.findViewById(R.id.pf_price_cl);
        final ClearEditText packPriceEt = mMarkPop.findViewById(R.id.pack_price_et);
        final EditText wordPop = mMarkPop.findViewById(R.id.word_pop);
        final Switch packPriceSwitch = mMarkPop.findViewById(R.id.pack_price_switch);
        final TextView packPriceTv = mMarkPop.findViewById(R.id.pack_price_tv);
        final ConstraintLayout packPriceCl = mMarkPop.findViewById(R.id.pack_price_cl);
        TextView cancel = mMarkPop.findViewById(R.id.cancel);
        TextView sure = mMarkPop.findViewById(R.id.sure);


        // 根据不同状态展示不同UI
        if (type == MARK_TYPE_ALL) {
            int isAllDone = 0;
            if (markHaveFillList[0]) {
                goodNumCl.setVisibility(View.GONE);
                isAllDone++;
            }
            if (markHaveFillList[1]) {
                getPriceCl.setVisibility(View.GONE);
                isAllDone++;
            }
            if (markHaveFillList[2]) {
                salePriceCl.setVisibility(View.GONE);
                isAllDone++;
            }
            if (markHaveFillList[3]) {
                pfPriceCl.setVisibility(View.GONE);
                isAllDone++;
            }
            if (markHaveFillList[4]) {
                packPriceCl.setVisibility(View.GONE);
                isAllDone++;
            }
            if (markHaveFillList[5]) {
                wordPop.setVisibility(View.GONE);
                isAllDone++;
            }
            if (isAllDone == 6) {
                showBottom(AddNewPhotoWordActivity.this, "已添加所有备注项，可左滑编辑每一项");
                return;
            }

        } else {
            switch (type) {

                case MARK_TYPE_GOOD_NUM:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_GOOD_NUM) {
                            goodNumEt.setText(markList.get(i).getTvContent());
                            break;
                        }
                    }
                    goodNumSwitch.setChecked(isOpen);
                    getPriceCl.setVisibility(View.GONE);
                    salePriceCl.setVisibility(View.GONE);
                    pfPriceCl.setVisibility(View.GONE);
                    packPriceCl.setVisibility(View.GONE);
                    wordPop.setVisibility(View.GONE);
                    break;
                case MARK_TYPE_GET_PRICE:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_GET_PRICE) {
                            getPriceEt.setText(markList.get(i).getTvContent());
                            break;
                        }
                    }
                    getPriceSwitch.setChecked(isOpen);
                    goodNumCl.setVisibility(View.GONE);
                    salePriceCl.setVisibility(View.GONE);
                    pfPriceCl.setVisibility(View.GONE);
                    packPriceCl.setVisibility(View.GONE);
                    wordPop.setVisibility(View.GONE);
                    break;
                case MARK_TYPE_SALE_PRICE:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_SALE_PRICE) {
                            salePriceEt.setText(markList.get(i).getTvContent());
                            break;
                        }
                    }
                    salePriceSwitch.setChecked(isOpen);
                    goodNumCl.setVisibility(View.GONE);
                    getPriceCl.setVisibility(View.GONE);
                    pfPriceCl.setVisibility(View.GONE);
                    packPriceCl.setVisibility(View.GONE);
                    wordPop.setVisibility(View.GONE);
                    break;
                case MARK_TYPE_PF_PRICE:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_PF_PRICE) {
                            pfPriceEt.setText(markList.get(i).getTvContent());
                            break;
                        }
                    }
                    pfPriceSwitch.setChecked(isOpen);
                    goodNumCl.setVisibility(View.GONE);
                    getPriceCl.setVisibility(View.GONE);
                    salePriceCl.setVisibility(View.GONE);
                    packPriceCl.setVisibility(View.GONE);
                    wordPop.setVisibility(View.GONE);
                    break;
                case MARK_TYPE_PACK_PRICE:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_PACK_PRICE) {
                            packPriceEt.setText(markList.get(i).getTvContent());
                            break;
                        }
                    }
                    packPriceSwitch.setChecked(isOpen);
                    goodNumCl.setVisibility(View.GONE);
                    getPriceCl.setVisibility(View.GONE);
                    salePriceCl.setVisibility(View.GONE);
                    pfPriceCl.setVisibility(View.GONE);
                    wordPop.setVisibility(View.GONE);
                    break;
                case MARK_TYPE_TV:
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).getType() == MARK_TYPE_TV) {
                            wordPop.setText(markList.get(i).getTvMark());
                            break;
                        }
                    }
                    goodNumCl.setVisibility(View.GONE);
                    getPriceCl.setVisibility(View.GONE);
                    salePriceCl.setVisibility(View.GONE);
                    pfPriceCl.setVisibility(View.GONE);
                    packPriceCl.setVisibility(View.GONE);
                    break;
            }
        }


        //switch
        goodNumSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    goodNumTv.setText("公开");
                } else {
                    goodNumTv.setText("仅自己可见");
                }
            }
        });
        getPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getPriceTv.setText("公开");
                } else {
                    getPriceTv.setText("仅自己可见");
                }
            }
        });
        packPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    packPriceTv.setText("公开");
                } else {
                    packPriceTv.setText("仅自己可见");
                }
            }
        });
        pfPriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pfPriceTv.setText("公开");
                } else {
                    pfPriceTv.setText("仅自己可见");
                }
            }
        });
        salePriceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    salePriceTv.setText("公开");
                } else {
                    salePriceTv.setText("仅自己可见");
                }
            }
        });

        //quxiao
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMarkPop.dismiss();
            }
        });

        //确定
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < markList.size(); i++) {
                    if (markList.get(i).getType() == MARK_TYPE_NONE) {
                        markList.clear();
                        break;
                    }
                }

                //货号
                if (!TextUtils.isEmpty(goodNumEt.getText().toString()) && goodNumCl.getVisibility() == View.VISIBLE) {

                    if (markHaveFillList[0]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_GOOD_NUM) {
                                markList.get(i).setTvContent(goodNumEt.getText().toString());
                                markList.get(i).setOpen(goodNumSwitch.isChecked());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("货号备注", goodNumEt.getText().toString(), goodNumSwitch.isChecked(), "", MARK_TYPE_GOOD_NUM));
                        markHaveFillList[0] = true;

                    }


                } else if (TextUtils.isEmpty(goodNumEt.getText().toString()) && goodNumCl.getVisibility() == View.VISIBLE) {
                    markHaveFillList[0] = false;
                    if (markHaveFillList[0]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_GOOD_NUM) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                }
                //拿货价
                if (!TextUtils.isEmpty(getPriceEt.getText().toString()) && getPriceCl.getVisibility() == View.VISIBLE) {

                    if (markHaveFillList[1]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_GET_PRICE) {
                                markList.get(i).setTvContent(getPriceEt.getText().toString());
                                markList.get(i).setOpen(getPriceSwitch.isChecked());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("拿货价", getPriceEt.getText().toString(), getPriceSwitch.isChecked(), "", MARK_TYPE_GET_PRICE));
                        markHaveFillList[1] = true;
                    }

                } else if (TextUtils.isEmpty(getPriceEt.getText().toString()) && getPriceCl.getVisibility() == View.VISIBLE) {
                    markHaveFillList[1] = false;
                    if (markHaveFillList[1]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_GET_PRICE) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                }
                //零售价n
                if (!TextUtils.isEmpty(salePriceEt.getText().toString()) && salePriceCl.getVisibility() == View.VISIBLE) {

                    if (markHaveFillList[2]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_SALE_PRICE) {
                                markList.get(i).setTvContent(salePriceEt.getText().toString());
                                markList.get(i).setOpen(salePriceSwitch.isChecked());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("零售价", salePriceEt.getText().toString(), salePriceSwitch.isChecked(), "", MARK_TYPE_SALE_PRICE));

                        markHaveFillList[2] = true;
                    }


                } else if (TextUtils.isEmpty(salePriceEt.getText().toString()) && salePriceCl.getVisibility() == View.VISIBLE) {
                    markHaveFillList[2] = false;

                    if (markHaveFillList[2]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_SALE_PRICE) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                }
                //批发价
                if (!TextUtils.isEmpty(pfPriceEt.getText().toString()) && pfPriceCl.getVisibility() == View.VISIBLE) {

                    if (markHaveFillList[3]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_PF_PRICE) {
                                markList.get(i).setTvContent(pfPriceEt.getText().toString());
                                markList.get(i).setOpen(pfPriceSwitch.isChecked());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("批发价", pfPriceEt.getText().toString(), pfPriceSwitch.isChecked(), "", MARK_TYPE_PF_PRICE));

                        markHaveFillList[3] = true;
                    }


                } else if (TextUtils.isEmpty(pfPriceEt.getText().toString()) && pfPriceCl.getVisibility() == View.VISIBLE) {
                    if (markHaveFillList[3]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_PF_PRICE) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                    markHaveFillList[3] = false;
                }

                //打包价
                if (!TextUtils.isEmpty(packPriceEt.getText().toString()) && packPriceCl.getVisibility() == View.VISIBLE) {

                    if (markHaveFillList[4]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_PACK_PRICE) {
                                markList.get(i).setTvContent(packPriceEt.getText().toString());
                                markList.get(i).setOpen(packPriceSwitch.isChecked());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("打包价", packPriceEt.getText().toString(), packPriceSwitch.isChecked(), "", MARK_TYPE_PACK_PRICE));
                        markHaveFillList[4] = true;
                    }

                } else if (TextUtils.isEmpty(packPriceEt.getText().toString()) && packPriceCl.getVisibility() == View.VISIBLE) {
                    if (markHaveFillList[4]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_PACK_PRICE) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                    markHaveFillList[4] = false;

                }
                //备注文字
                if (!TextUtils.isEmpty(wordPop.getText().toString()) && wordPop.getVisibility() == View.VISIBLE) {


                    if (markHaveFillList[5]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_TV) {
                                markList.get(i).setTvMark(wordPop.getText().toString());
                                break;
                            }
                        }
                    } else {
                        markList.add(new MarkListItemBean("", "", false, wordPop.getText().toString(), MARK_TYPE_TV));

                        markHaveFillList[5] = true;
                    }

                } else if (TextUtils.isEmpty(wordPop.getText().toString()) && wordPop.getVisibility() == View.VISIBLE) {
                    if (markHaveFillList[5]) {
                        for (int i = 0; i < markList.size(); i++) {
                            if (markList.get(i).getType() == MARK_TYPE_TV) {
                                markList.remove(i);
                                break;
                            }
                        }
                    }
                    markHaveFillList[5] = false;
                }

                if (markList.size() == 0) {
                    markList.add(new MarkListItemBean("", "", false, "", MARK_TYPE_NONE));
                } else {
                    for (int i = 0; i < markList.size(); i++) {
                        MarkListItemBean markListItemBean = markList.get(i);
                        if (markList.get(i).getType() == MARK_TYPE_TV) {
                            markList.remove(i);
                            markList.add(markListItemBean);
                            break;
                        }
                    }
                }

                mMarkPop.dismiss();
                marKListAdapter.notifyDataSetChanged();
            }
        });


        showMarkPop();

    }

    /**
     * 初始化媒体选择弹框
     */
    private void initChooseMediaPop() {

        mCirclePop = EasyPopup.create()
                .setContentView(AddNewPhotoWordActivity.this, R.layout.choose_media_type_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(false)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView((ViewGroup) findViewById(android.R.id.content))
                .apply();


        TextView cancel = mCirclePop.findViewById(R.id.cancel);
        ConstraintLayout photoCl = mCirclePop.findViewById(R.id.pic_cl);
        ConstraintLayout videoCl = mCirclePop.findViewById(R.id.video_cl);
        //quxiao
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
            }
        });

        //选择照片
        photoCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMode = PictureMimeType.ofImage();
                maxSelectNum = 9;
                choosePhoto();
                mCirclePop.dismiss();
            }
        });

        //选择视频
        videoCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxSelectNum = 1;
                chooseMode = PictureMimeType.ofVideo();
                choosePhoto();
                mCirclePop.dismiss();
            }
        });
    }

    /**
     * 弹出选择框
     */
    private void showTypeChoosePop() {


        mCirclePop.showAtAnchorView(findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);

    }

    /**
     * 弹出选择框
     */
    private void showMarkPop() {


        mMarkPop.showAtAnchorView(findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);

    }

    /**
     * 清除缓存
     */

    private void clearCash() {

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(AddNewPhotoWordActivity.this);
                } else {
                    Toast.makeText(AddNewPhotoWordActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 相册选择
     */
    private void choosePhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(AddNewPhotoWordActivity.this)
                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                .videoQuality(1)// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                .recordVideoSecond(20)//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 图片添加按钮点击事件
     */
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showTypeChoosePop();
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //添加备注按钮
            case R.id.mark_add_iv:
            case R.id.mark_add_tv:
                initMarkPop(MARK_TYPE_ALL, false);
                break;

            case R.id.save_btn:
                if (selectList.size() == 0) {
                    showCenter(AddNewPhotoWordActivity.this, "请选择照片或图片!");
                } else {
                    commit();
                }
                break;
        }

    }

    /*
     * Java文件操作 获取文件扩展名
     * */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


    public static final String MULTIPART_FORM_DATA = "image/jpg";       // 指明要上传的文件格式


    /**
     * 提交
     */
    private void commit() {

        loadingDialog.setHintText("上传中");
        loadingDialog.show();

        //如果是视频的话
        if (PictureMimeType.isPictureType(selectList.get(0).getPictureType()) == PictureConfig.TYPE_VIDEO) {


            //构建要上传的文件
            File file = new File(selectList.get(0).getPath());   // 需要上传的文件

      /*      RequestBody requestFile =               // 根据文件格式封装文件
                    RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

            // 初始化请求体对象，设置Content-Type以及文件数据流
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)            // multipart/form-data
                    .addFormDataPart(partName, file.getName(), requestFile)
                    .build();*/

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data;charset=utf-8"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);


            Call<CheckCodeBean> call = RetrofitService.createMyAPI().uploadFile(body,getExtensionName(file.getAbsolutePath()));
            call.enqueue(new Callback<CheckCodeBean>() {
                @Override
                public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                    loadingDialog.dismiss();
                }
            });





        } else {
            //如果是图片

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < selectList.size(); i++) {
                stringBuffer.append(ImageUtil.getBase64(selectList.get(i).getCompressPath()));
                if (i != selectList.size() - 1) {
                    stringBuffer.append(",");
                }
            }

            Call<CheckCodeBean> call = RetrofitService.createMyAPI().UploadImage(stringBuffer.toString());
            call.enqueue(new Callback<CheckCodeBean>() {
                @Override
                public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                    showBottom(AddNewPhotoWordActivity.this,response.body().getInfo());
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                    loadingDialog.dismiss();
                }
            });

        }

    }

    /**
     * 备注列表normalItem侧滑中的点击事件 删除
     *
     * @param position
     */
    @Override
    public void del(int position, int type) {
        markList.remove(position);

        switch (type) {
            case MARK_TYPE_GOOD_NUM:
                markHaveFillList[0] = false;
                break;
            case MARK_TYPE_GET_PRICE:
                markHaveFillList[1] = false;
                break;
            case MARK_TYPE_SALE_PRICE:
                markHaveFillList[2] = false;
                break;
            case MARK_TYPE_PF_PRICE:
                markHaveFillList[3] = false;
                break;
            case MARK_TYPE_PACK_PRICE:
                markHaveFillList[4] = false;
                break;
            case MARK_TYPE_TV:
                markHaveFillList[5] = false;
                break;
        }

        if (markList.size() == 0) {
            markList.add(new MarkListItemBean("", "", false, "", MARK_TYPE_NONE));
        }

        marKListAdapter.notifyDataSetChanged();
    }

    @Override
    public void modify(int type) {

        initMarkPop(type, false);
        showMarkPop();
    }


    /**
     * 备注列表normalItem侧滑中的点击事件 编辑
     *
     * @param type item 类型
     */
    @Override
    public void modify(int type, boolean isOpen) {

        initMarkPop(type, isOpen);
        showMarkPop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCash();
    }
}
