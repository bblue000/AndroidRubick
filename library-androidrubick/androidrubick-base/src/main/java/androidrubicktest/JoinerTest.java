package androidrubicktest;

import java.util.ArrayList;

import androidrubick.text.Joiner;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Function;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/20.
 *
 * @since 1.0
 */
public class JoinerTest {

    public static void main(String args[]) {
        testBasic();
        testPreSuffix();
        testSkipNulls();
        testUseForNull();
        testCollection();

        testToStringFunction();
    }

    public static void testBasic() {
        System.out.println("=========testBasic");
        System.out.println(
                Joiner.by()
                        .join(ArraysCompat.by()));
        System.out.println(
                Joiner.by()
                        .join(ArraysCompat.by("a", 1, null)));
        System.out.println(
                Joiner.by(", ")
                .join(ArraysCompat.by("a", 1, null)));
    }

    public static void testPreSuffix() {
        System.out.println("=========testPreSuffix");
        System.out.println(
                Joiner.by(", ")
                        .withPreAndSuffix("[", "]")
                        .join(ArraysCompat.by("a", 1, null)));
    }

    public static void testSkipNulls() {
        System.out.println("=========testSkipNulls");
        System.out.println(
                Joiner.by(", ")
                        .withPreAndSuffix("[", "]")
                        .skipNulls()
                        .join(ArraysCompat.by("a", 1, null)));
    }

    public static void testUseForNull() {
        System.out.println("=========testUseForNull");
        System.out.println(
                Joiner.by(", ")
                        .withPreAndSuffix("[", "]")
                        .useForNull("#")
                        .join(ArraysCompat.by("a", 1, null)));
    }

    public static void testCollection() {
        System.out.println("=========testCollection");
        ArrayList list = new ArrayList();
        list.add("a");
        list.add(1);
        list.add(null);
        System.out.println(
                Joiner.by(", ")
                        .withPreAndSuffix("[", "]")
                        .useForNull("#")
                        .join(list));
    }

    public static void testToStringFunction() {
        System.out.println("=========testToStringFunction");
        System.out.println(
                Joiner.by(", ")
                        .withPreAndSuffix("[", "]")
                        .useForNull("#")
                        .join(new Function<Object, CharSequence>() {
                            @Override
                            public CharSequence apply(Object input) {
                                return "%" + input + "%";
                            }
                        }, ArraysCompat.by("a", 1, null)));
    }

}
