/**
 * 该包想要定义一种顶层接口和工具方法，抽象出android应用程序开发中常用的一些API网络相关的功能和模块。
 *
 * <p/>
 *
 * 需要联网的客户端一般都有从Server获取数据，向Server提交数据等操作，也就是跟自身的后台交互。
 * <br/>
 * 该模块是http请求的特殊应用，此处封装让它更简化一些。
 * 可以直接调用：{@link androidrubick.xframework.api.XAPI XAPI}.&lt;方法类型,如get, post&gt;
 *
 * <p/>
 *
 * <p/>
 * 该模块依赖：
 * <ul>
 *     <li>HTTP模块;</li>
 *     <li>Job模块;</li>
 * </ul>
 *
 * <p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.api;