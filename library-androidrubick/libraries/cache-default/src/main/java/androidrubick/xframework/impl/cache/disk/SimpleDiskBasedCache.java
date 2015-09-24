package androidrubick.xframework.impl.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import androidrubick.io.FileUtils;
import androidrubick.io.IOUtils;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.XLog;
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
@Configurable
/*package*/abstract class SimpleDiskBasedCache implements XDiskBasedCache {

    private static final String TAG = "disk cache";

    /** {@value */
    @Configurable
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    /** {@value */
    @Configurable
    public static final int DEFAULT_COMPRESS_QUALITY = 100;


    private final Bitmap.CompressFormat mCF = DEFAULT_COMPRESS_FORMAT;
    private final int mQuality = DEFAULT_COMPRESS_QUALITY;
//    private File mDir;
    private XDiskCaches.CacheInfo mCacheInfo;
//    SimpleDiskBasedCache(File parentDir, String subDirName) {
//        mDir = new File(parentDir, subDirName);
//    }

//    @Override
//    public File getDirectory() {
//        return mDir;
//    }

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
        return FileUtils.deleteFile(getFileByName(fileName), true, null);
    }

    @Override
    public boolean exists(String fileName) {
        return FileUtils.exists(getFileByName(fileName));
    }

    @Override
    public boolean save(String fileName, InputStream ins, boolean closeIns) throws IOException {
        try {
            File targetFile = getFileByName(fileName);
            if (ensureTargetFile(targetFile)) {
                return FileUtils.saveToFile(ins, false, targetFile, false, null, null);
            }
            return false;
        } finally {
            IOUtils.close(ins);
        }
    }

    @Override
    public boolean save(String fileName, byte[] data) throws IOException {
        File targetFile = getFileByName(fileName);
        if (ensureTargetFile(targetFile)) {
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
        try {
            File targetFile = getFileByName(fileName);
            if (ensureTargetFile(targetFile)) {
                return FileUtils.saveToFile(ins, false, targetFile, false, charsetName, null, null);
            }
            return false;
        } finally {
            IOUtils.close(ins);
        }
    }

    @Override
    public boolean save(String fileName, Bitmap bm) throws IOException {
        File targetFile = getFileByName(fileName);
        if (ensureTargetFile(targetFile)) {
            FileOutputStream out = FileUtils.openFileOutput(targetFile, true, false);
            try {
                return bm.compress(mCF, mQuality, out);
            } finally {
                IOUtils.close(out);
            }
        }
        return false;
    }

    private boolean ensureTargetFile(File targetFile) {
        if (FileUtils.deleteFile(targetFile, true, null)) {
            return true;
        }
        XLog.w(TAG, "file cannot save, reason : cannot delete");
        return false;
    }

    /**
     * 此处可以尝试优化，存储时临时创建的File对象蛮多
     */
    @Configurable
    private File getFileByName(String fileName) {
        Preconditions.checkArgument(!Strings.isEmpty(fileName),
                "fileName cannot be null or empty");
        return new File(getDirectory(), fileName);
    }

//    /**
//     * 以当前缓存目录为父目录，创建名以<code>subDir</code>子目录为根目录的缓存
//     */
//    public XDiskBasedCache subDirCache(String subDir) {
//        return new SimpleDiskBasedCache(getDirectory(), subDir);
//    }
//
//    /*package*/ void cacheRootChanged(File suitableDir) {
//        mDir = new File(suitableDir, mDir.getName());
//    }
}
