package androidrubick.text;

/**
 * 一些常用的正则
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/25 0025.
 *
 * @since 1.0
 */
public class Patterns {

    private Patterns() { /* no instance needed */ }

    /***************************[Web url]*****************************/
    /**
     * Web url ip 相关的正则
     *
     * @since 1.0
     */
    public static interface URLPatterns {

        /**
         * regex for protocols
         *
         * @since 1.0
         */
        public static final String PROTOCOLS = "(?:(?i:http|https|rtsp|[a-z]+)\\:\\/\\/)";

        /**
         * regular expressions for IP address
         *
         * @since 1.0
         */
        public static final String REGEX_IP_ADDRESS =
                "(?:"
                        + "(?:25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])"
                        + "\\."
                        + "(?:25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])"
                        + "\\."
                        + "(?:25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])"
                        + "\\."
                        + "(?:25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])"
                + ")";

        /**
         * Regular expression to match all domain names, like www, google, com, etc...
         *
         * @since 1.0
         */
        public static final String GOOD_URL_HOST_WORDS = "[a-zA-Z0-9][a-zA-Z0-9_\\-]*";

        /**
         *  Regular expression to match all IANA top-level domains for WEB_URL.
         *  List accurate as of 2011/07/18.  List taken from:
         *  http://data.iana.org/TLD/tlds-alpha-by-domain.txt
         *  This pattern is auto-generated by frameworks/ex/common/tools/make-iana-tld-pattern.py
         *
         * @since 1.0
         */
        public static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL =
                "(?:"
                        + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
                        + "|(?:biz|b[abdefghijmnorstvwyz])"
                        + "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
                        + "|d[ejkmoz]"
                        + "|(?:edu|e[cegrstu])"
                        + "|f[ijkmor]"
                        + "|(?:gov|g[abdefghilmnpqrstuwy])"
                        + "|h[kmnrtu]"
                        + "|(?:info|int|i[delmnoqrst])"
                        + "|(?:jobs|j[emop])"
                        + "|k[eghimnprwyz]"
                        + "|l[abcikrstuvy]"
                        + "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
                        + "|(?:name|net|n[acefgilopruz])"
                        + "|(?:org|om)"
                        + "|(?:pro|p[aefghklmnrstwy])"
                        + "|qa"
                        + "|r[eosuw]"
                        + "|s[abcdeghijklmnortuvyz]"
                        + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
                        + "|u[agksyz]"
                        + "|v[aceginu]"
                        + "|w[fs]"
                        + "|y[et]"
                        + "|z[amw]"
                + ")";

        /**
         * regex for ports (include <code>:</code>)
         *
         * @since 1.0
         */
        public static final String PORTS = "(?:\\:\\d{1,5})";

        /**
         * Regular expression to match all domains (without port),
         *
         * like www.google.com, 192.168.0.1, etc...
         *
         * @since 1.0
         */
        public static final String GOOD_DOMAIN =
                "(?:"
                        + GOOD_URL_HOST_WORDS
                        + "(?:" + "\\." + GOOD_URL_HOST_WORDS + ")*"
                        + "(?:" + "\\." + TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL + ")+"
                + ")";

        /**
         * Regular expression to match all hosts (maybe with port),
         *
         * like www.google.com, www.google.com:80, 192.168.0.1, 192.168.0.1:8080, etc...
         *
         * @since 1.0
         */
        public static final String GOOD_HOST =
                "(?:"
                        + "(?:"
                                + GOOD_DOMAIN
                                + "|"
                                + REGEX_IP_ADDRESS
                        + ")"
                        + PORTS + "?"
                + ")";

        /**
         * Regular expression to match all domains with port,
         *
         * like http://www.google.com, www.google.com:80, http://192.168.0.1, 192.168.0.1:8080, etc...
         *
         * @since 1.0
         */
        public static final String GOOD_HOST_WITH_PROTOCOL =
                "(?:"
                        + PROTOCOLS
                        + GOOD_HOST
                + ")";

        /**
         * Good characters for Internationalized Resource Identifiers (IRI).
         * This comprises most common used Unicode characters allowed in IRI
         * as detailed in RFC 3987.
         * Specifically, those two byte Unicode characters are not included.
         *
         * @since 1.0
         */
        public static final String GOOD_IRI_CHAR =
                "[a-zA-Z0-9\u00A0-\u4DFF\u9FA6-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]";

