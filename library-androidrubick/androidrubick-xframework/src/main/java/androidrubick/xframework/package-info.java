/**
 * 该包想要定义一种顶层接口、基本类和工具方法，抽象出android应用程序开发中常用的一些功能和模块。
 *
 * <table>
 *     <tr>
 *         <td width="10%">模块</td>
 *         <td width="30%">说明</td>
 *         <td width="25%">可定制服务服务</td>
 *         <td width="35%">服务说明</td>
 *     </tr>
 *     <tr>
 *         <td>job</td>
 *         <td>任务模块，提供了基本任务类{@link androidrubick.xframework.job.XJob XJob}</td>
 *         <td>{@link androidrubick.xframework.job.spi.XJobExecutorService XJobExecutorService}</td>
 *         <td>定义如何执行任务{@link androidrubick.xframework.job.spi.XJobExecutorService#execute XJobExecutorService.execute}</td>
 *     </tr>
 *     <tr>
 *         <td>http</td>
 *         <td>HTTP请求模块，提供了HTTP请求类{@link androidrubick.xframework.net.http.request.XHttpRequest XHttpRequest}和
 *         可选的{@link androidrubick.xframework.net.http.request.body.XHttpBody XHttpBody}</td>
 *         <td>{@link androidrubick.xframework.net.http.spi.XHttpRequestService XHttpRequestService}</td>
 *         <td>定义如何执行HTTP请求{@link androidrubick.xframework.net.http.spi.XHttpRequestService#performRequest XHttpRequestService.performRequest}</td>
 *     </tr>
 *     <tr>
 *         <td>api</td>
 *         <td>api请求模块，提供了静态调用类{@link androidrubick.xframework.api.XAPI XAPI}，支持GET和POST请求</td>
 *         <td>{@link androidrubick.xframework.api.spi.XAPIService XAPIService}</td>
 *         <td>定义如何执行API请求{@link androidrubick.xframework.api.spi.XAPIService#doAPI XAPIService.doAPI}</td>
 *     </tr>
 * </table>
 *
 * <p/><p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework;