package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Plus on 01.05.2017.
 */

public class PictureUtils {


    //Метод проверяет размер экрана и уменьшает изображение до этого размера.
    public static  Bitmap getScaledBitmap (String path, Activity activity){
        Point size = new Point();

        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return  getScaledBitmap( path, size.x, size.y );
    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        //Чтение размеров изображения на диске.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( path, options );

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //Вычисление степини масштабирования.
        int inSampleSize = 1;
        if (srcHeight > srcHeight || srcWidth > srcWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round( srcHeight / destHeight );
            } else {
                inSampleSize = Math.round( srcWidth / destWidth );
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Чтение данных и создание итогового изображения.
        return BitmapFactory.decodeFile( path, options );
    }
}

