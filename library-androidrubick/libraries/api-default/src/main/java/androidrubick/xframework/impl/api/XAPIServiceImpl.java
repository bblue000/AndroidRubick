package androidrubick.xframework.impl.api;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.api.spi.XAPIService;
import androidrubick.xframework.impl.api.internal.XAPIHolderImpl;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class XAPIServiceImpl implements XAPIService {

    @Override
    public <Result> XAPIHolder doAPI(String url, HttpMethod method,
                                     Object param, Class<Result> result,
                                     XAPICallback<Result> callback) {
        XAPIHolder holder = new XAPIHolderImpl(url, method, param, result, callback);
        try {
            return holder;
        } finally {
            holder.execute();
        }
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }

}
