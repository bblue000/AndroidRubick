package androidrubick.text;

import java.util.Iterator;

import androidrubick.utils.Function;
import androidrubick.utils.Functions;
import androidrubick.utils.Objects;

import static androidrubick.utils.Preconditions.*;

/**
 * 用于字符串连接
 *
 * <p/>
 *
 * An object which joins pieces of text (specified as an array, {@link Iterable}, varargs or even a
 * {@link java.util.Map}) with a separator. It either appends the results to an {@link Appendable} or returns
 * them as a {@link String}. Example: <pre>   {@code
 *
 *   Joiner joiner = Joiner.by("; ").skipNulls();
 *    . . .
 *   return joiner.join("Harry", null, "Ron", "Hermione");}</pre>
 *
 * <p>This returns the string {@code "Harry; Ron; Hermione"}. Note that all input elements are
 * converted to strings using {@link androidrubick.utils.Function#apply(Object)}
 * （默认为{@link androidrubick.utils.Functions#TO_STRING}） before being appended.
 *
 * <p>If neither {@link #skipNulls()} nor {@link #useForNull(java.lang.CharSequence)} is specified, the joining
 * methods will use {@code null}.
 *
 * <p><b>Warning: joiner instances are always immutable</b>; a configuration method such as {@code
 * useForNull} has no effect on the instance it is invoked on! You must store and use the new joiner
 * instance returned by the method. This makes joiners thread-safe, and safe to store as {@code
 * static final} constants. <pre>   {@code
 *
 *   // Bad! Do not do this!
 *   Joiner joiner = Joiner.on(',');
 *   joiner.skipNulls(); // does nothing!
 *   return joiner.join("wrong", null, "wrong");}</pre>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/19 0019.
 *
 * @since 1.0
 */
public class Joiner {

    /**
     * 基于指定的分割符一个新的{@link Joiner}对象
     *
     * @param sep
     * @return 基于指定的分割符一个新的{@link Joiner}对象
     */
    public static Joiner by(CharSequence sep) {
        return new Joiner(sep);
    }

    /**
     *
     * 基于指定的分割字符一个新的{@link Joiner}对象
     *
     * @param sepChar
     * @return 基于指定的分割字符一个新的{@link Joiner}对象
     */
    public static Joiner by(char sepChar) {
        return new Joiner(String.valueOf(sepChar));
    }

    protected final CharSequence mSep;
    protected boolean mSkipNulls;
    protected CharSequence mNullText;
    protected Joiner(CharSequence sep) {
        mSep = sep;
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 一些设置
    /**
     * 跳过值为null的对象。
     *
     * <p/>
     *
     * 如果{@link #useForNull(CharSequence)}在之前调用，则{@link #useForNull(CharSequence)}失效；
     * <br/>
     * 如果{@link #useForNull(CharSequence)}在之后调用，则该方法失效；
     */
    public Joiner skipNulls() {
        mSkipNulls = true;
        mNullText = null;
        return this;
    }

    /**
     * 用<code>nullText</code>替换值为null的对象的。
     *
     * <p/>
     *
     * 如果{@link #skipNulls()}在之前调用，则{@link #skipNulls()}失效；
     * <br/>
     * 如果{@link #skipNulls()}在之后调用，则该方法失效；
     */
    public Joiner useForNull(final CharSequence nullText) {
        mSkipNulls = false;
        mNullText = nullText;
        return this;
    }

    // for array
    public <T>String join(T...tokens) {
        return join(Functions.TO_STRING, tokens);
    }

    public <T>String join(Function<? super T, ? extends CharSequence> toStringFunc, T...tokens) {
        checkNotNull(toStringFunc);

        return "";
    }

    public <T>String join(Iterable<T> it) {
        return join(Functions.TO_STRING, it);
    }

    public <T>String join(Function<? super T, ? extends CharSequence> toStringFunc, Iterable<T> iterable) {
        checkNotNull(toStringFunc);
        if (Objects.isNull(iterable)) {
            return Strings.EMPTY;
        }
        return join(toStringFunc, iterable.iterator());
    }

    public <T>String join(Iterator<T> it) {
        return join(Functions.TO_STRING, it);
    }

    public <T>String join(Function<? super T, ? extends CharSequence> toStringFunc, Iterator<T> it) {

    }

    protected <T>CharSequence toStringOf(Function<? super T, ? extends CharSequence> toStringFunc, T value) {
        return toStringFunc.apply(value);
    }

}
