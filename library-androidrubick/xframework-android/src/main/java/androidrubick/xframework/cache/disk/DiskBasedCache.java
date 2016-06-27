package androidrubick.xframework.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 * 以{@link #getDirectory()}为根目录的文件缓存；
 *
 * <p/>
 *
 * 个人感觉
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public abstract interface DiskBasedCache {

    /**
     * 获取该缓存的根目录；
     *
     * 由于开机状态下存储设备也可能发生安装、卸载、失效等状况，此时需要切换存储路径，
     *
     * 因此，该方法返回的路径不一定保持不变
     */
    File getDirectory();

    /**
     * 获取缓存目录中文件的数目，不包括子文件夹
     */
    long getFileCount();

    /**
     * 获取缓存目录中文件夹的数目
     */
    long getDirCount();

    /**
     * 当前缓存文件目录的总容量
     */
    long getByteSize();

    /**
     * 清除缓存
     */
    void clear() ;

    /**
     * Removes file associated with file name
     *
     * @param fileName file name
     * @return <b>true</b> - if file is deleted successfully; <b>false</b> - if file can't be deleted.
     */
    boolean remove(String fileName);

    /**
     * 判断文件名为<code>fileName</code>缓存是否存在。
     */
    boolean exists(String fileName) ;

    /**
     * Saves stream in disk cache.
     *
     * @param fileName    file name
     * @param ins         Input stream
     * @param closeIns    是否关闭ins
     *
     * @return <b>true</b> - if ins was saved successfully; <b>false</b> - if ins wasn't saved in disk cache.
     * @throws java.io.IOException
     */
    boolean save(String fileName, InputStream ins, boolean closeIns) throws IOException ;

    /**
     * Saves byte array data in disk cache.
     *
     * @param fileName file name
     * @param data     byte array data
     * @return <b>true</b> - if byte array data was saved successfully;
     *         <b>false</b> - if byte array data wasn't saved in disk cache.
     * @throws IOException
     */
    boolean save(String fileName, byte[] data) throws IOException ;

    /**
     * Saves data in disk cache.
     *
     * @param fileName file name
     * @param data     string data
     * @return <b>true</b> - if string data was saved successfully;
     *         <b>false</b> - if string data wasn't saved in disk cache.
     * @throws IOException
     */
    boolean save(String fileName, String data, String charsetName) throws IOException ;

    /**
     * Saves reader in disk cache.
     *
     * @param fileName file name
     * @param ins      reader
     * @return <b>true</b> - if reader's data was saved successfully;
     *         <b>false</b> - if reader's data wasn't saved in disk cache.
     * @throws IOException
     */
    boolean save(String fileName, Reader ins, String charsetName, boolean closeIns) throws IOException ;

    /**
     * Saves bitmap in disk cache.
     *
     * @param fileName file name
     * @param bm       bitmap data
     * @return <b>true</b> - if bitmap data was saved successfully;
     *         <b>false</b> - if bitmap data wasn't saved in disk cache.
     * @throws IOException
     */
    boolean save(String fileName, Bitmap bm) throws IOException ;

}
