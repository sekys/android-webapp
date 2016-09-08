package sk.seky.android.webapp.browser.webapp;

import android.content.res.AssetManager;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by lsekerak on 6. 5. 2016.
 */
public class LoadStaticResources implements WebRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadStaticResources.class);

    private final AssetManager assetManager;
    private final String host;

    public LoadStaticResources(AssetManager assetManager, Uri domain) {
        this.assetManager = assetManager;
        this.host = domain.getHost();
    }

    @Override
    public WebResourceResponse intercept(WebResourceRequest request) {
        Uri url = request.getUrl();
        if (url.getHost().equals(host)) {
            // Access-Control-Allow-Origin: *
            String assetPath = url.getPath();
            InputStream is;
            WebResourceResponse response;
            try {
                is = assetManager.open(assetPath.substring(1));
                String mime = getMimeType(assetPath);
                response = new WebResourceResponse(mime, "UTF-8", is);
                response.setStatusCodeAndReasonPhrase(200, "ok");
                return response;
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                LOGGER.error("error", e);
                try {
                    is = new ByteArrayInputStream("Error".getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    throw new RuntimeException(e1);
                }
                // TODO: zmazat vlastne exception mapovanie
                response = new WebResourceResponse("text/plain", "UTF-8", is);
                response.setStatusCodeAndReasonPhrase(500, "System error");
            }
            return response;
        }
        return null;
    }

    protected String getMimeType(String url) {
        if (url.endsWith(".js")) {
            return "application/javascript";
        }
        if (url.endsWith(".woff2")) {
            return "application/font-woff2";
        }
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (mime == null) {
            LOGGER.warn("{} unkown mime", url);
        }
        return mime;
    }
}
