package androidrubicktest;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/7/11 0011.
 *
 * @since 1.0
 */
public class InheritTest {

    public static void main(String[] args) {
//        new Aaa().d();
        new Aaa().dd();
    }

    static class A {
        void d() {
            System.out.println("A d");
        }
    }

    static class Aa extends A {
        void d() {
            System.out.println("Aa d");
            super.d();
        }

        void dd() {
            System.out.println("Aa dd");
            super.d();
        }
    }

    static class Aaa extends Aa {
        void d() {
            System.out.println("Aaa d");
            super.d();
        }

//        @Override
//        void dd() {
//            System.out.println("Aaa dd");
//            super.d();
//        }
    }

}
