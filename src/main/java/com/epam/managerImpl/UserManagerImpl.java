package com.epam.managerImpl;

import java.util.List;

public interface UserManagerImpl<T, E>{

    List<T> getAll();

    T getById(E id);

    void create(T object);

    void update (T object);

    void delete(E id);


}
