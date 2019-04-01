package com.hamitao.kids.player;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean;
import com.hamitao.kids.util.MusicUtils;
import com.hamitao.base_module.util.ImageUtils;
import com.hamitao.base_module.util.ScreenUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by linjianwen on 2018/3/1.
 * 封面加载器
 */

public class CoverLoader {

    public static final int THUMBNAIL_MAX_LENGTH = 500;
    private static final String KEY_NULL = "null";

    // 封面缓存
    private LruCache<String, Bitmap> mCoverCache;
    private Context mContext;

    private enum Type {
        THUMBNAIL(""),
        BLUR("#BLUR"),
        ROUND("#ROUND");

        private String value;

        Type(String value) {
            this.value = value;
        }
    }

    public static CoverLoader getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CoverLoader instance = new CoverLoader();
    }

    private CoverLoader() {
        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        mCoverCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return bitmap.getAllocationByteCount() / 1024;
                } else {
                    return bitmap.getByteCount() / 1024;
                }
            }
        };
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public Bitmap loadThumbnail(MediaListBean music) {
        return loadCover(music, Type.THUMBNAIL);
    }

    public Bitmap loadBlur(MediaListBean music) {
        return loadCover(music, Type.BLUR);
    }

    public Bitmap loadRound(MediaListBean music) {
        return loadCover(music, Type.ROUND);
    }

    private Bitmap loadCover(MediaListBean music, Type type) {
        Bitmap bitmap;
        String key = getKey(music, type);
        if (TextUtils.isEmpty(key)) {
            bitmap = mCoverCache.get(KEY_NULL.concat(type.value));
            if (bitmap != null) {
                return bitmap;
            }

            bitmap = getDefaultCover(type);
            mCoverCache.put(KEY_NULL.concat(type.value), bitmap);
            return bitmap;
        }

        bitmap = mCoverCache.get(key);
        if (bitmap != null) {
            return bitmap;
        }

        bitmap = loadCoverByType(music, type);
        if (bitmap != null) {
            mCoverCache.put(key, bitmap);
            return bitmap;
        }

        return loadCover(null, type);
    }

    private String getKey(MediaListBean music, Type type) {
        if (music == null) {
            return null;
        }

//        if (music.getType() == Music.Type.LOCAL && music.getAlbumId() > 0) {
//            return String.valueOf(music.getAlbumId()).concat(type.value);
//        } else if (music.getType() == Music.Type.ONLINE && !TextUtils.isEmpty(music.getCoverPath())) {
//            return music.getCoverPath().concat(type.value);
//        } else {
//            return null;
//        }
        return null;
    }


    /**
     * 获取默认封面
     *
     * @param type
     * @return
     */
    private Bitmap getDefaultCover(Type type) {
        switch (type) {
            case BLUR:
                return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_cover);
            case ROUND:
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_cover);
                bitmap = ImageUtils.resizeImage(bitmap, ScreenUtil.getScreenWidth(mContext) / 2, ScreenUtil.getScreenWidth(mContext) / 2);
                return bitmap;
            default:
                return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_cover);
        }
    }


    /**
     * 加载网络图片
     */
    public Bitmap getNetImg(String imgUrl) {

        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(mContext)
                    .load(imgUrl)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(500, 500)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return myBitmap;
    }




    /**
     * 根据类型加载封面
     *
     * @param music
     * @param type
     * @return
     */
    private Bitmap loadCoverByType(MediaListBean music, Type type) {
        Bitmap bitmap;
//        if (music.getType() == Music.Type.LOCAL) {
//            bitmap = loadCoverFromMediaStore(music.getAlbumId());
//        } else {
//            bitmap = loadCoverFromFile(music.getCoverPath());
//        }

        bitmap = loadCoverFromFile(music.getHttpURL());
        switch (type) {
            case BLUR:
                return ImageUtils.blur(bitmap);
            case ROUND:
                bitmap = ImageUtils.resizeImage(bitmap, ScreenUtil.getScreenWidth(mContext) / 2, ScreenUtil.getScreenWidth(mContext) / 2);
                return ImageUtils.createCircleImage(bitmap);
            default:
                return bitmap;
        }
    }

    /**
     * 从媒体库加载封面<br>
     * 本地音乐
     */
    private Bitmap loadCoverFromMediaStore(long albumId) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MusicUtils.getMediaStoreAlbumCoverUri(albumId);
        InputStream is;
        try {
            is = resolver.openInputStream(uri);
        } catch (FileNotFoundException ignored) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 从下载的图片加载封面<br>
     * 网络音乐
     */
    public Bitmap loadCoverFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }
}
