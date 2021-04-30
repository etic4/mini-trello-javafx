package model;

public interface History<T> {
    Memento<T> getMemento(MemType memType);
    Memento<T> restore(Memento<T> memento);

    default MemType getRestoreMemType(MemType memType) {
        var newMemtype = memType;

        if (memType == MemType.ADD) {
            newMemtype = MemType.DELETE;
        } else if (memType == MemType.DELETE) {
            newMemtype = MemType.ADD;
        }

        return newMemtype;
    }

    default Memento<T> getNewMemento(MemType memType) {
        var newMemType = getRestoreMemType(memType);
        return getMemento(newMemType);
    }
}
