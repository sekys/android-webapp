package sk.seky.android.webapp.barcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsekerak on 14. 6. 2016.
 * https://examples.javacodegeeks.com/android/android-barcode-and-qr-scanner-example/
 * http://stackoverflow.com/questions/16080181/qr-code-reading-with-camera-android
 * https://github.com/zxing/zxing/wiki/Scanning-Via-Intent
 */
public final class BarcodeScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeScanner.class);

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private Activity act;
    private Integer actualRequest;
    private Map<Integer, FutureCallback<BarCode>> callbacks;

    public BarcodeScanner(Activity act) {
        this.act = act;
        this.actualRequest = 0;
        callbacks = new HashMap<>();
    }

    public void scan(FutureCallback<BarCode> callback) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            actualRequest++;
            callbacks.put(actualRequest, callback);
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            act.startActivityForResult(intent, actualRequest);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog("No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    protected AlertDialog showDialog(CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void handleResult(int requestCode, int resultCode, Intent intent) {
        FutureCallback<BarCode> callback = callbacks.remove(requestCode);
        if (callback == null) {
            LOGGER.warn("Not found callback for {}", requestCode);
            return;
        }
        if (intent == null) {
            callback.onSuccess(null);
            return;
        }

        BarCode result = new BarCode();
        result.setContent(intent.getStringExtra("SCAN_RESULT"));
        result.setFormat(intent.getStringExtra("SCAN_RESULT_FORMAT"));

        LOGGER.debug("handleResult {}", result);
        Toast toast = Toast.makeText(act,
                "Content:" + Strings.nullToEmpty(result.getContent()) + " Format:" + Strings.nullToEmpty(result.getFormat()),
                Toast.LENGTH_LONG
        );
        toast.show();
        callback.onSuccess(result);
    }
}
