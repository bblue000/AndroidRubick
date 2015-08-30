package androidrubicktest;

import com.google.gson.Gson;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/29 0029.
 *
 * @since 1.0
 */
public class TestGson {

    private TestGson() { /* no instance needed */ }

    public static void main(String[] args) {
        Gson gson = new Gson();
        System.out.println(gson.fromJson("1", Object.class).getClass());
        System.out.println(gson.fromJson("1", Object.class));

        // gson 对“”，转为null
//        System.out.println(gson.fromJson("", Object.class).getClass());
//        System.out.println(gson.fromJson("", Object.class));

        // "" --> ""
//        System.out.println(gson.toJson("").getClass());
//        System.out.println(gson.toJson(""));

        // null --> "null"
//        System.out.println(gson.toJson(null).getClass());
//        System.out.println(gson.toJson(null));

        // null --> "null"
        System.out.println(gson.toJson(new Object()).getClass());
        System.out.println(gson.toJson(new Object()));
    }

}