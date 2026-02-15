package org.example.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileRepository<T> {
    private final List<T> data;

    public FileRepository(String filename, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            data = mapper.readValue(new File(filename),
                    mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen: " + filename, e);
        }
    }

    public List<T> findAll() {
        return data;
    }
}
