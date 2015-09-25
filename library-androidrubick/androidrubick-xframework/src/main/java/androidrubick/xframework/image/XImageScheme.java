package androidrubick.xframework.image;

import java.util.Locale;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/21.
 */
public enum XImageScheme {

    /**
     * from resource drawable
     */
    Res("res"),

    /**
     * from assets package
     */
    Asset("assets"),

    /**
     * from internal or external storage
     */
    File("file"),

    /**
     * from remote use HTTP Protocol
     */
    Http("http"),

    /**
     * from remote use HTTPs Protocol
     */
    Https("https"),

    Unknown("");

    private String scheme;
    private String uriPrefix;
    private XImageScheme(String scheme) {
        this.scheme = scheme;
        this.uriPrefix = scheme + "://";
    }

    /**
     * Defines scheme of incoming URI
     *
     * @param uri URI for scheme detection
     * @return Scheme of incoming URI
     */
    public static XImageScheme ofUri(String uri) {
        if (uri != null) {
            for (XImageScheme s : values()) {
                if (s.belongsTo(uri)) {
                    return s;
                }
            }
        }
        return Unknown;
    }

    private boolean belongsTo(String uri) {
        return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
    }

    /** Appends scheme to incoming path */
    public String wrap(String path) {
        return uriPrefix + path;
    }

    /** Removed scheme part ("scheme://") from incoming URI */
    public String crop(String uri) {
        if (!belongsTo(uri)) {
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, scheme));
        }
        return uri.substring(uriPrefix.length());
    }
}
