package nl.hu.ipass.infra.hibernate;

import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private SessionFactory sessionFactory;
    public ReizigerDAOHibernate() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean save(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.persist(reiziger);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(reiziger);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Reiziger.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from Reiziger where geboortedatum = :date", Reiziger.class)
                    .setParameter("date", date)
                    .list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reiziger> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from Reiziger", Reiziger.class).list();
        } finally {
            session.close();
        }
    }
}
