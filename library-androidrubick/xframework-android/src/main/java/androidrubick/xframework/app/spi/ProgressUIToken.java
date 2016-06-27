package androidrubick.xframework.app.spi;

/**
 * 专用于进度UI的Token
 *
 * <p/>
 *
 * Created by Yin Yong on 16/6/7.
 */
public interface ProgressUIToken extends FloatUIToken {

    /**
     * [0, 100]区间内，表示进度
     *
     * @param percent 进度值
     * @param fraction [0, 1]，小数进度值，提供更精确的数值
     */
    void updateProgress(int percent, float fraction);

}
