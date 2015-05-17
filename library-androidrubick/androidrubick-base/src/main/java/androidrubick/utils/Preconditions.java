package androidrubick.utils;

/**
 * 工具类，用来验证状态、操作等是否符合指定的条件，不满足条件将抛出相关异常：
 *
 * <br/>
 *
 * <table style="border:#ccc 1px solid">
 *     <tr>
 *         <td style="border:#ccc 1px solid; font-weight:bold">方法</td>
 *         <td style="border:#ccc 1px solid; font-weight:bold">用途</td>
 *     </tr>
 *     <tr>
 *         <td style="border:#ccc 1px solid">checkNotNull</td>
 *         <td style="border:#ccc 1px solid">如果指定对象为null，则抛出{@link java.lang.NullPointerException}</td>
 *     </tr>
 *     <tr>
 *         <td style="border:#ccc 1px solid">checkArgument</td>
 *         <td style="border:#ccc 1px solid">如果指定条件不成立，则抛出{@link java.lang.IllegalArgumentException}</td>
 *     </tr>
 *     <tr>
 *         <td style="border:#ccc 1px solid">checkState</td>
 *         <td style="border:#ccc 1px solid">如果指定状态非预期，则抛出{@link java.lang.IllegalStateException}</td>
 *     </tr>
 *     <tr>
 *         <td style="border:#ccc 1px solid">checkOperation</td>
 *         <td style="border:#ccc 1px solid">如果指定操作不可执行，则抛出{@link java.lang.UnsupportedOperationException}</td>
 *     </tr>
 * </table>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/24 0024.
 *
 * @since 1.0
 */
public class Preconditions {

    private Preconditions() { /* no instance needed */ }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     *
     * @since 1.0
     */
    public static <T> T checkNotNull(T reference) {
        if (Objects.isNull(reference)) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     *
     * @since 1.0
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (Objects.isNull(reference)) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *     message is formed by replacing each {@code %s} placeholder in the template with an
     *     argument. These are matched by position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *     in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     *     are converted to strings using {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     *
     * @since 1.0
     */
    public static <T> T checkNotNull(T reference,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (Objects.isNull(reference)) {
            // If either of these parameters is null, the right thing happens anyway
            throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *     message is formed by replacing each {@code %s} placeholder in the template with an
     *     argument. These are matched by position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *     in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     *     are converted to strings using {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
     *     {@code errorMessageArgs} is null (don't let this happen)
     *
     * @since 1.0
     */
    public static void checkArgument(boolean expression,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @throws IllegalStateException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *     message is formed by replacing each {@code %s} placeholder in the template with an
     *     argument. These are matched by position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *     in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     *     are converted to strings using {@link String#valueOf(Object)}.
     * @throws IllegalStateException if {@code expression} is false
     * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
     *     {@code errorMessageArgs} is null (don't let this happen)
     *
     * @since 1.0
     */
    public static void checkState(boolean expression,
                                  String errorMessageTemplate,
                                  Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }


    /**
     * Ensures the truth of an expression involving the ability of the calling operation.
     *
     * @param expression a boolean expression
     * @throws UnsupportedOperationException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkOperation(boolean expression) {
        if (!expression) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Ensures the truth of an expression involving the ability of the calling operation.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @throws UnsupportedOperationException if {@code expression} is false
     *
     * @since 1.0
     */
    public static void checkOperation(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new UnsupportedOperationException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *     message is formed by replacing each {@code %s} placeholder in the template with an
     *     argument. These are matched by position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *     in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     *     are converted to strings using {@link String#valueOf(Object)}.
     * @throws UnsupportedOperationException if {@code expression} is false
     * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
     *     {@code errorMessageArgs} is null (don't let this happen)
     *
     * @since 1.0
     */
    public static void checkOperation(boolean expression,
                                      String errorMessageTemplate,
                                      Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by
     * position: the first {@code %s} gets {@code args[0]}, etc.  If there are more arguments than
     * placeholders, the unmatched arguments will be appended to the end of the formatted message in
     * square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s} placeholders.
     * @param args the arguments to be substituted into the message template. Arguments are converted
     *     to strings using {@link String#valueOf(Object)}. Arguments can be null.
     *
     * @since 1.0
     */
    // Note that this is somewhat-improperly used from Verify.java as well.
    static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}