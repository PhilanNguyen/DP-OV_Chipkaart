package nl.hu.ipass.application;

import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;
import nl.hu.ipass.infra.dao.ReizigerDAOPsql;

import nl.hu.ipass.infra.hibernate.ReizigerDAOHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection conn = DatabaseConnection.getConnection();

        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(conn);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();

        testReizigerDAO(reizigerDAOHibernate);
        //testReizigerDAO(reizigerDAO);

        // Sluit de session factory als alles klaar is
        sessionFactory.close();


    }
    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(79, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Test de update-functionaliteit
        System.out.println("[Test] Update de achternaam van reiziger met id 79");
        sietske.setAchternaam("Verhoeven");
        rdao.update(sietske);
        Reiziger updatedReiziger = rdao.findById(79);
        System.out.println("[Test] Geüpdatete reiziger: " + updatedReiziger + "\n");


        // Test de findByGbdatum-functionaliteit
        System.out.print("[Test] Vind reiziger met geboortedatum 1981-03-14.");
        List<Reiziger> reizigersGb = rdao.findByGbdatum(java.sql.Date.valueOf(gbdatum));

        System.out.println("[Test] ReizigerDAO.findGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigersGb) {
            System.out.println(r);

        }

    }

}