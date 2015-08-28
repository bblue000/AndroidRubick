package androidrubick.xframework.impl.json;

import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.spi.JsonParserProvider;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public class JsonParserImpl implements JsonParserProvider {

    @Override
    public String toJsonString(Object target) {
        if (Objects.isNull(target)) {
            return Strings.NULL;
        }
        if (target instanceof CharSequence) {
            return Objects.getAs(target, CharSequence.class).toString();
        }
        return null;
    }

    @Override
    public <T> T toObject(String json, Class<T> clz) {
        if (Objects.isNull(json)) {
            return null;
        }
        return null;
    }

}
