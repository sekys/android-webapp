package sk.seky.android.webapp.browser.webapp;

import android.webkit.WebResourceRequest;

/**
 * Created by lsekerak on 6. 6. 2016.
 */
public interface WebResourceRequestByJavascript extends WebResourceRequest {
    String getBody();
}
