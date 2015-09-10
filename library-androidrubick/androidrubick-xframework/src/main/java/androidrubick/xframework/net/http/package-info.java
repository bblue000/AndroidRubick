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
 * 在框架中，请求自身为一个创建器。
 *
 * <p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.net.http;