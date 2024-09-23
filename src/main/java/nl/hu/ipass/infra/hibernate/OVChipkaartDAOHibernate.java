package nl.hu.ipass.infra.hibernate;

import nl.hu.ipass.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private SessionFactory sessionFactory;
    public OVChipkaartDAOHibernate() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.persist(ovChipkaart);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(ovChipkaart);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(ovChipkaart);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM OVChipkaart WHERE reizigerId = :reizigerId", OVChipkaart.class)
                    .setParameter("reizigerId", reiziger.getId())
                    .list();
        } finally {
            session.close();
        }
    }
    @Override
    public List<OVChipkaart> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from OVChipkaart ", OVChipkaart.class).list();
        } finally {
            session.close();
        }
    }
}
