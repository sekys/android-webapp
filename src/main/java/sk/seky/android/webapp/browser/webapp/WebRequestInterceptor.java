package sk.seky.android.webapp.browser.webapp;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import java.io.IOException;

/**
 * Created by lsekerak on 6. 5. 2016.
 */
public interface WebRequestInterceptor {
    /**
     * @param request
     * @return NULl if dont want intercept
     */
    WebResourceResponse intercept(WebResourceRequest request) throws IOException;
}
