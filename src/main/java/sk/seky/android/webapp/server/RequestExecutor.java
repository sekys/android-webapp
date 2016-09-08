package sk.seky.android.webapp.server;

import android.content.res.AssetManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.seky.android.webapp.browser.webapp.WebResourceRequestByJavascript;
import sk.seky.android.webapp.server.jaxrs.Annotations;
import sk.seky.android.webapp.server.provider.HtmlBodyProvider;
import sk.seky.android.webapp.server.provider.JsonBodyProvider;
import sk.seky.android.webapp.server.provider.QueryParamInjector;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lsekerak on 6. 6. 2016.
 */
public class RequestExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestExecutor.class);

    private final HtmlBodyProvider htmlBodyProvider;
    private final JsonBodyProvider jsonBodyProvider;
    private final QueryParamInjector queryParamInjector;

    public RequestExecutor(ObjectMapper mapper, AssetManager assetManager) {
        this.htmlBodyProvider = new HtmlBodyProvider(assetManager);
        this.jsonBodyProvider = new JsonBodyProvider(mapper);
        this.queryParamInjector = new QueryParamInjector(mapper);
    }

    public WebResourceResponse exec(RequestRecord record, WebResourceRequest request) throws Exception {
        try {
            Object[] args = mapArguments(record, request);
            Object result = record.getMethod().invoke(record.getInstance(), args);
            return mapResponse(record, result);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw (Exception) e.getTargetException();
        }
    }

    private Object[] mapArguments(RequestRecord record, WebResourceRequest request) throws IOException {
        POST type = Annotations.findAnnotation(record.getMethodAnnotation(), POST.class);
        if (type != null) { // TODO: skor za zameriat podla mime type
            if (request instanceof WebResourceRequestByJavascript) {
                return jsonBodyProvider.mapArguments(record, (WebResourceRequestByJavascript) request);
            } else {
                // ked controller ocakava POST ale browser poslal GET tak ma byt error
                throw new NotFoundException();
            }
        } else {
            return queryParamInjector.mapArguments(record, request.getUrl());
        }
    }

    private WebResourceResponse mapResponse(RequestRecord record, Object result) throws IOException {
        Produces type = Annotations.findAnnotation(record.getMethodAnnotation(), Produces.class);
        String mimeType = type.value()[0];
        InputStream is;
        if (mimeType.equals(MediaType.TEXT_HTML)) {
            is = htmlBodyProvider.mapResponse(result);
        } else {
            is = jsonBodyProvider.mapResponse(result);
        }
        WebResourceResponse response = new WebResourceResponse(mimeType, "UTF-8", is);
        response.setStatusCodeAndReasonPhrase(200, "ok");
        return response;
    }

}
