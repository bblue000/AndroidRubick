package androidrubick.xframework.cache.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 *
 * 设置图片的
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public interface XImageSetter<V extends View> {

    XImageSetter VIEW_BG_SETTER = new XImageSetter() {
        @Override
        public void setImage(View view, Bitmap bitmap) {
            view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), bitmap));
        }
    };

    XImageSetter<ImageView> IMAGE_SRC_SETTER = new XImageSetter<ImageView>() {
        @Override
        public void setImage(ImageView view, Bitmap bitmap) {
            view.setImageBitmap(bitmap);
        }
    };

    /**
     * 根据提供的<code>bitmap</code>设置图片
     */
    public void setImage(V view, Bitmap bitmap) ;

}
