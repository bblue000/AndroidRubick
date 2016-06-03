package androidrubick.xbase.view.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 工具类，用于直接获取内部View，内部封装了ViewHolder的逻辑。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/11 0011.
 */
public class ViewHolderUtil {

    private ViewHolderUtil() { /* no instance needed */ }

    /**
     * NOTE: <b>该方法将会覆盖convert view的tag</b>
     *
     * @param view convert view
     * @param id child view id
     * @param <T>
     * @return target view object
     */
    public static <T extends View> T get(View view, int id) {
        if (null == view) {
            throw new NullPointerException("param view is null");
        }
        Object tag = view.getTag();
        if (!(tag instanceof SparseArray)) { // clear tag
            view.setTag(null);
        }
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}