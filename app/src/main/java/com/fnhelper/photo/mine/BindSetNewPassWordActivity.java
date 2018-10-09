package com.fnhelper.photo.mine;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

/**
 * 设置新密码
 */
public class BindSetNewPassWordActivity extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.new_password)
    ClearEditText newPassword;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.new_password_again)
    ClearEditText newPasswordAgain;
    @BindView(R.id.show_password_again)
    ImageView showPasswordAgain;
    @BindView(R.id.login_btn)
    Button loginBtn;

    private String sPhone = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bind_set_new_pass_word);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comRight.setVisibility(View.GONE);
        comTitle.setText("设置新密码");
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sPhone = getIntent().getExtras().getString("sPhone");
        phoneNum.setText("手机号 : "+sPhone);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hidden(newPassword);
            }
        });
        showPasswordAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hidden1(newPasswordAgain);
            }
        });

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if ("".equals(newPassword.getText().toString().trim())) {
                   showCenter(BindSetNewPassWordActivity.this,"请输入新密码");
                   return;
               }
               if ("".equals(newPasswordAgain.getText().toString().trim())) {
                   showCenter(BindSetNewPassWordActivity.this,"请再次输入新密码");
                   return;
               }
               if (!newPassword.getText().toString().trim().equals(newPasswordAgain.getText().toString().trim())) {
                   showCenter(BindSetNewPassWordActivity.this,"输入两次新密码不相同");
                   return;
               }

               if (!isMatcherFinded("^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$", newPassword.getText().toString().trim())) {
                   showCenter(BindSetNewPassWordActivity.this, "请输入包括数字和字母、长度8到16位的密码组合");
                   return;
               }

               checkCode();
           }
       });

    }


    /**
     * 绑定
     */
    private void checkCode(){
        Call<CheckCodeBean> call = RetrofitService.createMyAPI().checkCode(sPhone,newPassword.getText().toString().trim());
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {

                if (response!=null){
                    if (response.body()!=null){
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(BindSetNewPassWordActivity.this,  response.body().getInfo());
                            finish();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(BindSetNewPassWordActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(BindSetNewPassWordActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(BindSetNewPassWordActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(BindSetNewPassWordActivity.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {

            }
        });
    }

    /**
     * 查看密码
     *
     * @param v
     */
    public void Hidden(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.close_eyes_pic));
        } else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.open_eyes_pic));
        }

    }

    /**
     * 查看密码
     *
     * @param v
     */
    public void Hidden1(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            showPasswordAgain.setImageDrawable(getResources().getDrawable(R.drawable.close_eyes_pic));
        } else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPasswordAgain.setImageDrawable(getResources().getDrawable(R.drawable.open_eyes_pic));
        }

    }


    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


}
