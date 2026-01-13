package org.example.Repository;

import org.example.Model.Fahrer;
import org.example.Model.RennenEreignis;

public class FahrerRepository  extends FileRepository<Fahrer, Integer>{
    public FahrerRepository( String filename) {      super(filename, Fahrer.class);}
}