        /**
         * Regular expression to match url path
         *
         * @since 1.0
         */
        public static final String GOOD_PATH =
                "(?:"
                        + "(?:"
                                + "\\/"
                                + GOOD_IRI_CHAR + "*"
                        + ")*"
                + ")";

        static final String GOOD_URL_CHAR =
                "[a-zA-Z0-9\\\\\\#\\$\\-\\_\\.\\+\\*\\?\\!\\'\"\\%\\(\\)\\,\\;\\&\\=\\/\\|~]";

        /**
         * plus option query names
         *
         * @since 1.0
         */
        public static final String GOOD_OPTION_QUERY_NAME =
                "(?:(?:[a-zA-Z0-9][a-zA-Z0-9_\\-]|\\%[a-fA-F0-9]{2})+)";
        /**
         * plus option query values
         *
         * @since 1.0
         */
        public static final String GOOD_OPTION_QUERY_VALUE =
                "(?:(?:" + GOOD_URL_CHAR + "|\\%[a-fA-F0-9]{2})*)";
        /**
         * plus option query values
         *
         * @since 1.0
         */
        public static final String GOOD_OPTION_QUERY =
                "(?:\\?(?:" + GOOD_OPTION_QUERY_NAME + "\\=" + GOOD_OPTION_QUERY_NAME + ")*)";

        public static final String URL =
                "(?:"
                        + PROTOCOLS + "?"
                        + GOOD_HOST
                        + GOOD_PATH
                        + "(?:" + GOOD_URL_CHAR + "+)?"
                + ")";

        public static final String URL_WITH_PROTOCOL =
                "(?:"
                        /*schema://ip:port/path?query#fragment*/
                        + GOOD_HOST_WITH_PROTOCOL
                        + GOOD_PATH
                        + "(?:" + GOOD_URL_CHAR + "+)?"
                + ")";

        public static final String URL_WITHOUT_PROTOCOL =
                "(?:"
                        /*ip:port/path?query#fragment*/
                        + GOOD_HOST
                        + GOOD_PATH
                        + "(?:" + GOOD_URL_CHAR + "+)?"
                + ")";

    }





    /***************************[Email]*****************************/
    /**
     * 邮箱相关的正则
     *
     * @since 1.0
     */
    public static interface EmailPatterns {
        //邮箱匹配规则
        public static String EMAIL =
		        /* Android源码中 */
                "(?:"
                        + "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
                        + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"
                        + "(?:\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+";

    }





    /************************[Phone]******************************/
    /**
     * 电话号码相关的正则
     *
     * @since 1.0
     */
    public static interface PhonePatterns {
        /**
         * 最简单的电话号码匹配格式，但是会匹配任意数字，这是需要去避免的
         *
         * @since 1.0
         */
        public static final String PHONE =
                // sdd = space, dot, or dash
                "(?:"
                        + "(?:\\+[0-9]+[\\- ]*)?"            // +<digits><sdd>*
                        + "(?:\\([0-9]+\\)[\\- ]*)?"         // (<digits>)<sdd>*
                        + "(?:[0-9][0-9\\- ][0-9\\- ]+[0-9])"// <digit><digit|sdd>+<digit>
                + ")";

        /**
         * 最简单的手机号码匹配格式，但是会匹配任意11位数字，这是需要去避免的
         *
         * @since 1.0
         */
        public static final String CELL_PHONE =
                "(?:"
                        + "(?:\\+[0-9]+[\\- ]*)?"           // +<digits><sdd>*
                        + "1[0-9]{10}"                      // (<digits>)<sdd>*
                + ")";

    }




    /************************[Phone]******************************/
    /**
     * 语言相关的正则
     *
     * @since 1.0
     */
    public static interface LanguagePatterns {
        /**
         * 单个中文字符
         *
         * @since 1.0
         */
        public static final String CHINESE_CHAR = "[\u4e00-\u9fa5]";
        /**
         * 单个非中文字符
         *
         * @since 1.0
         */
        public static final String NON_CHINESE_CHAR = "[^\u4e00-\u9fa5]";
    }
}