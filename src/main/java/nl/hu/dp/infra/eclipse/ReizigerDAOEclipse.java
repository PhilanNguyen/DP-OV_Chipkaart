package nl.hu.dp.infra.eclipse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import nl.hu.dp.domain.Reiziger;
import nl.hu.dp.domain.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOEclipse implements ReizigerDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ReizigerDAOEclipse(EntityManager em) {
        this.emf = Persistence.createEntityManagerFactory("ovchipkaartPU");
        this.em = em;
    }


    @Override
    public boolean save(Reiziger reiziger) {
        try{
            em.persist(reiziger);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            em.merge(reiziger);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            em.remove(reiziger);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            return em.find(Reiziger.class, id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        try {
            return em.createQuery("SELECT r FROM Reiziger r WHERE r.geboortedatum = :date", Reiziger.class)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            return em.createQuery("from Reiziger", Reiziger.class).getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }
}
