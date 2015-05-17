package androidrubick.io;

/**
 * IO 操作中的相关常量
 * 
 * @author Yin Yong
 *
 * @since 1.0
 *
 */
public interface IOConstants {
	/**
	 * 默认的字符大小（用于传输/缓存等）
     *
     * @since 1.0
	 */
	int DEF_BUFFER_SIZE = 512;
	
	/**
	 * 默认的编码——UTF-8
     *
     * @since 1.0
	 */
	String DEF_CHARSET = "UTF-8";
	
	/**
	 * 默认的超时时间——15s
     *
     * @since 1.0
	 */
	int DEF_TIMEOUT = 15 * 1000;
}
