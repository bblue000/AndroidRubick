/**
 * 本包提供adapter强化使用Holder及简单封装。
 *
 * <p>
 * 
 * 示例：
 * <pre style="border:#ccc 1px solid">
 *public class UserNameAdapter extends AbsResLayoutAdapter<UserInfo, UserNameAdapter.ViewHolder> {
 *
 *	public UserNameAdapter(Context context) {
 *		super(context);
 *	}
 *
 *	class ViewHolder {
 *		&#064ViewInject(id = R.id.main_view_person_name_tv)
 *		TextView mName_TV;
 *	}
 *
 *	&#064Override
 *	protected int provideLayoutResId() {
 *		return R.layout.main_view_person_item;
 *	}
 *
 *	&#064Override
 *	protected ViewHolder newHolder(int position, View contentView) {
 *		ViewHolder holder = new ViewHolder();
 *		injectHolder(holder, contentView);
 *		return holder;
 *	}
 *
 *	&#064Override
 *	protected void bindView(ViewHolder holder, UserInfo data, int position,
 *				View view) {
 *		holder.mName_TV.setText(data.getName());
 *	}
 *}
 * 	</pre>
 * </p>
 * @author Yin Yong
 */
package org.ixming.androidrubick.adapter;