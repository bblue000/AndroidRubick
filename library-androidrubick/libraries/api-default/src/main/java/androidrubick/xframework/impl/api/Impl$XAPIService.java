package androidrubick.xframework.impl.api;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.api.spi.XAPIService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class Impl$XAPIService implements XAPIService {

    public Impl$XAPIService() {

    }

    @Override
    public <Result> XAPIHolder doAPI(String url, HttpMethod method,
                                     Object param, Map<String, String> extraHeaders,
                                     Class<Result> result, XAPICallback<Result> callback) {
        XAPIHolder holder = new $APIHolderImpl(url, method, param, extraHeaders, result, callback);
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
