package androidrubick.xframework.net.http.request.body;

import android.util.AndroidRuntimeException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.io.IOUtils;
import androidrubick.net.MediaType;
import androidrubick.utils.Preconditions;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.net.http.request.XHttpRequestEncoder;

/**
 *
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

    private Map<String, File> mFileMap;
    protected XHttpMultipartBody() {
        super();
    }

    /**
     * 添加文件，由框架自行判断
     *
     * @param name 在请求中的字段名称
     * @param file
     * @return
     */
    public XHttpMultipartBody file(String name, File file) {
        return this;
    }

    public XHttpMultipartBody rawField(String name, File file) {
        return this;
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
        return MediaType.FORM_DATA_MULTIPART;
    }

    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        // write parameters
        DataOutputStream dos = new DataOutputStream(out);

        if (!CollectionsCompat.isEmpty(mParams)) {
            for (Map.Entry<String, Object> entry : mParams.entrySet()) {
                writeField(dos, entry.getKey(), entry.getValue());
            }
        }

        dos.writeBytes(TWO_HYPHENS + mBoundary + TWO_HYPHENS + LINE_END);
        try {
            dos.flush();
        } catch (Exception e) {}
        return super.writeGeneratedBody(out);
    }

    protected void writeFile(DataOutputStream dos, String fieldName, File file) {

    }


    private void writeData(DataOutputStream dos, String name, String filename, InputStream is) throws IOException {
        dos.writeBytes(TWO_HYPHENS + mBoundary);
        dos.writeBytes(LINE_END);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + name + "\";"
                + " filename=\"" + filename + "\"");
        dos.writeBytes(LINE_END);

        //added to specify type
        dos.writeBytes("Content-Type: application/octet-stream");
        dos.writeBytes(LINE_END);
        dos.writeBytes("Content-Transfer-Encoding: binary");
        dos.writeBytes(LINE_END);
        dos.writeBytes(LINE_END);
        IOUtils.writeTo(is, dos, false);
        dos.writeBytes(LINE_END);
    }

    protected void writeField(DataOutputStream dos, String key, Object value) throws IOException {
        dos.writeBytes(TWO_HYPHENS + mBoundary);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes("Content-Disposition: form-data; name=\"" + key + "\"", mParamEncoding));
        dos.writeBytes(LINE_END);
        dos.writeBytes(LINE_END);
        dos.write(XHttpRequestEncoder.getBytes(String.valueOf(value), mParamEncoding));
        dos.writeBytes(LINE_END);
    }
}
