package sk.seky.android.webapp.server.provider;

/**
 * Created by lsekerak on 25. 2. 2016.
 * http://jersey.576304.n2.nabble.com/A-sample-FreeMarkerProvider-td2450898.html
 * http://www.citytechinc.com/us/en/blog/2008/12/spring_jax-rs_free.html
 */
public class HtmlModel {
    private final String template;

    public HtmlModel(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HtmlModel{");
        sb.append("template='").append(template).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
