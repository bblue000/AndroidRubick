package androidrubick.xbase.util;

/**
 * 定义
 *
 * <p/>
 *
 * 所有的值都是以毫秒为单位。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/3.
 *
 * @since 1.0
 */
public class TimeSlots {
    private TimeSlots() {
    }

    /**
     * UI交互平均时间
     * @since 1.0
     */
    public static long getUIAvgSlot() {
        return 5 * 1000L;
    }

    /**
     * 无用户操作闲置时间
     * @since 1.0
     */
    public static long getNonOpSlot() {
        return 15 * 1000L;
    }

}