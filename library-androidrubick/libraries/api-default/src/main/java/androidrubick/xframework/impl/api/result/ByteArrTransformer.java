package androidrubick.xframework.impl.api.result;

import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.io.IOUtils;
import androidrubick.io.PoolingByteArrayOutputStream;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/19 0019.
 *
 * @since 1.0
 */
/*package*/ class ByteArrTransformer extends APITransformer<byte[]> {

    @Override
    public byte[] transform(XHttpResponse response) throws Throwable {
        final ByteArrayPool pool = XHttps.BYTE_ARRAY_POOL;
        byte[] buf = pool.getBuf(256);
        PoolingByteArrayOutputStream out = new PoolingByteArrayOutputStream(pool, 512);
        try {
            IOUtils.writeTo(response.getContent(), false, out, false, buf, null);
            byte[] data = out.toByteArray();

            pool.returnBuf(buf);
            IOUtils.close(out);
            buf = null;
            out = null;

            return data;
        } finally {
            // 确保释放
            pool.returnBuf(buf);
            IOUtils.close(out);
            IOUtils.close(response);
        }
    }
}