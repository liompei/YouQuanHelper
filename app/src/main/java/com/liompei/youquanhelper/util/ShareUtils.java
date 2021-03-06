package com.liompei.youquanhelper.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * Time 2017/9/10 22:02
 * 1137694912@qq.com
 * remark:
 */

public class ShareUtils {

    /**
     * 不实用微信的SDK分享图片到好友
     * @param context
     * @param path
     */
    public static void sharePicToFriendNoSDK(Context context, String path) {
        if(!isInstallWeChart(context)){
            Toast.makeText(context,"您没有安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        // intent.setFlags(0x3000001);
        File f = new File(path);
        if(f.exists()){
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        } else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intent);
    }


    /**
     * 分享9图到朋友圈
     *
     * @param activity
     * @param Kdescription 9图上边输入框中的文案
     * @param paths        本地图片的路径
     */
    public static void share9PicsToWXCircle(Activity activity, String Kdescription, List<String> paths) {
        if (!isInstallWeChart(activity)) {
            Toast.makeText(activity,"您没有安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        intent.setAction("android.intent.action.SEND_MULTIPLE");

        ArrayList<Uri> imageList = new ArrayList<Uri>();
        for (String picPath : paths) {
            imageList.add(Uri.parse(picPath));
//            File f = new File(picPath);
//            if (f.exists()) {
//                imageList.add(Uri.fromFile(f));
//            }
        }
        if(imageList.size() == 0){
            Toast.makeText(activity,"图片不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        intent.setType("image/*");
        intent.putExtra("Kdescription", Kdescription); //微信分享页面，图片上边的描述
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageList);
        activity.startActivityForResult(intent, 10);
//        intent.putExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
//        context.startActivity(intent);
    }

    /**不实用微信sdk检查是否安装微信
     * @param context
     * @return
     */
    public static boolean isInstallWeChart(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void shareToWeChat(Activity activity,String content, ArrayList<Uri> uris) {
        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            intent.putExtra("Kdescription", content);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            activity.startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(activity.getApplicationContext(), "未检测到微信，请先安装微信！", Toast.LENGTH_SHORT).show();
        }
    }

}
