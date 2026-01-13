package org.example.Repository;

import org.example.Model.Fahrer;
import org.example.Model.RennenEreignis;
import org.example.Model.Strafe;

public class RennenEreignisRepository extends FileRepository<RennenEreignis, Integer>{
    public RennenEreignisRepository( String filename) {      super(filename, RennenEreignis.class);}
}
