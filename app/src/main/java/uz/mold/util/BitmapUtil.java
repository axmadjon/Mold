package uz.mold.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import uz.mold.collection.MyArray;

public class BitmapUtil {

    //decode image size
    public static Bitmap decodeFile(File f, int requiredSize) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            //Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    // convert from bitmap to byte array
    public static byte[] toBytes(Bitmap bitmap, int quality) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap toBitmap(byte[] image) {
        if (image == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap drawTextToBitmap(Bitmap photo, MyArray<String> texts) {
        int textSize = 12 * 2;
        int textLeng = texts.size();

        Bitmap bitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        canvas.drawRect(0,
                bitmap.getHeight() - (textSize * (textLeng + 1)),
                bitmap.getWidth(),
                bitmap.getHeight(), p);


        for (String text : texts) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setTextSize(textSize);
            paint.setShadowLayer(4f, 0, 0, Color.WHITE);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int y = (int) (bitmap.getHeight() - (paint.getTextSize() * textLeng--));
            canvas.drawText(text, 10, y, paint);
        }

        return bitmap;
    }

}
