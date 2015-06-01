package androidrubick.xframework.task;

import android.os.AsyncTask;

/**
 * 具有优先级的{@link AsyncTask AsyncTask}
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/1.
 *
 * @since 1.0
 */
public abstract class XTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /**
     * {@inheritDoc}
     *
     * <p>
     *     我们做了一些新的处理
     * </p>
     *
     * @param params
     * @return
     */
    @Override
    protected final Result doInBackground(Params... params) {
        return null;
    }
}
