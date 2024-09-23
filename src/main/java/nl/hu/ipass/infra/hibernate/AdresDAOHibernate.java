package nl.hu.ipass.infra.hibernate;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.AdresDAO;
import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private SessionFactory sessionFactory;
    public AdresDAOHibernate() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean save(Adres adres) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.persist(adres);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(adres);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(adres);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Adres WHERE reizigerId = :reizigerId";
            Query<Adres> query = session.createQuery(hql, Adres.class);
            query.setParameter("reizigerId", reiziger.getId());
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Adres> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from Adres", Adres.class).list();
        } finally {
            session.close();
        }
    }
}
