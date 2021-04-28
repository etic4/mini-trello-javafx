package model;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> get_all(int id);
    T save(T t);
    void update(T t);
    void delete(T t);
}
