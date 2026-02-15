package org.example.Repository;

import org.example.Model.RennenEreignis;

public class RennenEreignisRepository extends FileRepository<RennenEreignis> {
    public RennenEreignisRepository(String filename) {
        super(filename, RennenEreignis.class);
    }
}
