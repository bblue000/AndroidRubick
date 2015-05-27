package androidrubick.xframework.net.http.request.body;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;
import androidrubick.io.IOUtils;
import androidrubick.io.MimeUtils;
import androidrubick.net.HttpHeaderValues;
import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.net.http.request.XHttpRequestEncoder;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * content type 为<code>multipart/form-data</code>的
 *
 * 请求体
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpMultipartBody extends XHttpBody<XHttpMultipartBody> {

    protected static final String TWO_HYPHENS = "--";
    protected static final String LINE_END = StandardSystemProperty.LINE_SEPARATOR.value();
    protected String mBoundary = "--AndroidRubickFormBoundary1314520";

    protected Map<String, Object> mDataMap;
    protected XHttpMultipartBody() {
        super();
    }

    /**
     * 添加文件类型的请求项，自行判断MIME type
     *
     * @param name 在请求中的字段名称
     */
    public XHttpMultipartBody file(String name, File file) {
        Preconditions.checkNotNull(file);
        prepareDataMap();
        mDataMap.put(name, file);
        return this;
    }

    /**
     * 该方法设置的请求项的content type将设置为application/octet-stream
     */
    public XHttpMultipartBody rawField(String name, byte[] data) {
        if (Objects.isNull(data)) {
            data = NONE_BYTE;
        }
        prepareDataMap();
        mDataMap.put(name, data);
        return this;
    }

    private void prepareDataMap() {
        if (null == mDataMap) {
            mDataMap = MapBuilder.newHashMap(4).build();
        }
    }

    /**
     * 使用boundary，用于{@link androidrubick.net.MediaType#FORM_DATA_MULTIPART multipart/form-data}
     * 类型的请求。
     */
    public XHttpMultipartBody useBoundary(String boundary) {
        mBoundary = Preconditions.checkNotNull(boundary);
        return this;
    }

    @Override
    protected MediaType rawContentType() {
        return MediaType.FORM_DATA_MULTIPART.withParameter(HttpHeaderValues.P_BOUNDARY, mBoundary);
    }

    @Configurable
    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        if (CollectionsCompat.isEmpty(mParams) && CollectionsCompat.isEmpty(mDataMap)) {
            return false;
        }
        // write parameters
        DataOutputStream dos = new DataOutputStream(out);

        if (!CollectionsCompat.isEmpty(mParams)) {
            for (Map.Entry<String, Object> entry : mParams.entrySet()) {
                writeField(dos, entry.getKey(), entry.getValue());
            }
        }

        if (!CollectionsCompat.isEmpty(mDataMap)) {
            for (Map.Entry<String, Object> entry : mDataMap.entrySet()) {
                Object value = Objects.getOr(entry.getValue(), NONE_BYTE);
                if (entry.getValue() instanceof File) {
                    writeFile(dos, entry.getKey(), Objects.getAs(value, File.class));
                } else if (entry.getValue() instanceof byte[]) {
                    writeData(dos, entry.getKey(), Objects.getAs(value, byte[].class));
                } else {
                    writeField(dos, entry.getKey(), value.toString());
                }
            }
        }

        // write end
        dos.writeBytes(TWO_HYPHENS + mBoundary + TWO_HYPHENS + LINE_END);
        try {
            dos.flush();
        } catch (Exception e) {}
        return super.writeGeneratedBody(out);
    }

    @Configurable
    protected void writeFile(DataOutputStream dos, String fieldName, File file) throws Exception {
        final String filename = file.getName();
        final InputStream is = new FileInputStream(file);
        dos.writeBytes(TWO_HYPHENS + mBoundary);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes("Content-Disposition: form-data; name=\""
                + fieldName + "\";"
                + " filename=\"" + filename + "\"", mParamEncoding));
        dos.writeBytes(LINE_END);

        //added to specify type
        dos.writeBytes("Content-Type: " + guessMediaType(filename));
        dos.writeBytes(LINE_END);
        dos.writeBytes("Content-Transfer-Encoding: binary");
        dos.writeBytes(LINE_END);
        dos.writeBytes(LINE_END);
        IOUtils.writeTo(is, dos, false);
        IOUtils.close(is);
        dos.writeBytes(LINE_END);
    }

    @Configurable
    protected void writeData(DataOutputStream dos, String name, byte[] data) throws IOException {
        dos.writeBytes(TWO_HYPHENS + mBoundary);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes("Content-Disposition: form-data; name=\""
                + name + "\"", mParamEncoding));
        dos.writeBytes(LINE_END);

        //added to specify type
        dos.writeBytes("Content-Type: " + guessMediaType(null));
        dos.writeBytes(LINE_END);
        dos.writeBytes("Content-Transfer-Encoding: binary");
        dos.writeBytes(LINE_END);
        dos.writeBytes(LINE_END);
        dos.write(data);
        dos.writeBytes(LINE_END);
    }

    @Configurable
    protected String guessMediaType(String filename) {
        String type = null;
        if (!Objects.isEmpty(filename)) {
            type = MimeUtils.guessMimeTypeByGetExtensionFromSrc(filename);
        }
        return Objects.getOr(type, MediaType.OCTET_STREAM.name());
    }

    @Configurable
    protected void writeField(DataOutputStream dos, String key, Object value) throws IOException {
        dos.writeBytes(TWO_HYPHENS + mBoundary);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes("Content-Disposition: form-data; name=\"" + key + "\"", mParamEncoding));
        dos.writeBytes(LINE_END);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes(value, mParamEncoding));
        dos.writeBytes(LINE_END);
    }
}
