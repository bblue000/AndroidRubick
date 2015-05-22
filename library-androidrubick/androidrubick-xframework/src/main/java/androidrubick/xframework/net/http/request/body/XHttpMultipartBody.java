package androidrubick.xframework.net.http.request.body;

import androidrubick.collect.CollectionsCompat;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.utils.StandardSystemProperty;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpMultipartBody extends XHttpBody<XHttpMultipartBody> {

    static final String TWO_HYPHENS = "--";
    static final String LINE_END = StandardSystemProperty.LINE_SEPARATOR.value();


    private String mBoundary = "--AndroidRubickFormBoundary1314520";
    protected XHttpMultipartBody() {
        super();
    }

    /**
     * 使用boundary，用于{@link androidrubick.net.MediaType#FORM_DATA_MULTIPART multipart/form-data}
     * 类型的请求。
     */
    public XHttpBody useBoundary(String boundary) {
        mBoundary = Preconditions.checkNotNull(boundary);
        return this;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public XHttpMultipartBody build() {
        // if raw body supported
        // if raw body supported
        if (!Objects.isNull(mRawBody)) {
            return mRequestBuilder;
        }

        mBody = NONE_BYTE;
        if (CollectionsCompat.isEmpty(this.mParams)) {
            return mRequestBuilder;
        }

        return super.build();
    }
}
