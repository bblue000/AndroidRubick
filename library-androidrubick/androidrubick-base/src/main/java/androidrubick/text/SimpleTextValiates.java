package androidrubick.text;

import java.util.regex.Pattern;

/**
 * 简单的字符串验证类
 *
 * <p/>
 *
 * Created by yong01.yin on 2014/12/29.
 */
public class SimpleTextValiates {

    private SimpleTextValiates() {}

    public static final String PATTERN_CELLPHONE = "^[1]\\d{10}$";
    public static final String PATTERN_MAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String PATTERN_NUMBER = "^(-)?\\d+([.]\\d+)?$";
    public static final String PATTERN_SINGLE_CHINESE = "[\\u4e00-\\u9fa5]";
    public static final String PATTERN_CHINESE = "^[\\u4e00-\\u9fa5]+$";
    public static final String PATTERN_NONE_CHINESE = "^[^\\u4e00-\\u9fa5]+$";

    public static final String PATTERN_DOMAIN = "^(?i:(http\\://))[/\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
    public static final String PATTERN_HTTP = "^(?i:(http\\://))[/\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

    /**
     * 判断字符串是否是手机号（简单版）：十一位数字，以1为开头的
     */
    public static boolean simpleValidateCellphone(CharSequence charSequence) {
        return checkEmptyThenValidate(PATTERN_CELLPHONE, charSequence);
    }

    public static boolean validateMail(CharSequence charSequence) {
        return checkEmptyThenValidate(PATTERN_MAIL, charSequence);
    }

    /**
     * 是否是纯数字（没有小数点和正负号）
     */
    public static boolean isPureNumber(CharSequence charSequence) {
        if (Strings.isEmpty(charSequence)) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isDigit(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPureLetter(CharSequence charSequence) {
        if (Strings.isEmpty(charSequence)) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isLetter(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLetterOrNumber(CharSequence charSequence) {
        if (Strings.isEmpty(charSequence)) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isLetterOrDigit(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是数字（实数）
     */
    public static boolean isNumber(CharSequence charSequence) {
        return checkEmptyThenValidate(PATTERN_NUMBER, charSequence);
    }

    public static boolean simpleValidateUsername(CharSequence charSequence, int minLen, int maxlen) {
        return checkEmptyThenValidate("^[a-z][a-z0-9_]{" + Math.max(0, minLen - 1) + "," + Math.max(0, maxlen - 1) + "}$", charSequence);
    }

    public static boolean isChinese(CharSequence charSequence) {
        return checkEmptyThenValidate(PATTERN_CHINESE, charSequence);
    }

    public static boolean hasChinese(CharSequence charSequence) {
        return !checkEmptyThenValidate(PATTERN_NONE_CHINESE, charSequence);
    }

    private static final boolean checkEmptyThenValidate(String regex, CharSequence input) {
        if (Strings.isEmpty(input)) {
            return false;
        }
        return Pattern.matches(regex, input);
    }
}
