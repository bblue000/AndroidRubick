package androidrubicktest.api;


import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import androidrubick.encode.MD5;
import androidrubick.xframework.api.param.XParamable;

/**
 * 网络请求构建工具类.
 */
public class ParametersUtils {


    public static long time_delta = 0;
    /**
     * 仅用于加密 不在网络过程中传输的key
     */
    /**
     * 可以放置不属于业务参数,又需要参与apiSign的字段,使用其value
     */
    public Map<String, String> mSignStrings = new HashMap<String, String>();

    /**
     * 参数TreeMap *
     */
    private TreeMap<String, String> params = new TreeMap<String, String>(new Comparator<Object>() {
        public int compare(Object o1, Object o2) {
            if (o1 == null || o2 == null) {
                return 0;
            } else {
                return String.valueOf(o1).compareTo(String.valueOf(o2));
            }
        }
    });
    /**
     * 放置在header中的信息,目前只有apiSign
     */
    private HashMap<String, String> mHeadMap = new HashMap<String, String>();
    public static String API_SECRET = BaseConfig.API_SECRET;
    public static String API_KEY = BaseConfig.API_KEY;
    public static void setAPISecret(String apiSecret) {
        if (null == apiSecret) {
            return ;
        }
        API_SECRET = apiSecret;
    }
    public static void setAPIKey(String apiKey) {
        if (null == apiKey) {
            return ;
        }
        API_KEY = apiKey;
    }
    private Object p;

    /**
     * 添加系统级别的参数
     *
     * @param baseParam 构造对象
     */
    public ParametersUtils(Object baseParam) {
        this.p = baseParam;
        parseFromObject();
    }

    /**
     *
     */
    public ParametersUtils(Map<String, String> map) {
        this.params.putAll(map);
        makeSign();
    }

    /**
     * @param baseParam
     * @param signStrings
     */
    public ParametersUtils(XParamable baseParam, Map<String, String> signStrings) {
        this.p = baseParam;
        this.mSignStrings = signStrings;
        parseFromObject();
    }

    /**
     *
     */
    public ParametersUtils(Map<String, String> map, Map<String, String> signStrings) {
        this.params.putAll(map);
        this.mSignStrings = signStrings;
        makeSignNew();
    }

    /**
     * 添加参数方法
     */
    private void addParam(String key, String value) {
        if (null == key || "".equals(key) || null == value) {
        } else {
            params.put(key.trim(), value.trim());
        }
    }


    /**
     * 添加string类型的参数
     */
    private void addStringParam(String key, String value) {
        addParam(key, value);
    }

    private void addHeader(String key, String value) {
        mHeadMap.put(key, value);
    }

    public HashMap<String, String> getHeaderMap() {
        return mHeadMap;
    }

    /**
     * 签名方法
     */
    private void makeSign() {
        String charset = "utf-8";
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] array = null;
        try {
            boolean isNeedUserSercret = false;
            for (String key : params.keySet()) {
                if (key.equals("userToken")) {
                    isNeedUserSercret = true;
                }
                byteStream.write(params.get(key).getBytes(charset));
            }
            if (isNeedUserSercret) {
                for (HashMap.Entry<String, String> entity : mSignStrings.entrySet()) {
                    byteStream.write(entity.getValue().getBytes(charset));
                }
            }
            byteStream.write(API_SECRET.getBytes(charset));
            array = byteStream.toByteArray();
            byteStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sign = MD5.makeMd5Sum(array);
        addStringParam("apiSign", sign);
    }

    private void makeSignNew() {
        String charset = "utf-8";
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] array = null;
        try {
            //写入API_SECRET
            byteStream.write(API_SECRET.getBytes(charset));

            //如果需要userSecret.写入
            if (!mSignStrings.isEmpty()) {
                for (HashMap.Entry<String, String> entity : mSignStrings.entrySet()) {
                    byteStream.write(("&" + entity.getValue()).getBytes(charset));
                }
            }

            //写入post参数的value
            for (String key : params.keySet()) {
                byteStream.write(params.get(key).getBytes(charset));
            }

            array = byteStream.toByteArray();
            byteStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sign = SHAUtil.makeShaSum(array);
        Log.d("yytest", "sign====>" + sign);
        addHeader("Authorization", "OAuth apiSign="+sign);
    }

    /**
     *
     */
    public Map<String, String> getReqMap() {
        return params;
    }

    /**
     * 获取get请求url
     */
    public String getReqURL(String baseUrl) {
        String paramUrl = "";
        StringBuilder urlBuilder = new StringBuilder();
        Iterator<?> iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            try {
                urlBuilder.append(
                        "&" + key + "=" + URLEncoder.encode(String.valueOf(val), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                urlBuilder.append("&" + key + "=" + val);
            }
        }

        if (!urlBuilder.toString().equals("")) {
            paramUrl = baseUrl + urlBuilder.toString().replaceFirst("&", "?");

        }
        return paramUrl;
    }

    private void parseFromObject() {
        parseFromObjectAsClass(p.getClass());
//        makeSign();
        makeSignNew();
    }

    private void parseFromObjectAsClass(Class<?> clz) {
        if (XParamable.class.isAssignableFrom(clz)) {
            parseFromObjectAsClass(clz.getSuperclass());

            Field[] fs = clz.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true);
                String val = null;
                try {
                    val = String.valueOf(f.get(p));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // update by yong01.yin 是否含有加密字段
                if (!"null".equals(val)) {
                    if (f.isAnnotationPresent(VipAPISecret.class)) {
                        mSignStrings.put(f.getName(), val);
                    } else {
                        params.put(f.getName(), val);
                    }
                }
            }
        }
    }
    public static long getExactlyCurrentTime() {
        return System.currentTimeMillis() - time_delta;
    }
}
