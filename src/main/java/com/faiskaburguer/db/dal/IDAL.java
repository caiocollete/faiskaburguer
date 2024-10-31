package com.faiskaburguer.db.dal;

import java.sql.SQLException;
import java.util.List;

public interface IDAL <T>{
    public boolean gravar(T entidade);
    public boolean alterar(T entidade);
    public boolean apagar(T entidade);

    public T get(int id) throws SQLException;
    public List<T> get(String filtro);
}
