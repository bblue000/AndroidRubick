package androidrubick.xframework.task;

import java.util.Comparator;

import androidrubick.utils.Objects;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/3.
 *
 * @since 1.0
 */
public class XJobComparator implements Comparator<XJob> {

    @Override
    public int compare(XJob lhs, XJob rhs) {
        boolean islNull = Objects.isNull(lhs);
        boolean isrNull = Objects.isNull(rhs);
        if (islNull && isrNull) {
            return 0;
        }
        if (islNull) {
            return -1;
        }
        if (isrNull) {
            return 1;
        }
        // non-NULL
        long createTimeDelta = lhs.getCreateTime() - rhs.getCreateTime();
        long expireTimeDelta = lhs.getCreateTime() - rhs.getCreateTime();
        return 0;
    }

}
