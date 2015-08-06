package androidrubick.io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/5 0005.
 *
 * @since 1.0
 */
public abstract class IOBuilder {

    public static IOBuilder from(InputStream ins, boolean closeIns) {

    }

    public static IOBuilder from(Reader ins, String charset, boolean closeIns) {

    }

    public static IOBuilder from(File file) {

    }

    private

    public IOBuilder to(OutputStream out, boolean closeOut) {

    }

    public IOBuilder to(Writer out, String charset, boolean closeOut) {

    }

    public IOBuilder to(File file) {

    }

    public void s() {

    }

}
