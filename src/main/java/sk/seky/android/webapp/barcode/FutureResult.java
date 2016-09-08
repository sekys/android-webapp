package sk.seky.android.webapp.barcode;

import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lsekerak on 5. 7. 2016.
 */
public final class FutureResult<T> implements FutureCallback<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FutureResult.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private T result;

    @Override
    public void onSuccess(T result) {
        // main thread
        this.result = result;
        latch.countDown();
    }

    @Override
    public void onFailure(Throwable t) {
        // TODO: dokoncit
    }

    public T get() {
        // background thread
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error("error", e);
            return null;
        }
        return result;
    }
}
