package sk.seky.android.webapp.browser;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;

/**
 * Created by lsekerak on 3. 7. 2016.
 */
public class NoSuggestionsWebView extends WebView {
    public NoSuggestionsWebView(Context context) {
        super(context);
    }

    public NoSuggestionsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSuggestionsWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection ic = super.onCreateInputConnection(outAttrs);

        //outAttrs.inputType &= ~EditorInfo.TYPE_MASK_VARIATION; /* clear VARIATION type to be able to set new value */
        //outAttrs.inputType |= InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD; /* WEB_PASSWORD type will prevent form suggestions */
        outAttrs.inputType = outAttrs.inputType | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        return ic;
    }


}
