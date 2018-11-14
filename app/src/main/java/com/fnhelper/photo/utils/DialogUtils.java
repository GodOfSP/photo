package com.fnhelper.photo.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DialogUtils {

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     * @author ：shijing
     * 2016年12月5日下午4:34:46
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static void showAlertDialog(Context context, String commitText, String content, final OnCommitListener onCommitListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogU);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_alert);
            alertDialog.setCanceledOnTouchOutside(false);
            TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
            TextView cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView commit = (TextView) window.findViewById(R.id.tv_commit);
            commit.setText(commitText);
            tv_content.setText(content);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCommitListener.onCommit();
                    alertDialog.dismiss();
                }
            });
        }
    }

    public static void showExitDialog(Context context, String content, final OnCommitListener onCommitListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogU);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_alert);
            alertDialog.setCanceledOnTouchOutside(false);
            TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
            TextView cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView commit = (TextView) window.findViewById(R.id.tv_commit);
            tv_content.setText(content);
            cancel.setVisibility(View.GONE);
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onCommitListener != null) {
                        onCommitListener.onCommit();
                    }
                    alertDialog.dismiss();
                }
            });
        }
    }


    public interface OnCommitListener {
        void onCommit();
    }


    /**
     * 取消监听
     */
    public interface OnCancelListener {
        void onCancel();
    }


    /**
     * 登录 --  需要绑定提示框
     */
    public static void showLoginTips(Context context, final OnCommitListener onCommitListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogU);
        final AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_login_tips);
            alertDialog.setCanceledOnTouchOutside(false);
            TextView cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView commit = (TextView) window.findViewById(R.id.tv_commit);
            ImageView close = (ImageView) window.findViewById(R.id.close);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCommitListener.onCommit();
                    alertDialog.dismiss();
                }
            });
        }
    }

    /**
     * 删除 --  提示框
     */
    public static void showDelNewsTips(Context context, final OnCommitListener onCommitListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogU);
        final AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_login_tips);
            alertDialog.setCanceledOnTouchOutside(false);
            TextView cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView commit = (TextView) window.findViewById(R.id.tv_commit);
            TextView content = (TextView) window.findViewById(R.id.tv_content);
            ImageView close = (ImageView) window.findViewById(R.id.close);

            cancel.setText("取消");
            commit.setText("确认");
            content.setText("删除图文后将不可恢复，确认删除 ？");

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCommitListener.onCommit();
                    alertDialog.dismiss();
                }
            });
        }
    }

    /**
     * 退出 --  提示框
     */
    public static void showLogoutDialog(Context context, final OnCommitListener onCommitListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogU);
        final AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_login_tips);
            alertDialog.setCanceledOnTouchOutside(false);
            TextView cancel = (TextView) window.findViewById(R.id.tv_cancel);
            TextView commit = (TextView) window.findViewById(R.id.tv_commit);
            TextView content = (TextView) window.findViewById(R.id.tv_content);
            ImageView close = (ImageView) window.findViewById(R.id.close);

            cancel.setText("取消");
            commit.setText("确认");
            content.setText("退出后将不保留用户信息，确认退出 ？");

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCommitListener.onCommit();
                    alertDialog.dismiss();
                }
            });
        }
    }


}
