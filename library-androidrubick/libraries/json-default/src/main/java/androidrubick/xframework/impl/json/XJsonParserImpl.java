package androidrubick.xframework.impl.json;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.spi.XJsonParserService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public class XJsonParserImpl implements XJsonParserService {

    private Gson mGson;
    public XJsonParserImpl() {
        mGson = new Gson();
    }

    @Override
    public String toJsonString(Object target) {
        if (Objects.isNull(target)) {
            return Strings.NULL;
        }
        return toJsonString(target, target.getClass());
    }

    public String toJsonString(Object target, Type type) {
        if (Objects.isNull(target)) {
            return Strings.NULL;
        }
        if (target instanceof CharSequence) {
            return Objects.getAs(target, CharSequence.class).toString();
        }
        if (Objects.isNull(type)) {
            return mGson.toJson(target);
        }
        return mGson.toJson(target, type);
    }

    @Override
    public <T> T toObject(String json, Class<T> clz) {
        if (Objects.isNull(json) || Strings.NULL.equals(json)) {
            return null;
        }
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return mGson.fromJson(json, clz);
    }

    @Override
    public void trimMemory() {
        // nothing to trim
    }
}
