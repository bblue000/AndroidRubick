package androidrubick.xframework.net.http.request.body;

import android.util.AndroidRuntimeException;

import java.io.IOException;
import java.io.OutputStream;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.XHttpRequestEncoder;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpUrlEncodedBody extends XHttpBody<XHttpUrlEncodedBody> {

    protected XHttpUrlEncodedBody() {
        super();
    }

    @Override
    public String getContentType() {
        MediaType mediaType = MediaType.FORM_DATA;
        if (!Objects.isEmpty(mParamEncoding)) {
            mediaType.withCharset(mParamEncoding);
        }
        return mediaType.name();
    }

    @Override
    public void writeTo(OutputStream out) {
        try {
            // if raw body supported
            if (!Objects.isNull(mRawBody)) {
                out.write(mRawBody);
                return ;
            }

            if (CollectionsCompat.isEmpty(this.mParams)) {
                return ;
            }

            byte[] body = NONE_BYTE;
            // 直接写入参数
            String query = XHttpRequestEncoder.parseUrlEncodedParameters(mParams, mParamEncoding);
            if (!Objects.isEmpty(query)) {
                body = query.getBytes(mParamEncoding);
            }
            out.write(body);
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        } finally {
            try {
                out.flush();
            } catch (IOException e) { }
        }
    }

    @Override
    public XHttpUrlEncodedBody build() {
        if (!mUserSetContentType) {
            MediaType mediaType = MediaType.FORM_DATA;
            if (!Objects.isEmpty(mParamEncoding)) {
                mediaType.withCharset(mParamEncoding);
            }
            mContentType = mediaType.name();
        }
        return super.build();
    }

    @Override
    protected int calculateByteSize() {
        return 0;
    }
}
