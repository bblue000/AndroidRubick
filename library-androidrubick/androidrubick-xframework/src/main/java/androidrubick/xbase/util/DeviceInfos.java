package androidrubick.xbase.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidrubick.utils.Objects;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.app.XGlobals;

/**
 * 获取设备信息的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/29 0029.
 *
 * @since 1.0
 */
public class DeviceInfos {

    private DeviceInfos() { /* no instance needed */ }

    private static float sDensity = 0.0f;
    private static float sScaledDensity = 0.0f;
    private static int sDisplayWidth = 0;
    private static int sDisplayHeight = 0;

    // >>>>>>>>>>>>>>>>>>>
    private static void getDisplay() {
        if (sDisplayWidth <= 0 || sDisplayHeight <= 0 || sDensity <= 0.0f) {
            WindowManager wm = (WindowManager) XGlobals.getAppContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDisplayWidth = dm.widthPixels;
            sDisplayHeight = dm.heightPixels;
            sDensity = dm.density;
            sScaledDensity = dm.scaledDensity;
        }
    }
    // 获取屏幕宽度
    public static int getScreenWidth() {
        getDisplay();
        return sDisplayWidth;
    }

    // 获取屏幕高度
    public static int getScreenHeight() {
        getDisplay();
        return sDisplayHeight;
    }

    /**
     * 获取屏幕密度
     */
    public static float getDensity() {
        getDisplay();
        return sDensity;
    }

    /**
     * 用于字体大小的密度
     */
    public static float getScaledDensity() {
        getDisplay();
        return sScaledDensity;
    }

    /**
     * 获取客户端的分辨率（例如480x800）
     */
    public static String getDeviceResolution() {
        return getDeviceResolution("x");
    }

    /**
     * 获取客户端的分辨率
     * @param linkMark 连接符，{@link #getDeviceResolution()} 使用的是“x”
     */
    public static String getDeviceResolution(String linkMark) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        return width + linkMark + height;
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 获取系统、机器相关的信息
    /**
     * 获取sdk版本号
     */
    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取android 系统发布版本，如“2.3.3”，“4.0.3”
     */
    public static String getAndroidOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取本机的发布版本
     * @return model of the device, or 'unknown'
     */
    public static String getBuildId() {
        return Build.ID;
    }

    /**
     * 获取本机机型，如“MI 4W”（小米4）
     * @return model of the device, or 'unknown'
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;
        if (TextUtils.isEmpty(model)) {
            return Build.UNKNOWN;
        }
        return model;
    }

    /**
     * 获取本机品牌，如Xiaomi
     *
     * @return brand of the device, or 'unknown'
     */
    public static String getDeviceBrand() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) {
            return Build.UNKNOWN;
        }
        return brand;
    }

    /**
     * 获取本机制造商，如Xiaomi
     *
     * @return brand of the device, or 'unknown'
     */
    public static String getDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (TextUtils.isEmpty(manufacturer)) {
            return Build.UNKNOWN;
        }
        return manufacturer;
    }


    private static String sDeviceUuid;
    /**
     * 设备唯一识别号
     */
    public static String getDeviceUuid() {
        if (null == sDeviceUuid) {
            DeviceUuidFactory factory = new DeviceUuidFactory(XGlobals.getAppContext());
            sDeviceUuid = factory.getDeviceUuid();
        }
        return sDeviceUuid;
    }

    /**
     * 获得设备识别认证码
     * <p/>
     * 需要权限：android.permission.READ_PHONE_STATE
     * @return the unique device ID,
     * for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
     * Return null if device ID is not available.
     */
    public static String getIMEI() {
        AndroidUtils.requestPermission(android.Manifest.permission.READ_PHONE_STATE);
        TelephonyManager tm = (TelephonyManager) XGlobals.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * 获取<code>User-Agent</code>
     */
    public static String getUserAgent() {
        String agent = StandardSystemProperty.HTTP_AGENT.value();
        if (Objects.isEmpty(agent)) {
            String osType = "Android";
            String androidVersion = getAndroidOsVersion();
            String device = getDeviceModel();
            String id = getBuildId();
            agent = String.format("Mozilla/5.0 (Linux; U; %s %s; %s Build/%s)",
                    osType, androidVersion, device, id);
            // Mozilla/5.0 (Linux; U; Android 4.3; en-us; HTC One - 4.3 - API 18 -
            // 1080x1920 Build/JLS36G)
        }
        return agent;
    }

    /**
     * 判断手机是否有外部存储
     */
    public static boolean hasExternalStorage() {
        return Objects.equals(Environment.MEDIA_MOUNTED, Environment.getExternalStorageState());
    }

}