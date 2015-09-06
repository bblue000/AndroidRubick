package androidrubick.xbase.util;

import android.os.*;
import android.os.Process;

import androidrubick.utils.Objects;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class LazyHandler {

    private static HandlerThread sExecutor;
    private static Handler sHandler;
    public static void post(Runnable command) {
        checkInit(command);
    }

    private static void checkInit(final Runnable command) {
        if (!Objects.isNull(sHandler) && sHandler.post(command)) {
            return;
        }
        // 说明没有初始化，或者Looper失效
        sExecutor = null;
        sHandler = null;

        sExecutor = new HandlerThread("LazyExecutor", Process.THREAD_PRIORITY_BACKGROUND) {
            @Override
            protected void onLooperPrepared() {
                sHandler = new Handler(getLooper());
                checkInit(command);
            }
        };
        sExecutor.start();
    }
}
