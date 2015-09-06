package androidrubick.cache.mem;

import java.util.LinkedList;

import androidrubick.collect.CollectionsCompat;
import androidrubick.utils.Objects;
import androidrubick.utils.Recycleable;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public abstract class InstancePool<E> implements Recycleable {

    private int mPoolSize;
    private LinkedList<E> mPoolList;
    protected InstancePool(int poolSize) {
        this.mPoolSize = poolSize;
        this.mPoolList = new LinkedList<E>();
    }

    /**
     * obtain an instance
     */
    public E obtain() {
        synchronized (mPoolList) {
            if (!mPoolList.isEmpty()) {
                return mPoolList.removeFirst();
            }
            return create();
        }
    }

    /**
     * max pool size
     */
    public final int poolSize() {
        return mPoolSize;
    }

    /**
     * current pool size
     */
    public int size() {
        return CollectionsCompat.getSize(mPoolList);
    }

    /**
     * 回收对象，如果对象池没有达到上限（{@link #poolSize()}），将加入到对象池中
     */
    public void returnInstance(E e) {
        if (Objects.isNull(e)) {
            return;
        }
        synchronized (mPoolList) {
            if (mPoolList.size() < mPoolSize) {
                if (e instanceof Recycleable) {
                    Objects.getAs(e, Recycleable.class).recycle();
                }
                mPoolList.add(e);
            }
        }
    }

    /**
     * create a new instance
     */
    protected abstract E create();

    @Override
    public void recycle() {
        if (null != mPoolList && !mPoolList.isEmpty()) {
            mPoolList.clear();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        recycle();
        mPoolList = null;
        super.finalize();
    }
}
