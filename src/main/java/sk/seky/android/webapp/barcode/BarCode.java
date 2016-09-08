package sk.seky.android.webapp.barcode;

import java.io.Serializable;

/**
 * Created by lsekerak on 5. 7. 2016.
 */
public class BarCode implements Serializable {
    private String content;
    private String format;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BarCode{");
        sb.append("content='").append(content).append('\'');
        sb.append(", format='").append(format).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
