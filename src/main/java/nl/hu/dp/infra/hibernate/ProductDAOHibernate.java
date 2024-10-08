package nl.hu.dp.infra.hibernate;

import nl.hu.dp.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private SessionFactory sessionFactory;
    private Session session;
    public ProductDAOHibernate(Session session) {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try{
            session.persist(product);
            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try{

            session.update(product);

            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try{
            //session.beginTransaction();
            session.delete(product);
            //session.getTransaction().commit();
            return true;

        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            return session.createQuery("SELECT p FROM Product p JOIN p.ovChipkaarten o WHERE o.kaartNummer = :kaartNummer", Product.class)
                    .setParameter("kaartNummer", ovChipkaart.getKaartNummer())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Product> findAll() {
        try {
            return session.createQuery("from Product ", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
