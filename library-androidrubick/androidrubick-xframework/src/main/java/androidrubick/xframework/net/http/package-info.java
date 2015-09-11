/**
 * 该包想要定义一种顶层接口和工具方法，抽象出android应用程序开发中常用的一些http网络请求相关的功能和模块。
 *
 * <p/>
 *
 * HTTP/HTTPS请求可以分为request(method, header, body), response(header, body);
 *
 * {@link org.apache.http.client.HttpClient}将二者都抽象为{@link org.apache.http.HttpMessage}，是有道理的。
 *
 * <p/>
 *
 * 在框架中，可以创建一个{@link androidrubick.xframework.net.http.request.XHttpRequest XHttpReq}，
 * 设置请求必要的元素（如请求URL，方法等），
 * 然后调用{@link androidrubick.xframework.net.http.request.XHttpRequest#performRequest XHttpReq.performRequest}
 * 发送请求。
 *
 * <p/>
 *
 * {@link androidrubick.xframework.net.http.request.XHttpRequest#performRequest XHttpReq.performRequest}
 * 方法内部调用了
 * {@link androidrubick.xframework.net.http.spi.XHttpRequestService#performRequest XHttpRequestService.performRequest}
 * 执行请求，项目默认提供了“http-default”模块，实现了该服务。
 *
 * <p/><hr/><br/>
 *
 * 执行请求后，或成功，或失败；
 * <br/>
 *
 * 本框架中认为请求成功的前提是：与服务器建立连接，获得完整的
 * 响应状态行（status line），响应头（response header），响应体（response body），
 * 且状态值在[200, 300)之间（包括200，不包括300）。
 *
 *
 * <br/>
 *
 * 其他情况将会抛出{@link androidrubick.xframework.net.http.response.XHttpError XHttpError}。
 * <br/>
 *
 * 该错误的具体描述如下：
 * <table>
 *     <tr>
 *         <td>错误类型</td>
 *         <td>描述</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Timeout Timeout}</td>
 *         <td>请求服务器建立超时，或者socket读取超时</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#NoConnection NoConnection}</td>
 *         <td>无法建立连接</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Auth Auth}</td>
 *         <td>没有权限访问（401 & 403）</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Server Server}</td>
 *         <td>服务端错误（5xx）</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Network Network}</td>
 *         <td>建立了连接，且能获得请求行（status line），但是获取内容时出错，多为网络原因</td>
 *     </tr>
 *     <tr>
 *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Other Other}</td>
 *         <td>其他运行时错误</td>
 *     </tr>
 * </table>
 *
 * <p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.net.http;