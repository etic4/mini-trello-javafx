package model;

public interface History<T> {
    Memento<T> save(MemType memType);
    void restore(Memento<T> memento);
}
