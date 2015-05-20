package androidrubick.xframework.net;

import java.util.Map;

import androidrubick.text.Prints;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;

/**
 * 媒体类型
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public class MediaType {

    private static final String CHARSET_ATTRIBUTE = "charset";

    // TODO(gak): make these public?
    public static final String APPLICATION_TYPE = "application";
    public static final String AUDIO_TYPE = "audio";
    public static final String IMAGE_TYPE = "image";
    public static final String TEXT_TYPE = "text";
    public static final String VIDEO_TYPE = "video";

    public static final String WILDCARD = "*";

    /**
     * 通配类型。
     */
    public static final MediaType ANY = new MediaType(WILDCARD, WILDCARD, null);

    /**
     * application/json
     */
    public static final MediaType JSON = new MediaType(APPLICATION_TYPE, "json", null);

    public static MediaType of() {
        return ANY;
    }

    public static MediaType of(String type) {
        return of(type, WILDCARD);
    }

    public static MediaType of(String type, String subtype) {
        return of(type, subtype, (Map) null);
    }

    public static MediaType of(String type, String subtype, Map<String, String> parameters) {
        return new MediaType(type, subtype, parameters);
    }

    public static MediaType of(String type, String subtype, String charset) {
        return new MediaType(type, subtype, MapBuilder.newHashMap().put(CHARSET_ATTRIBUTE, charset).build());
    }

    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;
    private MediaType(String type, String subtype, Map<String, String> parameters) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(subtype);
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    public MediaType withParameters(Map<String, String> map) {
        if (Objects.isEmpty(map)) {
            return this;
        }
        return new MediaType(this.type, this.subtype, map);
    }

    public MediaType withParameter(Map<String, String> map) {
        if (Objects.isEmpty(map)) {
            return this;
        }
        return new MediaType(this.type, this.subtype, map);
    }

    public MediaType withoutParameters() {
        if (Objects.isEmpty(parameters)) {
            return this;
        }
        return new MediaType(this.type, this.subtype, null);
    }

    public MediaType withCharset(String charset) {
        StringBuilder builder = new StringBuilder().append(type).append('/').append(subtype);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(type.length() + subtype.length() + 1 + 32)
                .append(type).append('/').append(subtype);
        Prints.join(parameters, new Function<Map.Entry<String, String>, String>() {
            @Override
            public String apply(Map.Entry<String, String> input) {
                return input.getKey() + "=" + input.getValue();
            }
        }, "; ");
        return builder.toString();
    }
}
