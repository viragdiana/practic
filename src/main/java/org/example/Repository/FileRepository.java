package org.example.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Model.HasId;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileRepository<T extends HasId<ID>, ID> implements IRepository<T, ID> {
    private final String filename;
    private final ObjectMapper mapper;
    private final Map<ID, T> storage;
    private final Class<T> type;

    public FileRepository(String filename, Class<T> type) {
        this.filename = filename;
        this.type = type;

        // 1. Instanțiem Mapper-ul
        this.mapper = new ObjectMapper();

        // 2. IMPORTANT: Înregistrăm modulul pentru a putea citi LocalDate (YYYY-MM-DD)
        this.mapper.registerModule(new JavaTimeModule());

        this.storage = new HashMap<>();

        // 3. Încărcăm datele existente la pornire
        loadFromFile();
    }

    @Override
    public void save(T entity) {
        // map.put face update dacă cheia există, sau add dacă nu există
        storage.put(entity.getId(), entity);
        writeToFile();
    }

    @Override
    public void delete(ID id) {
        storage.remove(id);
        writeToFile();
    }

    @Override
    public T findById(ID id) {
        return storage.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    // --- Metode Private pentru lucrul cu fișierul JSON ---

    private void writeToFile() {
        try {
            // Scriem lista de valori în fișier, formatat frumos (PrettyPrinter)
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), new ArrayList<>(storage.values()));
        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea in fisier: " + filename, e);
        }
    }

    private void loadFromFile() {
        File file = new File(filename);

        // Dacă fișierul nu există (prima rulare), nu facem nimic, storage rămâne gol
        if (!file.exists()) return;

        try {
            // TRUC JACKSON: Îi spunem să creeze o Listă de obiecte de tipul 'type' (T)
            List<T> items = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(List.class, type));

            // Populăm Map-ul din memorie
            for (T item : items) {
                storage.put(item.getId(), item);
            }
        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea din fisier: " + filename, e);
        }
    }
}
