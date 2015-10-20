package androidrubick.collect;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import static androidrubick.collect.CollectionsCompat.*;

/**
 * 简单的{@link java.util.Set}的创建器
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public class SetBuilder {

    public static SetBuilder newHashSet() {
        return newHashSet(DEFAULT_CAPACITY);
    }

    public static SetBuilder newHashSet(int capacity) {
        return new SetBuilder(new HashSet(capacity));
    }

    public static SetBuilder newLinkedHashSet() {
        return newLinkedHashSet(DEFAULT_CAPACITY);
    }

    public static SetBuilder newLinkedHashSet(int capacity) {
        return new SetBuilder(new LinkedHashSet(capacity));
    }

    public static SetBuilder newTreeSet() {
        return new SetBuilder(new TreeSet());
    }

//    /**
//     * 回收最近一次创建的
//     */
//    public static void recycle() {
//
//    }

    protected Set mCollection;
    protected SetBuilder(Set c) {
        mCollection = c;
    }

    public SetBuilder add(Object value) {
        mCollection.add(value);
        return this;
    }

    public SetBuilder addAll(Collection otherCollection) {
        CollectionsCompat.addAll(mCollection, otherCollection);
        return this;
    }

    public SetBuilder appendAll(Object...arr) {
        CollectionsCompat.appendAll(mCollection, arr);
        return this;
    }

    public <T extends Set>T build() {
        return (T) mCollection;
    }

}
