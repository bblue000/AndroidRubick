package androidrubick.xframework.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import androidrubick.io.FileUtils;

/**
 *
 * 以{@link }为根目录的文件缓存；
 *
 * <p/>
 *
 * 个人感觉
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public abstract class DiskBasedCache {

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

    /**
     * 清除缓存
     */
    public void clear() {
        FileUtils.deleteFile(getRootPath(), false, null);
    }

    /**
     * 判断文件名为<code>fileName</code>缓存是否存在。
     */
    public boolean exists(String fileName) {
        return FileUtils.exists(new File(getRootPath(), fileName));
    }

//    public boolean save(String fileName, InputStream ins, boolean closeIns) {
//        File targetFile = new File(getRootPath(), fileName);
//        if (FileUtils.deleteFile(targetFile, true, null)) {
//            FileUtils.saveToFile(ins, closeIns, targetFile, false, null, null);
//        }
//    }
//
//    public boolean save(String fileName, byte[] data) {
//        File targetFile = new File(getRootPath(), fileName);
//        if (FileUtils.deleteFile(targetFile, true, null)) {
//            FileUtils.saveToFile(ins, closeIns, targetFile, false, null, null);
//        }
//    }
//    public abstract boolean save(String fileName, String data, String charsetName) {
//        return save(fileName, data.getBytes(charsetName));
//    }
    public abstract boolean save(String fileName, Reader ins, String charsetName, boolean closeIns) ;
    public abstract boolean save(String fileName, Reader ins, Charset charset, boolean closeIns) ;
    public abstract boolean save(String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality) ;

//    /**
//     * 以当前缓存目录为父目录，创建名以<code>subDir</code>子目录为根目录的缓存
//     */
//    public abstract DiskBasedCache subDirCache(String subDir) ;
}
