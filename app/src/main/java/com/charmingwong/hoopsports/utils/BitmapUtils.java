package com.charmingwong.hoopsports.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by CharmingWong on 2016/12/10.
 */

public class BitmapUtils {


    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos;
        try {
            baos = new ByteArrayOutputStream();
            if (bitmap != null) {
                boolean isCompressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                if (isCompressed) {
                    return baos.toByteArray();
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap compressBitmapByResId(Context context, int ResId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeResource(context.getResources(), ResId, options);
    }
}
