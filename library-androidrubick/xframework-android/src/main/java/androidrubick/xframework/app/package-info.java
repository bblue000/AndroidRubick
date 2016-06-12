/**
 * 我们借鉴Java Web的MVC框架，引入VFMM框架：
 * <table>
 *     <tr>
 *         <td>缩写</td>
 *         <td>含义</td>
 *         <td>交互关系</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>视图</td>
 *         <td>Android中的Activity和Fragment</td>
 *     </tr>
 *     <tr>
 *         <td>F</td>
 *         <td>View Flow视图流程</td>
 *         <td>控制Android中的Activity/Fragment之间的跳转、数据传递逻辑</td>
 *     </tr>
 *     <tr>
 *         <td>M</td>
 *         <td>Manager数据管理员</td>
 *         <td>封装完善的数据业务逻辑(如：API封装)</td>
 *     </tr>
 *     <tr>
 *         <td>M</td>
 *         <td>Model数据层</td>
 *         <td>如果细化了，可以分为业务数据（从Manager获得），UI数据（UI层使用）</td>
 *     </tr>
 * </table>
 *
 * 在框架中依赖是单向的，View依赖于Controller，Controller依赖于ModelManager，依次类推。
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