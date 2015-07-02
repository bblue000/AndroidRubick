package androidrubick.xframework.app.control;

import androidrubick.xframework.app.manager.XManager;
import androidrubick.xframework.events.XEventAPI;

/**
 *
 * UI控制器的基类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/2.
 *
 * @since 1.0
 */
public abstract class XUIController<T extends XManager> {

    protected XUIController() {
        XEventAPI.inject(this);
    }

    /**
     * 获取该控制器的数据管理器
     */
    protected abstract T getManager() ;

}
