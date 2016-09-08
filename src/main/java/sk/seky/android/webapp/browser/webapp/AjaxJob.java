package sk.seky.android.webapp.browser.webapp;

import android.os.AsyncTask;
import android.webkit.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lsekerak on 8. 5. 2016.
 */
public class AjaxJob extends AsyncTask<String, Void, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxJob.class);

    private final WebView webView;
    private final JavascriptBridge bridge;
    private final int jobId;

    public AjaxJob(WebView webView, JavascriptBridge bridge, int jobId) {
        this.webView = webView;
        this.bridge = bridge;
        this.jobId = jobId;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String url = params[1];
        String body = params[2];
        String result = bridge.callHttpMethod(method, url, body);
        StringBuffer sb = new StringBuffer(result.length() + 100);
        sb.append("javascript:ApiClient.XHR_CALLBACKS[");
        sb.append(Integer.toString(jobId));
        sb.append("]('");
        sb.append(result.replaceAll("'", "\\\\'"));
        sb.append("');");
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        //LOGGER.debug(result);
        webView.loadUrl(result);
    }
}
