package androidrubicktest;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class TestHttpUrls {

    private TestHttpUrls() { /* no instance needed */ }

    public static void main(String args[]) {

        class A {
            public String d() {
                try {
                    return dd();
                } finally {
                    System.out.println("2");
                }
            }

            public String dd() {
                System.out.println("1");
                return "3";
            }
        }

        System.out.println(new A().d());


    }

}