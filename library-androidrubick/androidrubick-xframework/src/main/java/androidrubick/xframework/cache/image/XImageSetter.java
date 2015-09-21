package androidrubick.xframework.cache.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import androidrubick.utils.Objects;

/**
 *
 * 设置图片的操作对象
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public interface XImageSetter {

    /**
     * 设置背景图片的对象
     */
    XImageSetter VIEW_BG_SETTER = new XImageSetter() {
        @Override
        public void setImage(View view, Bitmap bitmap) {
            view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), bitmap));
        }
    };

    /**
     * 设置Image src图片的对象
     */
    XImageSetter IMAGE_SRC_SETTER = new XImageSetter() {
        @Override
        public void setImage(View view, Bitmap bitmap) {
            if (view instanceof ImageView) {
                Objects.getAs(view, ImageView.class).setImageBitmap(bitmap);
            }
        }
    };

    /**
     * 如果是通用的View，则设置背景图片；如果是ImageView，设置Image src
     */
    XImageSetter BOTH_WAY = new XImageSetter() {
        @Override
        public void setImage(View view, Bitmap bitmap) {
            if (view instanceof ImageView) {
                IMAGE_SRC_SETTER.setImage(Objects.getAs(view, ImageView.class), bitmap);
            } else {
                VIEW_BG_SETTER.setImage(view, bitmap);
            }
        }
    };

    /**
     * 根据提供的<code>bitmap</code>设置图片
     */
    public void setImage(View view, Bitmap bitmap) ;

}
