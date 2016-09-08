package sk.seky.android.webapp.browser.webapp;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.seky.android.webapp.server.RequestDispatcher;

import java.io.IOException;

/**
 * Created by lsekerak on 6. 6. 2016.
 */
public class ServerOriginInterceptor implements WebRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadStaticResources.class);

    private final RequestDispatcher requestDispatcher;
    private final String host;

    public ServerOriginInterceptor(RequestDispatcher requestDispatcher, Uri domain) {
        this.requestDispatcher = requestDispatcher;
        this.host = domain.getHost();
    }

    @Override
    public WebResourceResponse intercept(WebResourceRequest request) throws IOException {
        Uri url = request.getUrl();
        if (url.getHost().equals(this.host)) {
            return requestDispatcher.call(request);
        }
        return null;
    }
}
