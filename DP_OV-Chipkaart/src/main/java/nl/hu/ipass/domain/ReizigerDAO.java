package nl.hu.ipass.domain;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(Date date);
    List<Reiziger> findAll();
}