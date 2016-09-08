package sk.seky.android.webapp.browser.webapp;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lsekerak on 6. 5. 2016.
 */
public abstract class AjaxProxyInterceptor implements WebRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxProxyInterceptor.class);

    @Override
    public WebResourceResponse intercept(WebResourceRequest request) throws IOException {
        Map<String, String> headers = request.getRequestHeaders();
        if (!headers.containsKey("X-Requested-With")) {
            return null;
        }
        return interceptAjaxRequest(request);
    }

    public abstract WebResourceResponse interceptAjaxRequest(WebResourceRequest request) throws IOException;
}
