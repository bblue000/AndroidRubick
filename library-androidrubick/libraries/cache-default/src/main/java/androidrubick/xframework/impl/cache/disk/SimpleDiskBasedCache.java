package androidrubick.xframework.impl.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import androidrubick.io.FileUtils;
import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.cache.disk.XDiskBasedCache;
import androidrubick.xframework.cache.disk.XDiskCaches;

/**
 *
 * everything should be invoked in sub threads
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/20 0020.
 *
 * @since 1.0
 */
public class SimpleDiskBasedCache implements XDiskBasedCache {

    /** {@value */
    @Configurable
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    /** {@value */
    @Configurable
    public static final int DEFAULT_COMPRESS_QUALITY = 100;


    private final Bitmap.CompressFormat mCF = DEFAULT_COMPRESS_FORMAT;
    private final int mQuality = DEFAULT_COMPRESS_QUALITY;
    private File mDir;
    private XDiskCaches.CacheInfo mCacheInfo;
    public SimpleDiskBasedCache(String dirName) {
        this(null, dirName);
    }

    SimpleDiskBasedCache(File parentDir, String subDirName) {
        mDir = new File(parentDir, subDirName);
    }

    @Override
    public File getDirectory() {
        return mDir;
    }

    /**
     * 暂时是不准确的，不是同步的
     */
    @Override
    public long getFileCount() {
        return Objects.isNull(mCacheInfo) ? 0 : mCacheInfo.fileCount;
    }

    /**
     * 暂时是不准确的，不是同步的
     */
    @Override
    public long getDirCount() {
        return Objects.isNull(mCacheInfo) ? 0 : mCacheInfo.dirCount;
    }

    /**
     * 暂时是不准确的，不是同步的
     */
    @Override
    public long getByteSize() {
        return Objects.isNull(mCacheInfo) ? 0 : mCacheInfo.size;
    }

    @Override
    public void clear() {
        FileUtils.deleteFile(getDirectory(), false, null);
    }

    @Override
    public boolean remove(String fileName) {
        return FileUtils.deleteFile(new File(getDirectory(), fileName), true, null);
    }

    @Override
    public boolean exists(String fileName) {
        return FileUtils.exists(new File(getDirectory(), fileName));
    }

    @Override
    public boolean save(String fileName, InputStream ins, boolean closeIns) throws IOException {
        File targetFile = new File(getDirectory(), fileName);
        if (FileUtils.deleteFile(targetFile, true, null)) {
            return FileUtils.saveToFile(ins, closeIns, targetFile, false, null, null);
        }
        return false;
    }

    @Override
    public boolean save(String fileName, byte[] data) throws IOException {
        File targetFile = new File(getDirectory(), fileName);
        if (FileUtils.deleteFile(targetFile, true, null)) {
            return FileUtils.saveToFile(data, targetFile, false, null, null);
        }
        return false;
    }

    @Override
    public boolean save(String fileName, String data, String charsetName) throws IOException {
        return save(fileName, data.getBytes(Objects.getOr(charsetName, XGlobals.ProjectEncoding)));
    }

    @Override
    public boolean save(String fileName,
                        Reader ins, String charsetName, boolean closeIns) throws IOException {
        File targetFile = new File(getDirectory(), fileName);
        if (FileUtils.deleteFile(targetFile, true, null)) {
            return FileUtils.saveToFile(ins, closeIns, targetFile, false, charsetName, null, null);
        }
        return false;
    }

    @Override
    public boolean save(String fileName, Bitmap bm) throws IOException {
        File targetFile = new File(getDirectory(), fileName);
        if (FileUtils.deleteFile(targetFile, true, null)) {
            FileOutputStream out = FileUtils.openFileOutput(targetFile, true, false);
            try {
                return bm.compress(mCF, mQuality, out);
            } finally {
                IOUtils.close(out);
            }
        }
        return false;
    }

    /**
     * 以当前缓存目录为父目录，创建名以<code>subDir</code>子目录为根目录的缓存
     */
    public XDiskBasedCache subDirCache(String subDir) {
        return new SimpleDiskBasedCache(getDirectory(), subDir);
    }
}
