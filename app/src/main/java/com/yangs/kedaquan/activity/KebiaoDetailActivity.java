package com.yangs.kedaquan.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yangs.kedaquan.APPAplication;
import com.yangs.kedaquan.R;

/**
 * Created by yangs on 2017/2/16.
 */

public class KebiaoDetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;
    private TextView kcm;
    private TextView kcdm;
    private TextView ls;
    private TextView js;
    private TextView zc;
    private TextView jc;
    private int index;
    private Bundle bundle;
    private SimpleDraweeView v_ad;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebiao_detail_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kebiao_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        toolbar = findViewById(R.id.kebiao_detail_toolbar);
        kcm = findViewById(R.id.kebiao_detail_kcm);
        kcdm = findViewById(R.id.kebiao_detail_kcdm);
        ls = findViewById(R.id.kebiao_detail_ls);
        js = findViewById(R.id.kebiao_detail_js);
        zc = findViewById(R.id.kebiao_detail_zc);
        jc = findViewById(R.id.kebiao_detail_jc);
        v_ad = findViewById(R.id.kebiao_detail_ad);
        toolbar.setNavigationIcon(R.drawable.ic_arraw_back_white);
        bundle = this.getIntent().getExtras();
        index = Integer.parseInt(bundle.getString("index"));
        kcm.setText(bundle.getString("kcm"));
        kcdm.setText(bundle.getString("kcdm"));
        ls.setText(bundle.getString("ls"));
        js.setText(bundle.getString("js"));
        zc.setText(bundle.getString("zc"));
        toolbar.setTitle(bundle.getString("kcm"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(this);
        switch (bundle.getString("jc").split("\\s+")[0]) {
            case "1":
                jc.setText("周一 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "2":
                jc.setText("周二 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "3":
                jc.setText("周三 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "4":
                jc.setText("周四 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "5":
                jc.setText("周五 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "6":
                jc.setText("周六 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;
            case "7":
                jc.setText("周日 第" + bundle.getString("jc").split(" ")[1] + "大节");
                break;

        }
        String status = APPAplication.save.getString("kbxx_status", "关");
        if (status.equals("开")) {
            String url = APPAplication.save.getString("kbxx_url", "");
            v_ad.setImageURI(url);
            v_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String qq = APPAplication.save.getString("kbxx_remark", "");
                    if (qq.equals(""))
                        return;
                    PackageManager packageManager = getPackageManager();
                    try {
                        packageManager.getPackageInfo("com.tencent.mobileqq", 0);
                        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (PackageManager.NameNotFoundException e) {
                        APPAplication.showToast("无法拉起手机qq", 0);
                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            KebiaoDetailActivity.this.setResult(1);
            finish();
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kebiao_detail_menu_change:
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view = layoutInflater.inflate(R.layout.kebiao_detail_change_dailog, null);
                final EditText c_kcm = view.findViewById(R.id.kebiao_detail_kcm_c);
                final EditText c_kcdm = view.findViewById(R.id.kebiao_detail_kcdm_c);
                final EditText c_ls = view.findViewById(R.id.kebiao_detail_ls_c);
                final EditText c_js = view.findViewById(R.id.kebiao_detail_js_c);
                final EditText c_zc = view.findViewById(R.id.kebiao_detail_zc_c);
                final EditText c_jc = view.findViewById(R.id.kebiao_detail_jc_c);
                Dialog dialog = new AlertDialog.Builder(this).setView(view).setTitle("修改课程")
                        .setCancelable(false)
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (TextUtils.isEmpty(c_kcm.getText().toString())) {
                                    APPAplication.showToast("修改失败,课程名为必填项!", 1);
                                } else if (TextUtils.isEmpty(c_zc.getText().toString())) {
                                    APPAplication.showToast("修改失败,周次为必填项!", 1);
                                } else {
                                    kcm.setText(c_kcm.getText());
                                    kcdm.setText(c_kcdm.getText());
                                    ls.setText(c_ls.getText());
                                    js.setText(c_js.getText());
                                    zc.setText(c_zc.getText());
                                    int i = Integer.parseInt(c_jc.getText().toString().split("\\s+")[0]);
                                    int j = Integer.parseInt(c_jc.getText().toString().split("\\s+")[1]);
                                    switch (i) {
                                        case 1:
                                            jc.setText("周一 第" + j + "大节");
                                            break;
                                        case 2:
                                            jc.setText("周二 第" + j + "大节");
                                            break;
                                        case 3:
                                            jc.setText("周三 第" + j + "大节");
                                            break;
                                        case 4:
                                            jc.setText("周四 第" + j + "大节");
                                            break;
                                        case 5:
                                            jc.setText("周五 第" + j + "大节");
                                            break;
                                        case 6:
                                            jc.setText("周六 第" + j + "大节");
                                            break;
                                        case 7:
                                            jc.setText("周日 第" + j + "大节");
                                            break;

                                    }
                                    SQLiteDatabase db = KebiaoDetailActivity.this.openOrCreateDatabase("info.db", Context.MODE_PRIVATE, null);
                                    String sql = "update course set 课程名='" + c_kcm.getText().toString() + "',课程代码='" + c_kcdm.getText().toString() + "',教室='" + c_js.getText().toString() + "',老师='" + c_ls.getText().toString() + "',星期=" + i + ",节次=" + j + ",周次='" + c_zc.getText().toString() + "' where id=" + index;
                                    db.execSQL(sql);
                                    Toast.makeText(KebiaoDetailActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create();
                c_kcm.setText(kcm.getText());
                c_kcdm.setText(kcdm.getText());
                c_ls.setText(ls.getText());
                c_js.setText(js.getText());
                c_zc.setText(zc.getText());
                c_jc.setText(bundle.getString("jc"));
                dialog.show();
                break;
            case R.id.kebiao_detail_menu_delete:
                new AlertDialog.Builder(this).setTitle("提示").setMessage("您确定要删除这节课吗?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SQLiteDatabase db = KebiaoDetailActivity.this.openOrCreateDatabase("info.db", Context.MODE_PRIVATE, null);
                        db.execSQL("delete from course where id = " + index);
                        db.close();
                        KebiaoDetailActivity.this.setResult(1);
                        finish();
                    }
                }).show();
                break;
        }
        return true;
    }
}
