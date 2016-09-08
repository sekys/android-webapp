package sk.seky.android.webapp.server.provider;

import android.content.res.AssetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lsekerak on 25. 2. 2016.
 * http://jersey.576304.n2.nabble.com/A-sample-FreeMarkerProvider-td2450898.html
 * http://www.citytechinc.com/us/en/blog/2008/12/spring_jax-rs_free.html
 */
@Provider
public class HtmlBodyProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlBodyProvider.class);

    private final AssetManager assetManager;

    public HtmlBodyProvider(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public InputStream mapResponse(Object result) throws IOException {
        String template = ((HtmlModel) result).getTemplate();
        InputStream is = assetManager.open(template.substring(1));
        return is;
    }
}
