package org.wahlzeit.utils;

public @interface DesignPatternInstance {
    String purpose();
    String scope();
    String patternName();
    String[] patternParticipants();
    String[] instanceParticipants();
    String roleOfAnnotatedClass();
}
