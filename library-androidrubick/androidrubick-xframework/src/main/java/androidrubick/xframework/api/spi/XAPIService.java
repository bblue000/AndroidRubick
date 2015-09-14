package androidrubick.xframework.api.spi;

import androidrubick.net.HttpMethod;
import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.api.XAPICallback;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15 0015.
 *
 * @since 1.0
 */
public interface XAPIService extends XSpiService {

    public <Result>Result api(String url, HttpMethod method,
                            Class<Result> result, XAPICallback callback);

}
