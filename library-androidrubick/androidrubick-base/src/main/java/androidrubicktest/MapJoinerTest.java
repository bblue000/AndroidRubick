package androidrubicktest;

import androidrubick.collect.MapBuilder;
import androidrubick.text.Joiner;
import androidrubick.text.MapJoiner;
import androidrubick.utils.Function;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/20.
 *
 * @since 1.0
 */
public class MapJoinerTest {

    public static void main(String args[]) {
        testBasic();
        testPreSuffix();
        testSkipNulls();
        testUseForNull();

        testToStringFunction();

        testContentType();
    }

    public static void testBasic() {
        System.out.println("=========testBasic");
        System.out.println(
                MapJoiner.by(", ", ":")
                        .join(MapBuilder.newHashMap().put("a", "1").put(1, null).put(null, 3).build())
        );
    }

    public static void testPreSuffix() {
        System.out.println("=========testPreSuffix");
        System.out.println(
                MapJoiner.by(", ", ":")
                        .withPreAndSuffix("{", "}")
                        .join(MapBuilder.newHashMap().put("a", "1").put(1, null).put(null, 3).build())
        );
    }

    public static void testSkipNulls() {
        System.out.println("=========testSkipNulls");
        System.out.println(
                MapJoiner.by(", ", ":")
                        .skipNullKeys()
                        .join(MapBuilder.newHashMap().put("a", "1").put(1, null).put(null, 3).build())
        );
    }

    public static void testUseForNull() {
        System.out.println("=========testUseForNull");
        System.out.println(
                MapJoiner.by(", ", ":")
                        .useForNullKey("#")
                        .useForNullValue("$")
                        .join(MapBuilder.newHashMap().put("a", "1").put(1, null).put(null, 3).build())
        );
    }

    public static void testToStringFunction() {
        System.out.println("=========testToStringFunction");
        System.out.println(
                MapJoiner.by(", ", ":")
                        .useForNullKey("#")
                        .useForNullValue("$")
                        .withToStringFuncOfKey(new Function<Object, CharSequence>() {
                            @Override
                            public CharSequence apply(Object input) {
                                return null;
                            }
                        })
                        .withToStringFuncOfValue(new Function() {
                            @Override
                            public Object apply(Object input) {
                                return "*" + input + "*";
                            }
                        })
                        .join(MapBuilder.newHashMap().put("a", "1").put(1, null).put(null, 3).build())
        );
    }


    public static void testContentType() {
        System.out.println("=========testContentType");
        System.out.println(
                MapJoiner.by("; ", "=")
                        .join(MapBuilder.newHashMap().put("charset", "UTF-8").put("xxx", "yyy").build()));
    }
}
