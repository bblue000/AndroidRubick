package androidrubicktest.base;

import androidrubick.io.FileUtils;
import androidrubick.xbase.util.AppInfos;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public class AppInfoTest {

    private AppInfoTest() { /* no instance needed */ }

    public static void main(String args[]) {
        System.out.println(FileUtils.calFileSizeString(AppInfos.getMemoryClass()));
        System.out.println(FileUtils.calFileSizeString(AppInfos.memoryByRatio(0.3f)));
    }

}
