package nl.hu.ipass.infra.hibernate;

import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Connection conn;
    public ReizigerDAOHibernate(Connection conn) {
        this.conn = conn;
    }
    @Override
    public boolean save(Reiziger reiziger) {
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
