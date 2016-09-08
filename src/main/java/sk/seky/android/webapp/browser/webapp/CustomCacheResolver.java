package sk.seky.android.webapp.browser.webapp;

import android.content.Context;
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
public abstract class CustomCacheResolver implements WebRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCacheResolver.class);
    protected File cache;

    public CustomCacheResolver(Context context) {
        File internalStorage = context.getFilesDir();
        cache = new File(internalStorage, this.getClass().getSimpleName());
        cache.mkdir();
    }

    protected static long copy(InputStream from, OutputStream to) throws IOException {
        byte[] buf = new byte[0x1000];  // 4K
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }

    protected abstract boolean canCache(WebResourceRequest request);

    @Override
    public WebResourceResponse intercept(WebResourceRequest request) {
        if (!canCache(request)) {
            return null;
        }
        Uri url = request.getUrl();
        String path = url.getPath();
        try {
            InputStream is = load(path); // synchronny zapis maybe?
            if (is == null) {
                is = download(url); // SocketTimeoutException  after 10000ms
                is = save(path, is);
            }
            return new WebResourceResponse(getMimeType(path), "UTF-8", is);
        } catch (IOException e) {
            LOGGER.error("error", e);
            return null;
        }
    }

    protected abstract InputStream download(Uri url) throws IOException;

    protected InputStream load(String path) {
        File file = new File(cache, path);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    protected InputStream save(String path, InputStream is) throws IOException {
        int index = path.lastIndexOf('/');
        String subfolders = path.substring(0, index);
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        File folder = new File(cache, subfolders);
        folder.mkdirs();
        File file = new File(folder, fileName);

        FileOutputStream fos = new FileOutputStream(file);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            copy(is, buffer);
            fos.write(buffer.toByteArray());
            return new ByteArrayInputStream(buffer.toByteArray());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
    }

    protected String getMimeType(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
