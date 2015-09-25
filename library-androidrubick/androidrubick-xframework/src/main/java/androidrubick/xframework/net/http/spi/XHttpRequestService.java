package androidrubick.xframework.net.http.spi;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * 提供实现HTTP请求的服务
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public interface XHttpRequestService extends XSpiService {

    /**
     * 执行请求
     *
     * <p/>
     *
     * 当且仅当，响应状态在[200, 300)区间，返回一个XHttpResultHolder对象；
     *
     * 其他响应状态根据具体的含义，抛出不同的异常。
     *
     * <p/>
     *
     * <table>
     *     <tr>
     *         <td>错误类型</td>
     *         <td>描述</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Timeout}</td>
     *         <td>请求服务器建立超时，或者socket读取超时</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#NoConnection}</td>
     *         <td>无法建立连接</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Auth}</td>
     *         <td>没有权限访问（401 & 403）</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Server}</td>
     *         <td>服务端错误（5xx）</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Network}</td>
     *         <td>建立了连接，且能获得请求行（status line），但是获取内容时出错，多为网络原因</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Other}</td>
     *         <td>其他运行时错误</td>
     *     </tr>
     * </table>
     *
     * @throws XHttpError
     * @throws java.lang.RuntimeException <code>request</code>为空将抛出空指针异常，其他代码导致的运行时异常
     *
     * @see XHttpError
     */
    XHttpResponse performRequest(XHttpRequest request) throws XHttpError;

}
