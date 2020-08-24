package com.gtp.myhistory.zxingutils.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class ZxingImageUtil {
    public static void savePicToLocal(Bitmap bitmap, Context context) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen" + File.separator + System.currentTimeMillis() + ".png";
        if (bitmap != null) {
            try {
                // 图片文件路径
                Log.i("ansen", "filePath:" + filePath);
                File file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File(filePath));
                intent.setData(uri);
                context.sendBroadcast(intent);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }
}
