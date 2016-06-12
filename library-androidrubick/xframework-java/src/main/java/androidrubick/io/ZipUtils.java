package androidrubick.io;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * zip压缩/解压操作
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/25 0025.
 *
 * @since 1.0
 */
public class ZipUtils {

    private ZipUtils() { /* no instance needed */ }

    /**
     * 解压文件
     *
     * @param srcFile 原zip文件
     * @param destDir 目标文件夹
     * @param coverIfExists 如果拷贝过程中目标文件/文件夹已经存在，是否需要替换掉
     * @param useBuf 使用提供的字节数组进行中间传输变量，
     *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
     * @return 如果成功解压文件，返回true（不论原文件有没有删除成功）
     *
     * @since 1.0
     */
    public static boolean unzip(File srcFile, boolean deleteSrc,
                                File destDir, boolean coverIfExists,
                                byte[] useBuf) throws IOException {
        // 如果目标目录创建不成功，下面的事根本就不能做了
        if (!FileUtils.checkAndEnsureDestDir(destDir, coverIfExists)) {
            return false;
        }
        if (!FileUtils.exists(srcFile)) {
            return false;
        }
        ZipFile zipFile = new ZipFile(srcFile);
        try {
            boolean ret = true;
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (null != entries && entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.isDirectory()) {
                    String zipEntryDirName = zipEntry.getName();
                    File zipEntryDir = new File(destDir, zipEntryDirName);
                    ret &= FileUtils.checkAndEnsureDestDir(zipEntryDir, coverIfExists);
                } else {
                    String zipEntryFileName = zipEntry.getName();
                    File zipEntryFile = new File(destDir, zipEntryFileName);
                    if (FileUtils.checkAndEnsureDestFile(zipEntryFile, coverIfExists)) {
                        ret &= FileUtils.saveToFile(zipFile.getInputStream(zipEntry), true,
                                zipEntryFile, false, useBuf, null);
                    } else {
                        ret &= false;
                    }
                }
            }
            if (deleteSrc) {
                FileUtils.deleteFile(srcFile, true, null);
            }
            return ret;
        } finally {
            IOUtils.close(zipFile);
        }
    }

//    /**
//     * 解压文件
//     *
//     * @param ins 读入流
//     * @param closeIns 是否关闭参数 <code>InputStream ins</code> （无论成功与否）
//     * @param destDir 目标文件夹
//     * @param coverIfExists 如果拷贝过程中目标文件/文件夹已经存在，是否需要替换掉
//     * @return 如果成功解压文件，返回true；如果<code>zipFile</code>，
//     * 或者<code>dest</code>为文件时，返回false
//     */
//    public static boolean unzip(InputStream ins, boolean closeIns,
//                                File destDir, boolean coverIfExists,
//                                byte[] useBuf) throws IOException {
//        if (Objects.isNull(ins)) {
//            return false;
//        }
//        // 如果目标目录创建不成功，下面的事根本就不能做了
//        if (!FileUtils.checkAndEnsureDestDir(destDir, coverIfExists)) {
//            return false;
//        }
//        try {
//            boolean ret = true;
//            return ret;
//        } finally {
//            IOUtils.close(ins);
//        }
//    }
//
//    /**
//     *
//     * @param srcFiles 原文件
//     * @param destFile
//     * @return
//     */
//    public static boolean zip(File[] srcFiles,
//                              File destFile, boolean coverIfExists,
//                              byte[] useBuf) {
//        if (ArraysCompat.isEmpty(srcFiles)) {
//
//        }
//    }
//
//    /**
//     *
//     * @param srcDir 原文件目录
//     * @param deleteSrc 是否删除原文件
//     * @param destFile 目标文件
//     * @param coverIfExists 如果拷贝过程中目标文件已经存在，是否需要替换掉
//     * @return 如果压缩成功，返回true
//     */
//    public static boolean zip(File srcDir, boolean deleteSrc, boolean
//                              File destFile, boolean coverIfExists,
//                              byte[] useBuf) throws IOException {
//        if (!FileUtils.exists(srcDir)) {
//            return false;
//        }
//        // 如果目标目录创建不成功，下面的事根本就不能做了
//        if (!FileUtils.checkAndEnsureDestFile(destFile, coverIfExists)) {
//            return false;
//        }
//        FileOutputStream out = FileUtils.openFileOutput(destFile, true, false);
//        ZipOutputStream zipOutputStream = new ZipOutputStream(out);
//        if (deleteSrc) {
//            FileUtils.deleteFile(srcDir, true, null);
//        }
//        return
//    }

}