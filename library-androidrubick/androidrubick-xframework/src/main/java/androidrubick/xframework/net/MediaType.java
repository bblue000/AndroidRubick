package androidrubick.xframework.net;

import java.util.Map;

import androidrubick.utils.Objects;

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

    public static final MediaType JSON = new MediaType(APPLICATION_TYPE, "json", null);


    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;
    private MediaType(String type, String subtype, Map<String, String> parameters) {
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

}
