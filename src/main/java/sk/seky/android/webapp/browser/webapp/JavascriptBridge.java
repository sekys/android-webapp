package sk.seky.android.webapp.browser.webapp;

import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.seky.android.webapp.server.RequestDispatcher;

/**
 * Created by lsekerak on 7. 5. 2016.
 */
public final class JavascriptBridge {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptBridge.class);

    private final RequestDispatcher requestDispatcher;
    private final WebView webView;
    private final JavascriptMapper mapper;

    public JavascriptBridge(ObjectMapper mapper, RequestDispatcher requestDispatcher, WebView webView) {
        this.requestDispatcher = requestDispatcher;
        this.webView = webView;
        this.mapper = new JavascriptMapper(mapper);
    }

    @JavascriptInterface
    public String callHttpMethod(String method, String url, String body) {
        WebResourceRequest request = mapper.deserialize(method, url, body);
        WebResourceResponse response = requestDispatcher.call(request);
        return mapper.serialize(response);
    }

    @JavascriptInterface
    public void callAsynHttpMethod(int jobId, String method, String url, String body) {
        new AjaxJob(webView, this, jobId).execute(method, url, body);
    }
}
