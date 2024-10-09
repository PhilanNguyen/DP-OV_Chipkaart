package nl.hu.dp.infra.eclipse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.AdresDAO;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class AdresDAOEclipse implements AdresDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public AdresDAOEclipse(EntityManager em) {
        this.emf = Persistence.createEntityManagerFactory("ovchipkaartPU");
        this.em = em;
    }

    @Override
    public boolean save(Adres adres) {
        try{
            em.persist(adres);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try{
            em.merge(adres);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try{
            em.remove(adres);
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            return em.createQuery( "SELECT a FROM Adres a WHERE a.reiziger.id = :reizigerId", Adres.class)
                    .setParameter("reizigerId", reiziger.getId())
                    .getSingleResult();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Adres> findAll() {
        try {
            return em.createQuery("SELECT a FROM Adres a", Adres.class).getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }
}

