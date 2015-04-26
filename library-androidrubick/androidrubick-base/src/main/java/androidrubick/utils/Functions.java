package androidrubick.utils;

/**
 * 针对Function的操作
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/27.
 */
public class Functions {

    private Functions() {}

    private static class ToStringFunc implements Function<Object, String > {
        @Override
        public String apply(Object input) {
            return Objects.toString(input);
        }
    }
    public static final ToStringFunc TO_STRING = new ToStringFunc();

}
