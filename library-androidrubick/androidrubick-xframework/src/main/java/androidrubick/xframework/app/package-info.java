/**
 * 我们借鉴Java Web的MVC框架，引入VCMM框架：
 * <table>
 *     <tr>
 *         <td>缩写</td>
 *         <td>含义</td>
 *         <td>交互关系</td>
 *     </tr>
 * </table>
 * <ul>
 *     <li>V--View视图，即Android中的Activity和Fragment</li>
 *     <li>C--View Controller视图控制器，即控制Android中的Activity/Fragment之间的跳转、数据传递逻辑</li>
 *     <li>M--Manager视图控制器，即控制Android中的Activity/Fragment之间的跳转、数据传递逻辑</li>
 * </ul>
 *
 * <p/>
 *
 * 需要联网的客户端一般都有从Server获取数据，向Server提交数据等操作，也就是跟自身的后台交互。
 * <br/>
 * 该模块是http请求的特殊应用，此处封装让它更简化些。
 *
 * <p/>
 * 该模块依赖：
 * <ul>
 *     <li>HTTP模块;</li>
 *     <li>Task模块;</li>
 * </ul>
 *
 * <p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.app;