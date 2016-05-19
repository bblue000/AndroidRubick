package androidrubick.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import androidrubick.utils.ArraysCompat;
import androidrubick.utils.NumberUtils;
import androidrubick.utils.Objects;

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
     * @return 如果文件已存在或者被创建成功，返回true，否则返回false。
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
            } catch (Exception e) { e.printStackTrace(); }
            return exists(file);
        }
    }

    /**
     * 创建指定路径所有的未创建的文件夹
     *
     * @return 如果文件夹已存在或者被创建成功，返回true，否则返回false。
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
     * @return <b><i>如果最终指定的文件/文件夹不再存在</i></b>，则返回true
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
     * @param ins 读入流
     * @param closeIns 是否关闭参数 <code>InputStream ins</code> （无论成功与否）
     * @param file 目标文件
     * @param append 内容写入时是否使用叠加模式
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
     * @param callback IO进度的回调，可为null
     * @return 如果成功写入，返回true，否则返回false
     *
     * @see {@link #openFileOutput(File, boolean, boolean)}
     *
     * @since 1.0
     */
    public static boolean saveToFile(InputStream ins, boolean closeIns,
                                     File file, boolean append,
                                     byte[] useBuf, IOProgressCallback callback) throws IOException {
        FileOutputStream out = openFileOutput(file, true, append);
        return IOUtils.writeTo(ins, closeIns, out, true, useBuf, callback);
    }

    /**
     *
     * @param data byte array data
     * @param file 目标文件
     * @param append 内容写入时是否使用叠加模式
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
     * @param callback IO进度的回调，可为null
     * @return 如果成功写入，返回true，否则返回false
     *
     * @see {@link #openFileOutput(File, boolean, boolean)}
     *
     * @since 1.0
     */
    public static boolean saveToFile(byte[] data,
                                     File file, boolean append,
                                     byte[] useBuf, IOProgressCallback callback) throws IOException {
        FileOutputStream out = openFileOutput(file, true, append);
        return IOUtils.writeTo(data, out, true, useBuf, callback);
    }

    /**
     *
     * @param ins 字符流
     * @param closeIns 是否关闭参数 <code>InputStream ins</code> （无论成功与否）
     * @param file 目标文件
     * @param append 内容写入时是否使用叠加模式
     * @param charsetName 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @param callback IO进度的回调，可为null
     * @return 如果成功写入，返回true，否则返回false
     *
     * @see {@link #openFileOutput(File, boolean, boolean)}
     *
     * @since 1.0
     */
    public static boolean saveToFile(Reader ins, boolean closeIns,
                                     File file, boolean append,
                                     String charsetName,
                                     char[] useBuf, IOProgressCallback callback) throws IOException {
        FileOutputStream out = openFileOutput(file, true, append);
        return IOUtils.writeTo(ins, closeIns, out, true, charsetName, useBuf, callback);
    }

    /**
     * 计算指定文件/文件夹的大小
     *
     * @since 1.0
     */
    public static long calculateFileSize(File file) {
        if (null == file || !file.exists()) {
            return 0L;
        }
        if (file.isDirectory()) {
            long size = 0L;
            File[] childFile = file.listFiles();
            if (!ArraysCompat.isEmpty(childFile)) {
                for (File cFile : childFile) {
                    size += calculateFileSize(cFile);
                }
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * 计算指定文件夹中的文件和文件夹的数量；
     *
     * <p/>
     *
     * 返回长度为3，数组第一项为文件数，第二项为文件夹数，第三项为总数目
     *
     * @param recursive 是否递归地计算子文件夹
     * @param includeHidden 是否包括隐藏文件/文件夹
     *
     * @since 1.0
     */
    public static int[] calculateFileAndDirCount(File path, boolean recursive, boolean includeHidden) {
        int[] target = new int[3];
        calculateFileAndDirCount(path, target, recursive, includeHidden);
        target[2] = target[0] + target[1];
        return target;
    }

    /**
     * 计算指定文件夹中的文件和文件夹的数量；
     *
     * <p/>
     *
     * @param target 数组第一项为文件数，第二项为文件夹数
     * @param recursive 是否递归地计算子文件夹
     * @param includeHidden 是否包括隐藏文件/文件夹
     *
     * @since 1.0
     */
    public static void calculateFileAndDirCount(File path, int target[], boolean recursive, boolean includeHidden) {
        if (path.isDirectory()) {
            File[] childFile = path.listFiles();
            if (ArraysCompat.isEmpty(childFile)) return;

            for (File cFile : childFile) {
                // 如果不需要计算隐藏文件，直接继续循环
                if (cFile.isHidden() && !includeHidden) continue;

                if (cFile.isDirectory()) {
                    if (ArraysCompat.getLength(target) > 1)
                        target[1] += 1;
                    // 如果不需要递归调用，则直接继续循环
                    if (!recursive) continue;
                }
                calculateFileAndDirCount(cFile, target, recursive, includeHidden);
            }
        } else if (path.isFile()) {
            if (ArraysCompat.getLength(target) > 0)
                target[0] += 1;
        }
    }

    /**
     * copy a file from <code>srcFile</code> to <code>destFile</code>,
     * return true if succeed, return false on failure（该方法同步进行，如果文件越大，耗时会增加）;
     *
     * @param srcFile 原文件
     * @param deleteSrc 是否删除原文件
     * @param destFile 目标文件
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
     * @param callback IO进度的回调，可为null
     *
     * @return 如果拷贝成功，返回true；否则，返回false（不管原文件有没有删除成功）
     *
     * @since 1.0
     */
    public static boolean copyToFile(File srcFile, boolean deleteSrc,
                                     File destFile, boolean coverIfExists,
                                     byte[] useBuf, IOProgressCallback callback) throws IOException {
        // 如果文件不存在，相当于成功咯
        if (!exists(srcFile)) {
            return false;
        }
        // 如果目标文件创建不成功，下面的事根本就不能做了
        if (!checkAndEnsureDestFile(destFile, coverIfExists)) {
            return false;
        }
        InputStream in = openFileInput(srcFile);
        boolean ret = saveToFile(in, true, destFile, false, useBuf, callback);
        if (deleteSrc) {
            deleteFile(srcFile, true, null);
        }
        return ret;
    }

    /**
     * copy a file from <code>srcFile</code> to <code>destFile</code>,
     * return true if succeed, return false on failure
     *
     * @param srcFile 原文件
     * @param deleteSrc 是否删除原文件
     * @param destDir 目标目录
     * @param coverIfExists 如果目标文件已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
     * @param callback IO进度的回调，可为null
     *
     * @return 如果拷贝成功，返回true；否则，返回false（不管原文件有没有删除成功）
     *
     * @since 1.0
     */
    public static boolean copyToDir(File srcFile, boolean deleteSrc,
                                    File destDir, boolean coverIfExists,
                                    byte[] useBuf, IOProgressCallback callback) throws IOException {
        // 如果目标目录创建不成功，下面的事根本就不能做了
        if (!checkAndEnsureDestDir(destDir, coverIfExists)) {
            return false;
        }
        return copyToFile(srcFile, deleteSrc,
                new File(destDir, srcFile.getName()), coverIfExists,
                useBuf, callback);
    }

    /**
     * 拷贝文件夹，将<code>srcDir</code>中的所有文件拷贝到<code>destFile</code>中
     * （拷贝操作是同步进行的，所以耗时跟文件量有关，不推荐在主进程调用）
     *
     * @param srcDir 原文件目录
     * @param deleteSrc 是否删除原文件
     * @param destDir 目标目录
     * @param coverIfExists 如果拷贝过程中目标文件/文件夹已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
     * @param callback IO进度的回调，可为null
     *
     * @return 如果拷贝成功，返回true；否则，返回false（不管原文件有没有删除成功）
     * @since 1.0
     */
    public static boolean copyDir(File srcDir, boolean deleteSrc,
                                  File destDir, boolean coverIfExists,
                                  byte[] useBuf, FileProgressCallback callback) throws IOException {
        if (!exists(srcDir)) {
            return false;
        }
        return copyDirInner(srcDir, deleteSrc, destDir, coverIfExists, useBuf, callback, true);
    }

    private static boolean copyDirInner(File srcDir, boolean deleteSrc,
                                        File destDir, boolean coverIfExists,
                                        byte[] useBuf, FileProgressCallback callback,
                                        boolean runComplete) throws IOException {
        // 如果目标目录创建不成功，下面的事根本就不能做了
        if (!checkAndEnsureDestDir(destDir, coverIfExists)) {
            return false;
        }
        boolean ret;
        if (srcDir.isDirectory()) {
            boolean flag = true;
            File[] childFiles = srcDir.listFiles();
            if (!ArraysCompat.isEmpty(childFiles)) {
                for (File cFile : childFiles) {
                    if (cFile.isDirectory()) {
                        flag &= copyDirInner(cFile, deleteSrc,
                                new File(destDir, cFile.getName()), coverIfExists,
                                useBuf, callback, false);
                    } else {
                        boolean copyToDirRet = copyToDir(cFile, deleteSrc,
                                destDir, coverIfExists, useBuf, null);
                        flag &= copyToDirRet;
                        if (null != callback)
                            callback.onProgress(cFile, copyToDirRet);
                    }
                }
            }
            if (deleteSrc) {
                deleteFileInner(srcDir, true, callback, false);
            }
            ret = flag;
        } else {
            // 如果不是文件目录，就是拷贝文件咯
            File srcFile = srcDir;
            ret = copyToDir(srcFile, deleteSrc, destDir, coverIfExists, useBuf, null);
            if (null != callback)
                callback.onProgress(srcFile, ret);
        }
        if (runComplete) {
            if (null != callback)
                callback.onComplete();
        }
        return ret;
    }

    /*package*/ static boolean checkAndEnsureDestFile(File destFile, boolean coverIfExists) {
        // 如果是文件夹，又不能替换，什么事都不能做
        if (destFile.isDirectory()) {
            if (!coverIfExists) {
                return false;
            }
            // 能替换就先删除
            deleteFile(destFile, true, null);
        }
        return true;
    }

    /*package*/ static boolean checkAndEnsureDestDir(File destDir, boolean coverIfExists) {
        // 如果是文件，又不能替换，什么事都不能做
        if (destDir.isFile()) {
            if (!coverIfExists) {
                return false;
            }
            // 能替换就先删除
            deleteFile(destDir, true, null);
        }
        // 如果目标目录创建不成功，下面的事根本就不能做了
        return createDir(destDir);
    }

    /**
     * Read a text file into a String, optionally limiting the length.
     * @param file to read (will not seek, so things like /proc files are OK)
     * @param max length (positive for head, negative of tail, 0 for no limit)
     * @param ellipsis to add of the file was truncated (can be null)
     * @param charsetName 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
     * @return the contents of the file, possibly truncated
     * @throws IOException if something goes wrong reading the file
     *
     * @since 1.0
     */
    public static String readTextFile(File file, int max, String ellipsis, String charsetName)
            throws IOException {
        try {
            charsetName = Objects.getOr(charsetName, IOConstants.DEF_CHARSET_NAME);
            InputStream input = new FileInputStream(file);
            try {
                long size = file.length();
                if (max > 0 || (size > 0 && max == 0)) {  // "head" mode: read the first N bytes
                    if (size > 0 && (max == 0 || size < max)) max = (int) size;
                    byte[] data = new byte[max + 1];
                    int length = input.read(data);
                    if (length <= 0) return "";
                    if (length <= max) return new String(data, 0, length, charsetName);
                    if (ellipsis == null) return new String(data, 0, max, charsetName);
                    return new String(data, 0, max, charsetName) + ellipsis;
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
                    if (last == null) return new String(data, 0, len, charsetName);
                    if (len > 0) {
                        rolled = true;
                        System.arraycopy(last, len, last, 0, last.length - len);
                        System.arraycopy(data, 0, last, last.length - len, len);
                    }
                    if (ellipsis == null || !rolled) return new String(last, charsetName);
                    return ellipsis + new String(last, charsetName);
                } else {  // "cat" mode: size unknown, read it all in streaming fashion
                    ByteArrayOutputStream contents = new ByteArrayOutputStream();
                    int len;
                    byte[] data = new byte[1024];
                    do {
                        len = input.read(data);
                        if (len > 0) contents.write(data, 0, len);
                    } while (len == data.length);
                    return contents.toString(charsetName);
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
     * @param charsetName 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
     * @return return true if succeed, return false if fail
     *
     * @since 1.0
     */
    public static boolean stringToFile(File file, String string, String charsetName) {
        return stringToFile(file, string, charsetName, false);
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     * @param file target file
     * @param string char sequence
     * @param charsetName 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
     * @param append whether to append to file
     * @return return true if succeed, return false if fail
     *
     * @since 1.0
     */
    public static boolean stringToFile(File file, String string, String charsetName, boolean append) {
        try {
            return saveToFile(new StringReader(string), true, file, append, charsetName, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
