### API请求

需要访问网络的客户端要与其服务端通信，会定义一套API，以支持两端的数据同步。

`XAPI`是该模块的总调用入口，基于HTTP，封装了`GET`和`POST`两个请求方法；

以`GET`为例，提供如下两个重载方法：

	/**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
	public static <Result>APIToken get(String url, Object param, Class<Result> result, APICallback<Result> callback)

	/**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param extraHeaders 额外的HTTP请求头信息
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
	public static <Result>APIToken get(String url, Object param, Map<String, String> extraHeaders, Class<Result> result, APICallback<Result> callback)


后者比前者多了一个参数，方便增加额外的请求头信息。


#### 参数
不关注参数的类型，使用`Object`通配；

抽象出通用的参数类型基类——`BaseParam`，APP可以自行修改：

	/**
 	 * 标识对象是否可以转为参数，它并没有包含任何方法。
 	 *
 	 * 作为所有参数的父类，便于以后的扩展。
 	 *
  	 * <p/>
 	 *
 	 * <p/>
 	 * Created by Yin Yong on 15/6/2.
 	 *
 	 * @since 1.0
 	 */
	@Configurable
	public class BaseParam {

    	/**
     	 * 设备识别号
     	 */
    	public String deviceToken;

    	/**
     	 * 机器类型，分ios和android
     	 */
    	public String deviceType;

    	/**
     	 * 分辨率
     	 */
    	public String resolution;

    	/**
     	 * 应用的版本号
     	 */
    	public String appVersion;

    	public BaseParam() {
        	deviceToken = DeviceInfos.getDeviceUuid();
        	deviceType = "android";
        	resolution = DeviceInfos.getDeviceResolution("x");
        	appVersion = AppInfos.getVersionName("1.0");
    	}

	}

**NOTE:**客户端可根据自身情况修改。



#### 返回类型
一般客户端和后台（中间层）会约定一种形式，告知客户端API是否请求成功;

以两端约定JSON格式的情况为例：

	{
		"code": 200,
		"msg": "",
		data: []
	}

抽象出基本返回类型——`BaseResult`：

	/**
 	 * 标识对象是否可以转为结果，它并没有包含任何方法。
 	 *
 	 * <p/>
 	 * <p/>
 	 * Created by Yin Yong on 15/6/2.
 	 *
 	 * @since 1.0
 	 */
	@Configurable
	public class BaseResult<T> {

    	public int code;

    	public String msg;

    	public T data;

	}

举例说明：

一个获取订单列表的接口，data类型为Order[]，则调用方式如下：

	XAPI.get("{url}", null, Order[].class, new APICallback<Order[]>() {
			@Override
            public void onSuccess(Order[] orders) {
                // do sth.
            }

            @Override
            public void onFailed(APIStatus status) {
                // do sth.
            }

	});


> 有些时候，客户端请求的接口来源于第三方，虽然同样为JSON，但格式不一致！

可以创建一个模型，加上`NoneAPIResponse`注解，告诉内部处理器，不要以上述方式解析（将JSON字符串转为BaseResult<XXX>，最终返回XXX对象），而是直接将JSON字符串转为A类型：

	@NoneAPIResponse
	public class A { ... }

此外，也支持一些其他类型的解析，如byte[]，String，JSONObject，JSONArray等。

**NOTE:**客户端可根据自身情况修改。



