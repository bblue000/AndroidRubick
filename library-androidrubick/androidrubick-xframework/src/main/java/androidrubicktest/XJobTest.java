package androidrubicktest;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;

import androidrubick.xframework.job.XJob;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/8.
 *
 * @since 1.0
 */
public class XJobTest {

    public static void main(String args[]) {
    }

    public static void testExecutor() {
        Executor executor = XJob.getDefaultExecutor();

        Log.d("yytest", "Runtime.getRuntime().availableProcessors() = "
                + Runtime.getRuntime().availableProcessors());


        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.d("yytest", "run");
                try {
                    Thread.sleep(60 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
        executor.execute(run);
    }

    public static void testXJob() {
        final XJob job = new XJob() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }
        };
        BlockingDeque deque;
        job.execute();

    }

    class A {
        <D extends A>D get() {
            return null;
        }
    }

    class Aa extends A {
        Aa get() {
            return null;
        }
    }

}
