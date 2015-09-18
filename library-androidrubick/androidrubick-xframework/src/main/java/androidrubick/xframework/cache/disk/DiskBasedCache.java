package androidrubick.xframework.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import androidrubick.io.FileUtils;
import androidrubick.xframework.cache.base.MeasurableCache;

/**
 *
 * 以{@link }为根目录的文件缓存
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public abstract class DiskBasedCache {

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    /**
     * 获取该缓存的根目录
     */
    public abstract File getRootPath();

    /**
     * 获取缓存目录中文件的数目，不包括子文件夹
     */
    public abstract long getFileCount();

    /**
     * 获取缓存目录中文件夹的数目
     */
    public abstract long getDirCount();

    /**
     * 当前缓存文件目录的总容量
     */
    public abstract long getByteSize();

    public void clear() {
        FileUtils.deleteFile(getRootPath())
    }

    public boolean save(String fileName, InputStream ins, boolean closeIns) {
        File targetFile = new File(getRootPath(), fileName);
        if (FileUtils.deleteFile(targetFile, true, null)) {
            FileUtils.saveToFile(ins, closeIns, targetFile, false, null, null);
        }
    }
    public abstract boolean save(String fileName, byte[] data) ;
    public abstract boolean save(String fileName, String data) ;
    public abstract boolean save(String fileName, Reader ins, String charsetName, boolean closeIns) ;
    public abstract boolean save(String fileName, Reader ins, Charset charset, boolean closeIns) ;
    public abstract boolean save(String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality) ;

    /**
     * 以当前缓存目录为父目录，创建名以<code>subDir</code>子目录为根目录的缓存
     */
    public abstract DiskBasedCache subDirCache(String subDir) ;
}
