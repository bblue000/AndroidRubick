package androidrubick.xbase.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;

import androidrubick.io.FileUtils;
import androidrubick.text.Strings;
import androidrubick.utils.ArraysCompat;
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
    /**
     * 获取屏幕宽度(单位：px)
     * @since 1.0
     */
    public static int getScreenWidth() {
        getDisplay();
        return sDisplayWidth;
    }

    /**
     * 获取屏幕高度(单位：px)
     * @since 1.0
     */
    public static int getScreenHeight() {
        getDisplay();
        return sDisplayHeight;
    }

    /**
     * 获取屏幕密度
     * @since 1.0
     */
    public static float getDensity() {
        getDisplay();
        return sDensity;
    }

    /**
     * 用于字体大小的密度
     * @since 1.0
     */
    public static float getScaledDensity() {
        getDisplay();
        return sScaledDensity;
    }

    /**
     * 获取客户端的分辨率（例如480x800）
     * @since 1.0
     */
    public static String getDeviceResolution() {
        return getDeviceResolution("x");
    }

    /**
     * 获取客户端的分辨率
     * @param linkMark 连接符，{@link #getDeviceResolution()} 使用的是“x”
     * @since 1.0
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
     * @since 1.0
     */
    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isSDKOver(int targetSDK) {
        return getAndroidSDKVersion() >= targetSDK;
    }

    /**
     * 获取android 系统发布版本，如“2.3.3”，“4.0.3”
     * @since 1.0
     */
    public static String getAndroidOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取本机的发布版本
     * @return model of the device, or 'unknown'
     * @since 1.0
     */
    public static String getBuildId() {
        return Build.ID;
    }

    /**
     * 获取本机机型，如“MI 4W”（小米4）
     * @return model of the device, or 'unknown'
     * @since 1.0
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;
        if (Strings.isEmpty(model)) {
            return Build.UNKNOWN;
        }
        return model;
    }

    /**
     * 获取本机品牌，如Xiaomi
     *
     * @return brand of the device, or 'unknown'
     * @since 1.0
     */
    public static String getDeviceBrand() {
        String brand = Build.BRAND;
        if (Strings.isEmpty(brand)) {
            return Build.UNKNOWN;
        }
        return brand;
    }

    /**
     * 获取本机制造商，如Xiaomi
     *
     * @return brand of the device, or 'unknown'
     * @since 1.0
     */
    public static String getDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (Strings.isEmpty(manufacturer)) {
            return Build.UNKNOWN;
        }
        return manufacturer;
    }


    private static String sDeviceUuid;
    /**
     * 设备唯一识别号
     * @since 1.0
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
     * @since 1.0
     */
    public static String getIMEI() {
        AndroidUtils.checkPermission(android.Manifest.permission.READ_PHONE_STATE);
        TelephonyManager tm = (TelephonyManager) XGlobals.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * 获取<code>User-Agent</code>
     * @since 1.0
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
     * @since 1.0
     */
    public static boolean hasExternalStorage() {
        return Objects.equals(Environment.MEDIA_MOUNTED, Environment.getExternalStorageState());
    }

    /**
     * 如果没有模拟存储和外部存储设备，返回null；
     * <br/>
     * 如果API < 21，返回{@link android.content.Context#getExternalCacheDir()}；
     * <br/>
     * 如果API >= 21，返回{@link android.content.Context#getExternalCacheDirs()}；
     * <br/>
     * @since 1.0
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static File[] getExternalCacheDirs() {
        // get ext cache dir
        if (DeviceInfos.isSDKOver(Build.VERSION_CODES.KITKAT)) {
            File[] extCacheDirs = XGlobals.getAppContext().getExternalCacheDirs();
            if (!ArraysCompat.isEmpty(extCacheDirs)) {
                return extCacheDirs;
            }
        }
        File extCacheDir = XGlobals.getAppContext().getExternalCacheDir();
        if (Objects.isNull(extCacheDir) || FileUtils.exists(extCacheDir)) {
            return null;
        }
        return new File[] {
                extCacheDir
        };
    }

}