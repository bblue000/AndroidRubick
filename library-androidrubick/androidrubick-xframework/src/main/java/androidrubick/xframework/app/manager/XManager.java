package androidrubick.xframework.app.manager;

/**
 * 项目中的提供数据来源的基类。
 *
 * <p/>
 *
 * 该层主要负责业务元数据（API请求，本地状态等）的获取。
 *
 * 该层有些模块由于业务逻辑，可能需要有缓存在内存中的临时数据，会创建单例；
 *
 * 如果没有这种要求，可以在Controller中进行创建。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/6/1 0001.
 *
 * @since 1.0
 */
public class XManager {

    protected XManager() {

    }

}
