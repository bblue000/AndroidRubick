package androidrubick.xframework.impl.api.param;

import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.AppInfos;
import androidrubick.xbase.util.DeviceInfos;

/**
 * 标识对象是否可以转为参数，它并没有包含任何方法。
 *
 * 作为所有参数的父类，便于以后的扩展。
 *
 * <p/>
 *
 * <p/>
 * Created by Yin Yong on 15/6/2.
 *
 * @since 1.0
 */
@Configurable
public class BaseParam {

    /**
     * 设备识别号
     */
    public String deviceToken;

    /**
     * 机器类型，分ios和android
     */
    public String deviceType;

    /**
     * 分辨率
     */
    public String resolution;

    /**
     * 应用的版本号
     */
    public String appVersion;

    public BaseParam() {
        deviceToken = DeviceInfos.getDeviceUuid();
        deviceType = "android";
        resolution = DeviceInfos.getDeviceResolution("x");
        appVersion = AppInfos.getVersionName("1.0");
    }

}
