package model;

public interface History {
    Memento save(MemType memType);
    void restore(Memento memento);

}
