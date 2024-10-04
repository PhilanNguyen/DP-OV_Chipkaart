package nl.hu.dp.infra.hibernate;

import nl.hu.dp.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private SessionFactory sessionFactory;
    private Session session;
    public OVChipkaartDAOHibernate(Session session) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
//        Session session = sessionFactory.openSession();
        try{
//            session.beginTransaction();
            session.persist(ovChipkaart);
//            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
//        Session session = sessionFactory.openSession();
        try{
//            session.beginTransaction();
            session.update(ovChipkaart);
//            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
//        Session session = sessionFactory.openSession();
        try{
//            session.beginTransaction();
            session.delete(ovChipkaart);
//            session.getTransaction().commit();
            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
//        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM OVChipkaart WHERE reiziger.id = :reizigerId", OVChipkaart.class)
                    .setParameter("reizigerId", reiziger.getId())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
//        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from OVChipkaart ", OVChipkaart.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
