package org.example.Repository;

import org.example.Model.Fahrer;

public class FahrerRepository extends FileRepository<Fahrer> {
    public FahrerRepository(String filename) {
        super(filename, Fahrer.class);
    }
}
