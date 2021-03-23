package kazmierczak.jan.config.converter.generic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kazmierczak.jan.config.converter.exception.JsonConverterException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {
    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    public void toJson(final T item) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)) {
            fileWriter.write(gson.toJson(item));
        } catch (Exception e) {
            throw new JsonConverterException(e.getMessage());
        }
    }

    public Optional<T> fromJson() {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonConverterException(e.getMessage());
        }
    }
}