package model;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> get_all(int id);
    void save(T t);
    void update(T t, String[] params);
    void delete(T t);
}
