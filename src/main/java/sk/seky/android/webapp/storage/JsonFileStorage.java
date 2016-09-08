package sk.seky.android.webapp.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lsekerak on 5. 6. 2016.
 */
public class JsonFileStorage<T> implements IProvider<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileStorage.class);

    private final JsonFileManager manager;
    private final Class<T> klass;
    private final String path;

    public JsonFileStorage(JsonFileManager manager, Class<T> klass) {
        this.manager = manager;
        this.klass = klass;
        path = klass.getSimpleName();
    }

    @Override
    public T get() {
        try {
            return manager.load(path, klass);
        } catch (IOException e) {
            LOGGER.error(path, e);
            return null;
        }
    }

    @Override
    public void set(T data) {
        try {
            manager.save(path, klass);
        } catch (IOException e) {
            LOGGER.error("UserCredentials", e);
            return;
        }
    }
};
