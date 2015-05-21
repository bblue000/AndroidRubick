package androidrubicktest;

import androidrubick.net.MediaType;
import androidrubick.text.Charsets;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/21.
 *
 * @since 1.0
 */
public class MediaTypeTest {

    public static void main(String args[]) {
        testBasic();
        testWithParam();
        testEquals();
    }

    public static void testBasic() {
        System.out.println("===============testBasic");
        System.out.println(MediaType.ANY);
        System.out.println(MediaType.ANY_APPLICATION);
        System.out.println(MediaType.JSON);
        System.out.println(MediaType.FORM_DATA);
    }

    public static void testWithParam() {
        System.out.println("===============testWithParam");
        System.out.println(MediaType.JSON.withCharset(Charsets.UTF_8.name()));
        System.out.println(MediaType.JSON.withParameters(MediaType.UTF_8_CONSTANT_PARAMETERS)
        .withParameter("q", "1"));
        System.out.println(MediaType.JSON.withParameters(MediaType.UTF_8_CONSTANT_PARAMETERS)
        .withParameter("q", "1").withoutParameters());
    }

    public static void testEquals() {
        System.out.println("===============testEquals");
        System.out.println(MediaType.JSON.withCharset(Charsets.UTF_8.name()).equals(
                MediaType.JSON.withParameters(MediaType.UTF_8_CONSTANT_PARAMETERS)));
        System.out.println(MediaType.JSON.withCharset(Charsets.UTF_8.name()).equals(
                MediaType.JSON.withParameters(MediaType.UTF_8_CONSTANT_PARAMETERS)
        .withParameter("q", "1")));
        System.out.println(MediaType.JSON.equals(MediaType.JSON.withParameters(MediaType.UTF_8_CONSTANT_PARAMETERS)
        .withParameter("q", "1").withoutParameters()));
    }



}
