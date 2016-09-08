package sk.seky.android.webapp.browser;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lsekerak on 9. 5. 2016.
 */
public class Preloader {
    private TextView informationTextView;
    private ProgressBar progressBar;
    private AtomicInteger runningSteps;
    private WebView webView;
    private RelativeLayout layout;
    private final int bgColor;

    public Preloader(int steps, int color) {
        this.runningSteps = new AtomicInteger(steps);
        this.bgColor = color;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setInformationTextView(TextView informationTextView) {
        this.informationTextView = informationTextView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    private void hide() {
        informationTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);

        layout.setBackgroundColor(bgColor);
        webView.setBackgroundColor(bgColor);
        runningSteps.set(0);
    }

    public void setStatus(String text) {
        informationTextView.setText(text);
    }

    public boolean isFinished() {
        return this.runningSteps.equals(0);
    }

    public void finish() {
        int value = this.runningSteps.decrementAndGet();
        if (value == 1) {
            this.hide();
        }
    }

    public void setLayout(RelativeLayout layout) {
        this.layout = layout;
    }
}
