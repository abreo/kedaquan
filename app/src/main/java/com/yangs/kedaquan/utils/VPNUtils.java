package com.yangs.kedaquan.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.yangs.kedaquan.APPAplication;

/**
 * Created by yangs on 2017/11/16 0016.
 */

public class VPNUtils {
    public interface onReultListener {
        void onResult(int code, ProgressDialog progressDialog);
    }

    /*
    0: 有效
    -1: 密码错误
    -2: 在其他地方登录
    -3: network error
     */
    public static void checkVpnIsValid(Context context, final onReultListener onReultListener) {
        final Handler handler = new Handler();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("校检vpn登录态...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int code = APPAplication.vpnSource.checkVpnIsValid();
                switch (code) {
                    case 0:
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onReultListener.onResult(0, progressDialog);
                            }
                        });
                        break;
                    case -1:
                        code = APPAplication.vpnSource.checkVpnUser();
                        final int finalCode = code;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                switch (finalCode) {
                                    case 0:
                                        onReultListener.onResult(0, progressDialog);
                                        break;
                                    case -1:
                                        onReultListener.onResult(-1, progressDialog);
                                        break;
                                    case -2:
                                        onReultListener.onResult(-2, progressDialog);
                                        break;
                                    case -3:
                                        onReultListener.onResult(-3, progressDialog);
                                }
                            }
                        });
                        break;
                    case -2:
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onReultListener.onResult(-3, progressDialog);
                            }
                        });
                        break;
                }
            }
        }).start();
    }
}
