package com.ubiRobot.modules.user.collectionCode;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.ubiRobot.R;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.app.UrlFactory;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.BaseRobotBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.http.callback.JsonCallback;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionCodeActivity extends BaseActivity<CollectionCodeView, CollectionCodePresenter> implements CollectionCodeView {


    Bitmap bitmap;
    @BindView(R.id.alipay_code)
    ImageView alipayCode;
    @BindView(R.id.wechat_code)
    ImageView wechatCode;

    boolean isAlipay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_code;
    }

    @Override
    public CollectionCodePresenter initPresenter() {
        return new CollectionCodePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.my_skm));
        verifyStoragePermissions(this);
        UserBean user = MyApp.getmInstance().getUser();
        if (!Util.isNullOrEmpty(user.getPic2())) {
            alipayCode.setEnabled(false);
            Glide.with(activity)
                    .load(UrlFactory.host1 + user.getPic2())
                    .into(alipayCode);
        }
        if (!Util.isNullOrEmpty(user.getPic1())) {
            wechatCode.setEnabled(false);
            Glide.with(activity)
                    .load(UrlFactory.host1 + user.getPic1())
                    .into(wechatCode);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override


    public void requestFail(int code, String msg) {

    }

    @Override
    public void uplodeSuccess(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void onViewClicked() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                Uri uri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (isAlipay) {
                    alipayCode.setImageBitmap(bitmap);
                } else {
                    wechatCode.setImageBitmap(bitmap);
                }
                upLodeImage(bitmap);
            }
        } catch (Exception e) {
            ToastUtils.showLongToast(this, "Something went wrong" + e.getMessage());
        }

    }

    private void upLodeImage(Bitmap bm) {
        String url = UrlFactory.SetPaymentProfileUrl();
        File file = getFile(bm, "pic");
        OkGo.<BaseRobotBean>post(url)
                .tag(this)
                .params("Access_Token", SharedPrefsUitls.getInstance().getUserToken())
                .params(isAlipay ? "pic2" : "pic1", file)
                .isMultipart(true)
                .execute(new JsonCallback<BaseRobotBean>() {
                    @Override
                    public void onSuccess(Response<BaseRobotBean> response) {

                        ToastUtils.showLongToast(activity, "上传成功");
                    }

                    @Override
                    public void onError(Response<BaseRobotBean> response) {
                        ToastUtils.showLongToast(activity, "上传失败");
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                        MyLog.i("progress======"+progress.fraction*100);

                    }
                });
    }

    /**
     * 将bitmap转为File *phone 是我给图片命名用的，你不需要可以不加，用时间命名也行
     */
    public File getFile(Bitmap bitmap, String fname) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String headImgName = fname + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory() + "/" + headImgName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @OnClick({R.id.alipay_code, R.id.wechat_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_code:
                isAlipay = true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
                break;
            case R.id.wechat_code:
                isAlipay = false;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
        }
    }

    //先定义
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    //然后通过一个函数来申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
