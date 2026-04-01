package com.doctorsuggester.dao;

import java.util.List;

public interface DAOInterface<T> {
    boolean insert(T obj);
    T getById(int id);
    List<T> getAll();
    boolean update(T obj);
    boolean delete(int id);
}