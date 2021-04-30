package main;

import model.DaoBackendType;

public class Config {
    public static DaoBackendType BACKEND = DaoBackendType.SQLITE;
    public static int UNDO_REDO_MAX = 10;
}
