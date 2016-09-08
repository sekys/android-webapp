package sk.seky.android.webapp.server;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.seky.android.webapp.browser.webapp.JavascriptBridge;
import sk.seky.android.webapp.server.jaxrs.AnnnotationWalker;
import sk.seky.android.webapp.server.jaxrs.MyRuntimeDelegate;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsekerak on 8. 5. 2016.
 */
public class RequestDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptBridge.class);

    private final Map<String, RequestRecord> endpoints; // path toRecord
    private final ExceptionMapper exceptionHandler;
    private final RequestExecutor executor;
    private final ObjectMapper mapper;

    public RequestDispatcher(ObjectMapper mapper, RequestExecutor executor, ExceptionMapper exceptionHandler) {
        new MyRuntimeDelegate();
        this.executor = executor;
        this.mapper = mapper;
        this.endpoints = new HashMap<>();
        this.exceptionHandler = exceptionHandler;
    }

    public void registerEndpoints(final Object controller) {
        AnnnotationWalker walker = new AnnnotationWalker() {
            @Override
            protected void foundAnnottatedObject(Class<?> clazz, Method method) {
                Path path;
                String listenPath = "";
                path = AnnnotationWalker.findAnnotation(clazz.getAnnotations(), Path.class);
                if (path != null) {
                    listenPath = path.value();
                }
                path = AnnnotationWalker.findAnnotation(method.getAnnotations(), Path.class);
                listenPath += path.value();

                RequestRecord record = new RequestRecord(mapper, controller, method);
                endpoints.put(listenPath, record);
            }
        };
        walker.walktree(controller, Path.class);
    }

    private RequestRecord resolveMethod(Uri url) {
        String path = url.getPath();
        return endpoints.get(path);
    }

    public WebResourceResponse call(WebResourceRequest request) {
        RequestRecord record = resolveMethod(request.getUrl());
        try {
            if (record == null) {
                throw new NotFoundException(request.getUrl().toString());
            }
            LOGGER.debug("{} - {}", request.getUrl(), record.getMethod().getName());
            return executor.exec(record, request);
        } catch (Exception e) {
            WebResourceResponse response = exceptionHandler.resolve(e);
            //LOGGER.debug("Uri {} response", request.getUrl(), response.getStatusCode());
            return response;
        }
    }
}
