package androidrubick.xframework.impl.json;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.spi.XJsonParserService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public class Impl$XJsonParserService implements XJsonParserService {

    private Gson mGson;
    public Impl$XJsonParserService() {
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
            return String.valueOf(target);
        }
        if (Objects.isNull(type)) {
            return mGson.toJson(target);
        }
        return mGson.toJson(target, type);
    }

    @Override
    public <T> T toObject(String json, Type clz) {
        if (Objects.isNull(json) || Strings.isEmpty(json, true)) {
            return null;
        }
        return mGson.fromJson(json, clz);
    }

    @Override
    public <T> T toObject(Reader reader, Type clz) {
        if (Objects.isNull(reader)) {
            return null;
        }
        return mGson.fromJson(reader, clz);
    }

    @Override
    public void trimMemory() {
        // nothing to trim
    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
