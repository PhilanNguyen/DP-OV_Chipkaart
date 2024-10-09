package nl.hu.dp.infra.eclipse;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.OVChipkaartDAO;
import nl.hu.dp.domain.Reiziger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class OVChipkaartDAOEclipse implements OVChipkaartDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public OVChipkaartDAOEclipse(EntityManager em) {
        this.emf = Persistence.createEntityManagerFactory("ovchipkaartPU");
        this.em = em;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            em.persist(ovChipkaart);
            return true;

        }catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try{
            em.merge(ovChipkaart);
            return true;

        }catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try{
            OVChipkaart managedOVChipkaart = em.merge(ovChipkaart);
            em.remove(managedOVChipkaart);
            return true;

        }catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            return em.createQuery("SELECT o FROM OVChipkaart o WHERE o.reiziger.id = :reizigerId", OVChipkaart.class)
                    .setParameter("reizigerId", reiziger.getId())
                    .getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            return em.createQuery("SELECT o FROM OVChipkaart o ", OVChipkaart.class).getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }
}
