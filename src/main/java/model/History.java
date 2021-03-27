package model;

// interface implémentée par les objets qui peuvent sauvegarder leur état dans un memento
// C'est le memento (cf interface Memento) qui est responsable du restore
public interface History {
    Memento save(MemType memType);
}
