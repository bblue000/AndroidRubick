package androidrubick.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import androidrubick.utils.ArraysCompat;
import androidrubick.utils.NumberUtils;
import static androidrubick.io.IOUtils.*;

/**
 * 工具类，封装简单的文件相关的操作
 *
 * @author Yin Yong
 *
 * @since 1.0
 *
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

	private FileUtils() { /* no instance */ }

	/**
	 * 简单地生成文件大小的字符串，如100.00KB，10.20M, e.g.
     *
     * @see NumberUtils#formatByUnit(double, double, double, int, String...)
     * @see NumberUtils#formatByUnit(double, double, int, String...)
     * @since 1.0
	 */
	public static String calFileSizeString(double bytes) {
		if (0D >= bytes) {
			bytes = 0D;
		}
		return NumberUtils.formatByUnit(bytes, 1024D, 900D, 2, "B", "KB", "M", "G", "T");
	}

    /**
     * 文件是否存在
     *
     * @throws NullPointerException <code>file</code>为空抛出异常
     *
     * @since 1.0
     */
    public static boolean exists(File file) {
        return file.exists();
    }

    /**
     * 创建指定文件（非文件夹，文件夹的创建）
     *
     * @return 如果文件已存在或者被创建成功，返回TRUE，否则返回false。
     *
     * @throws NullPointerException <code>file</code>为空抛出异常
     *
     * @since 1.0
     */
    public static boolean createFile(File file) {
        if (exists(file)) {
            return file.isFile();
        } else {
            File parentFile = file.getParentFile();
            if (null != parentFile) {
                createDir(parentFile);
            }
            try {
                file.createNewFile();
            } catch (Exception e) { }
            return exists(file);
        }
    }

    /**
     * 创建指定路径所有的未创建的文件夹
     *
     * @return 如果文件夹已存在或者被创建成功，返回TRUE，否则返回false。
     *
     * @throws NullPointerException <code>file</code>为空抛出异常
     *
     * @since 1.0
     */
    public static boolean createDir(File dir) {
        if (exists(dir)) {
            return dir.isDirectory();
        }
        dir.mkdirs();
        return exists(dir);
    }

    /**
     * 删除指定的文件（操作同步进行，如果文件、文件夹较大，耗时也会相应的增加）
     *
     * @param file 指定的文件对象
     * @param deleteRoot 如果该文件是文件夹，是否删除该文件夹
     * @param callback 文件操作的回调
     *
     * @return <b><i>如果最终指定的文件不再存在</i></b>，则返回TRUE
     *
     * @since 1.0
     */
    public static boolean deleteFile(File file, boolean deleteRoot,
                                     final FileProgressCallback callback) {
        // 如果文件不存在，也算是删除了
        if (!exists(file)) {
            if (null != callback) {
                callback.onProgress(file, true);
                callback.onComplete();
            }
            return true;
        }
        return deleteFileInner(file, deleteRoot, callback, true);
    }

    private static boolean deleteFileInner(File file, boolean deleteRoot,
                                           final FileProgressCallback fileProgressCallback,
                                           boolean runComplete) {
        boolean ret;
        if (file.isDirectory()) {
            boolean flag = true;
            File[] childFiles = file.listFiles();
            if (!ArraysCompat.isEmpty(childFiles)) {
                for (File cFile : childFiles) {
                    flag &= deleteFileInner(cFile, true, fileProgressCallback, false);
                }
            }
            if (deleteRoot) {
                boolean delRootRet = file.delete();
                flag &= delRootRet;
                if (null != fileProgressCallback)
                    fileProgressCallback.onProgress(file, delRootRet);
            }
            ret = flag;
        } else {
            ret = file.delete();
            if (null != fileProgressCallback)
                fileProgressCallback.onProgress(file, ret);
        }
        if (runComplete) {
            if (null != fileProgressCallback)
                fileProgressCallback.onComplete();
        }
        return ret;
    }


    // I/O 操作
    /**
     *
     * 读取文件
     *
     * @param file 指定文件
     * @return file指定的FileInputStream
     *
     * @throws IOException 如果找不到文件，将抛出该异常/访问权限、异常等，
     * 或者如果对应地址为文件夹，将抛出该异常
     *
     * @since 1.0
     */
    public static FileInputStream openFileInput(File file) throws IOException {
        if (exists(file)) {
            if (file.isFile()) {
                return new FileInputStream(file);
            } else {
                throw new IOException("path = { "
                        + file.getAbsolutePath() + " } is not a regular file!");
            }
        } else {
            throw new FileNotFoundException("path = { " + file.getAbsolutePath()
                    + " } is not found!");
        }
    }

    /**
     * @param file 指定文件
     * @param createIfUnExists 如果文件不存在，是否创建
     * @param append If append is true and the file already exists,
     * it will be appended to; otherwise it will be truncated
     *
     * @return path指定的FileOutputStream
     *
     * @throws IOException 文件不存在且createIfUnExists == false，
     * 或file指定的路径非文件，或其他异常
     *
     * @since 1.0
     */
    public static FileOutputStream openFileOutput(File file,
                                                  boolean createIfUnExists,
                                                  boolean append) throws IOException {
        if (exists(file)) {
            if (file.isFile()) {
                return new FileOutputStream(file, append);
            }
            throw new IOException("path = { "
                    + file.getAbsolutePath() + " } is not a regular file!");
        }
        if (!createIfUnExists) {
            throw new FileNotFoundException("path = { " + file.getAbsolutePath()
                    + " } is not found!");
        }

        if (createFile(file)) {
            return new FileOutputStream(file, append);
        }
        throw new IOException("path = { " + file.getAbsolutePath() + " } "
                + "cannot be created!");
    }

    /**
     *
     * @param file 目标文件
     * @param ins 读入流
     * @param append 内容写入时是否使用叠加模式
     * @param closeIns 是否关闭参数 <code>InputStream ins</code> （无论成功与否）
     * @return 如果成功写入，返回TRUE，否则返回false
     *
     * @see {@link #openFileOutput(File, boolean, boolean)}
     *
     * @since 1.0
     */
    public static boolean saveToFile(InputStream ins, boolean closeIns,
                                     File file, boolean append,
                                     byte[] useBuf, IOProgressCallback callback) throws IOException {
        FileOutputStream out = openFileOutput(file, true, append);
        return IOUtils.writeTo(ins, out, closeIns, true, useBuf, callback);
    }

    /**
     * 计算指定文件/文件夹的大小
     *
     * @since 1.0
     */
    public static long caculateFileSize(File file) {
        if (null == file || !file.exists()) {
            return 0L;
        }
        if (file.isDirectory()) {
            long size = 0L;
            File[] childFile = file.listFiles();
            if (null != childFile && childFile.length > 0) {
                for (File cFile : childFile) {
                    size += caculateFileSize(cFile);
                }
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * copy a file from srcFile to destFile, return true if succeed, return
     * false on failure（该方法同步进行，如果文件越大，耗时会增加）
     *
     * @param srcFile 原文件
     * @param destFile 目标文件或者目录
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     *
     * @since 1.0
     */
    public static boolean copyToFile(File srcFile, File destFile,
                                     boolean coverIfExists,
                                     byte[] useBuf, IOProgressCallback callback) throws IOException {
        // 如果文件不存在，相当于成功咯
        if (!exists(srcFile)) {
            return true;
        }
        // 如果目标文件已经存在，但是不能覆盖，直接返回
        if (exists(destFile) && !coverIfExists) {
            return false;
        }
        // 如果目标文件已存在或者创建成功，才能进行下一步
        if (!createFile(destFile)) {
            return false;
        }
        InputStream in = openFileInput(srcFile);
        return saveToFile(in, true, destFile, false, useBuf, callback);
    }

    /**
     * copy a file from srcFile to destFile, return true if succeed, return
     * false on failure
     *
     * @param srcFile 原文件
     * @param destDir 目标目录
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     *
     * @since 1.0
     */
    public static boolean copyToDir(File srcFile, File destDir,
                                    boolean coverIfExists,
                                    byte[] useBuf, IOProgressCallback callback) throws IOException {
        // 如果文件夹创建不了，直接返回
        if (!createDir(destDir)) {
            return false;
        }
        return copyToFile(srcFile, new File(destDir, srcFile.getName()), coverIfExists, useBuf, callback);
    }

    /**
     * Copy data from a source stream to destFile, and then delete the source file.
     * Return true if succeed, return false if failed.
     *
     * @param srcFile 原文件
     * @param destFile 目标文件
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     *
     * @since 1.0
     */
    public static boolean cutToFile(File srcFile, File destFile,
                                    boolean coverIfExists,
                                    byte[] useBuf, IOProgressCallback callback) throws IOException {
        return copyToFile(srcFile, destFile, coverIfExists, useBuf, callback) && deleteFile(srcFile, true, null);
    }

    /**
     * Copy data from a source stream to destFile, and then delete the source file.
     * Return true if succeed, return false if failed.
     *
     * @param srcFile 原文件
     * @param destDir 目标目录
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     *
     * @since 1.0
     */
    public static boolean cutToDir(File srcFile, File destDir,
                                   boolean coverIfExists,
                                   byte[] useBuf, IOProgressCallback callback) throws IOException {
        return copyToDir(srcFile, destDir, coverIfExists, useBuf, callback) && deleteFile(srcFile, true, null);
    }

    /**
     * 拷贝文件夹（拷贝操作是同步进行的，所以耗时跟文件量有关，不推荐在主进程调用）
     *
     * @param srcDir 原文件目录
     * @param destDir 目标目录
     * @param willCut 是否是剪切
     * @param coverIfExists 如果拷贝过程中目标文件/文件夹已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     *
     * @since 1.0
     */
    public static boolean copyDir(File srcDir, boolean willCut,
                                  File destDir, boolean coverIfExists,
                                  byte[] useBuf, FileProgressCallback callback) throws IOException {
        // 原文件夹为空，也算是拷贝成功
        if (!exists(srcDir)) {
            if (null != callback) {
                callback.onProgress(srcDir, true);
                callback.onComplete();
            }
            return true;
        }
        return copyDirInner(srcDir, willCut, destDir, coverIfExists, useBuf, callback, true);
    }

    private static boolean copyDirInner(File srcDir, boolean willCut,
                                        File destDir, boolean coverIfExists,
                                        byte[] useBuf, FileProgressCallback callback,
                                        boolean runComplete) throws IOException {
        // 如果目标目录创建不成功，下面的事根本就不能做了
        if (!createDir(destDir)) {
            return false;
        }
        boolean ret;
        if (srcDir.isDirectory()) {
            boolean flag = true;
            File[] childFiles = srcDir.listFiles();
            if (!ArraysCompat.isEmpty(childFiles)) {
                for (File cFile : childFiles) {
                    File targetDir = destDir;
                    if (cFile.isDirectory()) {
                        targetDir = new File(destDir, cFile.getName());
                    }
                    flag &= copyDirInner(cFile, willCut, targetDir, coverIfExists, useBuf, callback, false);
                }
            }
            if (willCut) {
                boolean delRootRet = file.delete();
                flag &= delRootRet;
                if (null != callback)
                    callback.onProgress(srcDir, delRootRet);
            }
            ret = flag;
            File[] files = srcDir.listFiles();
            // 如果是空目录，则直接返回
            if (!ArraysCompat.isEmpty(files)) {
                if (willCut) {

                }
                return true;
            }
            if (willCut) {
                // delete root dir
                boolean delRootRet = deleteFile(srcDir, true, null);
                r
            }
            for (File file : files) {
                if (file.isDirectory()) {

                } else {

                }
            }
            if (!exists(destDir) || !destDir.isDirectory()) {
                return false;
            }
            if (willCut) {
                if (null != callback)
                    callback.onProgress(srcFile, ret);
            }
        } else {
            // 如果不是文件目录，就是拷贝文件咯
            File srcFile = srcDir;
            ret = willCut ? cutToDir(srcFile, destDir, coverIfExists, useBuf, null) :
                    copyToDir(srcFile, destDir, coverIfExists, useBuf, null);
            if (null != callback)
                callback.onProgress(srcFile, ret);
        }
        if (runComplete) {
            if (null != callback) {
                callback.onComplete();
            }
        }
        return ret;
    }

    /**
     * Read a text file into a String, optionally limiting the length.
     * @param file to read (will not seek, so things like /proc files are OK)
     * @param max length (positive for head, negative of tail, 0 for no limit)
     * @param ellipsis to add of the file was truncated (can be null)
     * @return the contents of the file, possibly truncated
     * @throws IOException if something goes wrong reading the file
     *
     * @since 1.0
     */
    public static String readTextFile(File file, int max, String ellipsis) throws IOException {
        try {
            InputStream input = new FileInputStream(file);
            try {
                long size = file.length();
                if (max > 0 || (size > 0 && max == 0)) {  // "head" mode: read the first N bytes
                    if (size > 0 && (max == 0 || size < max)) max = (int) size;
                    byte[] data = new byte[max + 1];
                    int length = input.read(data);
                    if (length <= 0) return "";
                    if (length <= max) return new String(data, 0, length);
                    if (ellipsis == null) return new String(data, 0, max);
                    return new String(data, 0, max) + ellipsis;
                } else if (max < 0) {  // "tail" mode: keep the last N
                    int len;
                    boolean rolled = false;
                    byte[] last = null, data = null;
                    do {
                        if (last != null) rolled = true;
                        byte[] tmp = last; last = data; data = tmp;
                        if (data == null) data = new byte[-max];
                        len = input.read(data);
                    } while (len == data.length);

                    if (last == null && len <= 0) return "";
                    if (last == null) return new String(data, 0, len);
                    if (len > 0) {
                        rolled = true;
                        System.arraycopy(last, len, last, 0, last.length - len);
                        System.arraycopy(data, 0, last, last.length - len, len);
                    }
                    if (ellipsis == null || !rolled) return new String(last);
                    return ellipsis + new String(last);
                } else {  // "cat" mode: size unknown, read it all in streaming fashion
                    ByteArrayOutputStream contents = new ByteArrayOutputStream();
                    int len;
                    byte[] data = new byte[1024];
                    do {
                        len = input.read(data);
                        if (len > 0) contents.write(data, 0, len);
                    } while (len == data.length);
                    return contents.toString();
                }
            } finally {
                close(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     * @param file target file
     * @param string char sequence
     * @return return true if succeed, return false if fail
     *
     * @since 1.0
     */
    public static boolean stringToFile(File file, String string) {
        return stringToFile(file, string, false);
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     * @param file target file
     * @param string char sequence
     * @param append whether to append to file
     * @return return true if succeed, return false if fail
     *
     * @since 1.0
     */
    public static boolean stringToFile(File file, String string, boolean append) {
        try {
            FileWriter out = new FileWriter(file, append);
            try {
                out.write(string);
            } finally {
                close(out);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
