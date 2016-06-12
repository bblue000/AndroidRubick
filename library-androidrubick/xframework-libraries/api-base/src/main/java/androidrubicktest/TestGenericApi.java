package androidrubicktest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class TestGenericApi {

    private TestGenericApi() { /* no instance needed */ }

    public static void main(String args[]) {

        class A<R> {

            A() {
                getSuperclassTypeParameter(getClass());
            }

        }

        new A<A>() {};


    }
    /**
     * Returns the type from super class's type parameter in {@link $Gson$Types#canonicalize
     * canonical form}.
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        System.out.println(parameterized);
        return null;
//        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
