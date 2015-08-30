package androidrubick.xframework.impl.job;

import java.util.Comparator;

import androidrubick.utils.NumberUtils;
import androidrubick.utils.Objects;
import androidrubick.xframework.task.XJob;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 *
 * 任务统一的比较器。
 *
 * <p/>
 *
 * 通过{@link #INSTANCE}获取单例
 *
 * <p/>
 * Created by Yin Yong on 15/7/3.
 *
 * @since 1.0
 */
@Configurable
public class XJobComparator implements Comparator<Runnable> {

    /**
     * 单例入口
     */
    public static final XJobComparator INSTANCE = new XJobComparator();

    private XJobComparator(){ }

    @Override
    public int compare(Runnable lhsRun, Runnable rhsRun) {
        XJob lhs = XJob.asAsyncTask(lhsRun);
        XJob rhs = XJob.asAsyncTask(rhsRun);
        boolean islNull = Objects.isNull(lhs);
        boolean isrNull = Objects.isNull(rhs);
        if (islNull || isrNull) {
            return _compare(islNull, isrNull);
        }
        // non-NULL
        // 按照级别
        int typeDiff = lhs.getJobType() - rhs.getJobType();
        if (typeDiff != NumberUtils.INT_ZERO) {
            return typeDiff;
        }

        // 按照是否过期
        boolean isExpiredLhs = lhs.isExpired();
        boolean isExpiredRhs = lhs.isExpired();
        if (isExpiredLhs || isExpiredRhs) {
            return _compare(isExpiredLhs, isExpiredRhs);
        }

        // 按照创建时间
        long createTimeDiff = lhs.getCreateTime() - rhs.getCreateTime();
        return (int) createTimeDiff;
    }

    protected int _compare(boolean lhs, boolean rhs) {
        if (lhs && rhs) {
            return 0;
        }
        if (lhs) {
            return -1;
        }
        if (rhs) {
            return 1;
        }
        return 0;
    }
}
