package androidrubick.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import androidrubick.utils.Objects;

/**
 * 简单的{@link java.util.Collection}的创建器
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public class CollectionBuilder {

    public static final int DEFAULT_CAPACITY = 1 << 3;

    public static CollectionBuilder newArrayList() {
        return newArrayList(DEFAULT_CAPACITY);
    }

    public static CollectionBuilder newArrayList(int capacity) {
        return new CollectionBuilder(new ArrayList(capacity));
    }

    public static CollectionBuilder newLinkedList() {
        return new CollectionBuilder(new LinkedList());
    }

    public static CollectionBuilder newHashSet() {
        return newHashSet(DEFAULT_CAPACITY);
    }

    public static CollectionBuilder newHashSet(int capacity) {
        return new CollectionBuilder(new HashSet(capacity));
    }

    public static CollectionBuilder newLinkedHashSet() {
        return newLinkedHashSet(DEFAULT_CAPACITY);
    }

    public static CollectionBuilder newLinkedHashSet(int capacity) {
        return new CollectionBuilder(new LinkedHashSet(capacity));
    }

    public static CollectionBuilder newTreeSet() {
        return new CollectionBuilder(new TreeSet());
    }

//    /**
//     * 回收最近一次创建的
//     */
//    public static void recycle() {
//
//    }

    protected Collection mCollection;
    protected CollectionBuilder(Collection c) {
        mCollection = c;
    }

    public CollectionBuilder add(Object value) {
        mCollection.add(value);
        return this;
    }

    public CollectionBuilder addAll(Collection otherCollection) {
        if (!Objects.isEmpty(otherCollection)) {
            mCollection.addAll(otherCollection);
        }
        return this;
    }

    public <T extends Collection>T build() {
        return (T) mCollection;
    }

}
