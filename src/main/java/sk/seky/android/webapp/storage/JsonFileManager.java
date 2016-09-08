package sk.seky.android.webapp.storage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * Created by lsekerak on 5. 6. 2016.
 * TODO: JavaStorage - Serializovat data rovno cez Javu
 * TODO: SecretStorage - Serializovat data rovno cez Javu pirocm ju pri streame sifrovat klucom
 */
public class JsonFileManager {
    private final ObjectMapper mapper;
    private final File root;

    public JsonFileManager(File root, ObjectMapper mapper) {
        this.mapper = mapper;
        this.root = root;
    }

    public void save(String path, Object data) throws IOException {
        File file = new File(root, path);
        FileOutputStream fos = new FileOutputStream(file);
        mapper.writeValue(fos, data);
    }

    public <T> T load(String path, Class<T> klass) throws IOException {
        File file = new File(root, path);
        try {
            FileInputStream fis = new FileInputStream(file);
            return mapper.readValue(fis, klass);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
