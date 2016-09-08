package sk.seky.android.webapp.browser;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.seky.android.webapp.browser.webapp.WebRequestInterceptor;

import java.io.IOException;
import java.util.List;

/**
 * Created by lsekerak on 6. 5. 2016.
 */
public class CustomWebViewClient extends WebViewClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebViewClient.class);

    private final List<WebRequestInterceptor> interceptors;

    public CustomWebViewClient(List<WebRequestInterceptor> interceptors) {
        this.interceptors = interceptors;
        //this.interceptors = new ArrayList<>();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //LOGGER.debug("{}", url);
        // false- ponechame funkcne javascript redirect a klasicke requesty - server to spracuje
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        // Can intercept?
        try {
            WebResourceResponse response;
            for (WebRequestInterceptor in : interceptors) {
                response = in.intercept(request);
                if (response != null) {
                    return response;
                }
            }
            // Casto nechceme dovolit aby javascript sa pripajal na net.
            // ServerOrigin zachyti linky pod domenou, a vrati 404, ostatne linky su prepustene dalej prehliadacom
            return super.shouldInterceptRequest(view, request);
        } catch (IOException e) {
            LOGGER.error("interceptor error", e);
            return null;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }
}
