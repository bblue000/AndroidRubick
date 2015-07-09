package org.androidrubick.view.adapter;

import android.util.SparseArray;
import android.view.View;

import androidrubick.base.android.R;

import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;

/**
 * 工具类，用于直接获取内部View，内部封装了ViewHolder的逻辑
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/11 0011.
 */
public class ViewHolderUtil {

    private ViewHolderUtil() { /* no instance needed */ }

    public static <T extends View> T get(View view, int id) {
        Preconditions.checkNotNull(view, "param view is null");
        SparseArray<? extends View> sparseArray = Objects.getAs(view.getTag(R.id.org_androidrubick_view_adapter_viewholderutil));
        if (null == sparseArray) {
            sparseArray = new SparseArray<View>();
            view.setTag(R.id.org_androidrubick_view_adapter_viewholderutil, sparseArray);
        }
        return (T) sparseArray.get(id);
    }

}