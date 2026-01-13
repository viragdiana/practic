package org.example.Repository;

import org.example.Model.Strafe;

public class StrafeRepository extends FileRepository<Strafe, Integer>{
    public StrafeRepository(String filename) {
        super(filename, Strafe.class);
    }
}
