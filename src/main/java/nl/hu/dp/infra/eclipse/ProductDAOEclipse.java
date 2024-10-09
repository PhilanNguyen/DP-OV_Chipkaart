package nl.hu.dp.infra.eclipse;

import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.OVChipkaart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import nl.hu.dp.domain.ProductDAO;

import java.util.List;

public class ProductDAOEclipse implements ProductDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ProductDAOEclipse(EntityManager em) {
        this.emf = Persistence.createEntityManagerFactory("ovchipkaartPU");
        this.em = em;
    }

    @Override
    public boolean save(Product product) {
        try {
//            em.getTransaction().begin();
            em.persist(product);
//            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try {
            em.merge(product);
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try {
            em.remove(product);
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            return em.createQuery("SELECT p FROM Product p JOIN p.ovChipkaarten o WHERE o.kaartNummer = :kaartNummer", Product.class)
                    .setParameter("kaartNummer", ovChipkaart.getKaartNummer())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
