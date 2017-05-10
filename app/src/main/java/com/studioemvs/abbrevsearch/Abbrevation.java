package com.studioemvs.abbrevsearch;

/**
 * Created by vijsu on 09-05-2017.
 */

public class Abbrevation {
    public final String term;
    public final String definition;

    public Abbrevation(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
