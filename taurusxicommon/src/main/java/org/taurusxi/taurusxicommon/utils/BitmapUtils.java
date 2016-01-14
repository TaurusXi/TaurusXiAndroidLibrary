package org.taurusxi.taurusxicommon.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by storm on 14-6-17.
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap drawViewToBitmap(View view, int width, int height, int downSampling) {
        return drawViewToBitmap(view, width, height, 0f, 0f, downSampling);
    }

    public static Bitmap drawViewToBitmap(View view, int width, int height, float translateX,
                                          float translateY, int downSampling) {
        float scale = 1f / downSampling;
        int bmpWidth = (int) (width * scale - translateX / downSampling);
        int bmpHeight = (int) (height * scale - translateY / downSampling);
        Bitmap dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        c.translate(-translateX / downSampling, -translateY / downSampling);
        if (downSampling > 1) {
            c.scale(scale, scale);
        }
        view.draw(c);
        return dest;
    }


    public static Bitmap getInSampleBitmap(String filePath, int width, int height) {
        int widthNeed = (int) (width * DisplayUtil.scale);
        int heightNeed = (int) (height * DisplayUtil.scale);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int originalWidth = options.outWidth;
        int originalHight = options.outHeight;

        int inSample;
        if (originalWidth >= originalHight) {
            inSample = getInsampleSize(originalWidth, widthNeed);
        } else {
            inSample = getInsampleSize(originalHight, heightNeed);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSample;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getInSampleBitmapByBitmap(Bitmap bitmap) {
        int oldWith = bitmap.getWidth();
        int oldLength = bitmap.getHeight();
        if (oldWith >= oldLength) {

            if (oldWith >= 3000) {
                oldWith = oldWith / 4;
                oldLength = oldLength / 4;
            } else if (oldWith >= 1500) {
                oldWith = oldWith / 2;
                oldLength = oldLength / 2;
            }
        } else {
            if (oldLength >= 3000) {
                oldWith = oldWith / 4;
                oldLength = oldLength / 4;
            } else if (oldLength >= 1500) {
                oldWith = oldWith / 2;
                oldLength = oldLength / 2;
            }
        }
        Bitmap newbitmap = zoomImage(bitmap, oldWith, oldLength);
//        if (bitmap != null) {
//            bitmap.recycle();
//        }
        return newbitmap;
    }

    public static Bitmap getInSampleAvatarByBitmap(Bitmap bitmap) {
        int oldWith = bitmap.getWidth();
        int oldLength = bitmap.getHeight();
        if (oldWith >= oldLength) {
            if (oldWith >= 4000) {
                oldWith = oldWith / 10;
                oldLength = oldLength / 10;
            } else if (oldWith >= 3000) {
                oldWith = oldWith / 8;
                oldLength = oldLength / 8;
            } else if (oldWith >= 2000) {
                oldWith = oldWith / 6;
                oldLength = oldLength / 6;
            } else if (oldWith >= 1200) {
                oldWith = oldWith / 4;
                oldLength = oldLength / 4;
            } else if (oldWith >= 600) {
                oldWith = oldWith / 2;
                oldLength = oldLength / 2;
            }
        } else {
            if (oldLength >= 4000) {
                oldWith = oldWith / 10;
                oldLength = oldLength / 10;
            } else if (oldLength >= 3000) {
                oldWith = oldWith / 8;
                oldLength = oldLength / 8;
            } else if (oldLength >= 2000) {
                oldWith = oldWith / 6;
                oldLength = oldLength / 6;
            } else if (oldLength >= 1200) {
                oldWith = oldWith / 4;
                oldLength = oldLength / 4;
            } else if (oldLength >= 600) {
                oldWith = oldWith / 2;
                oldLength = oldLength / 2;
            }
        }
        Bitmap newbitmap = zoomImage(bitmap, oldWith, oldLength);
//        if (bitmap != null) {
//            bitmap.recycle();
//        }
        return newbitmap;
    }

    public static Bitmap getInSampleBitmapByFile(String file) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        int oldWith = options.outWidth;
        int oldLength = options.outHeight;
        int inSample = 1;
        if (oldWith >= oldLength) {

            if (oldWith >= 3000) {
                inSample = 4;
            } else if (oldWith >= 1500) {
                inSample = 2;
            }
        } else {
            if (oldLength >= 3000) {
                inSample = 4;
            } else if (oldLength >= 1500) {
                inSample = 2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSample;

        return BitmapFactory.decodeFile(file, options);
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private static int getInsampleSize(int origin, int need) {
        int inSample = 1;
        int size = origin / need;
        if (size >= 16) {
            inSample = 16;
        } else if (size >= 8) {
            inSample = 8;
        } else if (size >= 4) {
            inSample = 4;
        } else if (size >= 2) {
            inSample = 2;
        }

        return inSample;
    }

    public static int getBitmapDegree(String path, int degreeInt) throws IOException {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException ex) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1 && orientation != 0) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            } else if (orientation == 0 && degreeInt != 0) {
                switch (degreeInt) {
                    case 90:
                        orientation = ExifInterface.ORIENTATION_ROTATE_90;
                        degree = 90;
                        break;
                    case 180:
                        orientation = ExifInterface.ORIENTATION_ROTATE_180;
                        degree = 180;
                        break;
                    case 270:
                        orientation = ExifInterface.ORIENTATION_ROTATE_270;
                        degree = 270;
                        break;
                }
                exif.setAttribute(ExifInterface.TAG_ORIENTATION, Integer.toString(orientation));
                try {
                    exif.saveAttributes();
                } catch (IOException e) {
                }
            }

        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != null && bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }


    public static Bitmap convert(Bitmap a) {
        int w = a.getWidth();
        int h = a.getHeight();
        Bitmap newb = Bitmap.createBitmap(w, h, a.getConfig());// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
//        m.postScale(1, -1);   //镜像垂直翻转
        m.postScale(-1, 1);   //镜像水平翻转
//        m.postRotate(-90);  //旋转-90度
        Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, w, h), null);
        return newb;
    }


    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap zoomImage(Bitmap bgimage, float size) {
        float sqrt = (float) (1 / Math.sqrt(size));
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        // 缩放图片动作
        matrix.postScale(sqrt, sqrt);
        try {
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, bgimage.getWidth(),
                    bgimage.getHeight(), matrix, true);
            return bitmap;
        } catch (Exception e) {
            int i = 0;
            return null;
        }
    }

    public static Bitmap zoomImage(Bitmap bgimage) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        Matrix matrix = new Matrix();
        float v = 1500 / Math.max(width, height);
        matrix.postScale(v, v);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, bgimage.getWidth(),
                bgimage.getHeight(), matrix, true);
        return bitmap;
    }

    public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        //假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewWidth) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

            //为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }

    public static int computeScaleWithWidth(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        //假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewWidth) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

            //为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = heightScale > widthScale ? heightScale : widthScale;
        }
        return inSampleSize;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    public static FileAndProperty getThumbnailFile(String sourceFile, int width) {
        return getThumbnailFile(sourceFile, width, 85);
    }

    public static FileAndProperty getThumbnailFile(String sourceFile, int width, int quality) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sourceFile, options);
        int originalWidth = options.outWidth;
        int insampleSize = originalWidth / width;
        if (insampleSize >= 4) {
            insampleSize = 4;
        } else if (insampleSize >= 2) {
            insampleSize = 2;
        } else {
            insampleSize = 1;
        }
        options.inSampleSize = insampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(sourceFile, options);
        Bitmap newBitmap = null;
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(readPictureDegree(sourceFile)); // 旋转angle度
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);// 从新生成图片

        }
        if (newBitmap == null) {
            Log.e(TAG, "选择Bitmap失败");
            return null;
        }
        int height = width * newBitmap.getHeight() / newBitmap.getWidth();
        Bitmap extractThumbnail = ThumbnailUtils.extractThumbnail(newBitmap, width, height);
        FileAndProperty fileAndProperty = saveBitmap(sourceFile, extractThumbnail, quality);
        try {
            if (!extractThumbnail.isRecycled()) {
                extractThumbnail.recycle();
            }
            if (!newBitmap.isRecycled()) {
                newBitmap.recycle();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            MLog.e(TAG, "bitmap回收异常");
        }
        return fileAndProperty;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation =
                    exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }


    public static FileAndProperty saveBitmap(String soureFile, Bitmap bitmap, int quality) {
        Log.e(TAG, "保存图片");
        String fileName = soureFile + "_thumbnail";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
            return new FileAndProperty(file, bitmap.getWidth(), bitmap.getHeight());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "保存失败");
        return null;
    }


    public static FileAndProperty saveBrowserBitmap(String fileName, Bitmap bitmap, int quality) {
        if (bitmap == null) {
            return null;
        }
        Log.e(TAG, "保存图片");
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
            return new FileAndProperty(file, bitmap.getWidth(), bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "保存失败");
        return null;
    }


    public static class FileAndProperty {

        public File file;
        public int width;
        public int height;

        public FileAndProperty(File file, int width, int height) {
            this.file = file;
            this.width = width;
            this.height = height;
        }

        public FileAndProperty(String file, int width, int height) {
            this.file = new File(file);
            this.width = width;
            this.height = height;
        }
    }

}
