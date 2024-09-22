package nl.hu.ipass.application;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.AdresDAO;
import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;
import nl.hu.ipass.infra.dao.AdresDAOPsql;
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

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(conn);

        ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();

        AdresDAOPsql AdresDAO = new AdresDAOPsql(conn);

        //testReizigerDAO(reizigerDAOHibernate);
        //testReizigerDAO(reizigerDAO);

        testAdresDAO(AdresDAO,reizigerDAO);
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

        int id = 78;

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(id, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Test de update-functionaliteit
        System.out.println("[Test] Update de achternaam van reiziger met id 79");
        sietske.setAchternaam("Verhoeven");
        rdao.update(sietske);
        Reiziger updatedReiziger = rdao.findById(id);
        System.out.println("[Test] Geüpdatete reiziger: " + updatedReiziger + "\n");


        // Test de findByGbdatum-functionaliteit
        System.out.print("[Test] Vind reiziger met geboortedatum 1981-03-14.");
        List<Reiziger> reizigersGb = rdao.findByGbdatum(java.sql.Date.valueOf(gbdatum));

        System.out.println("[Test] ReizigerDAO.findGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigersGb) {
            System.out.println(r);
        }
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        int reizigerId = 78; // Zorg ervoor dat deze reiziger bestaat
        String gbdatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(reizigerId, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(reiziger); // Voeg de reiziger toe als deze nog niet bestaat

        // Maak een nieuw adres aan
        int adresId = 6; // Zorg ervoor dat dit een uniek id is
        Adres nieuwAdres = new Adres(adresId, "Hoofdstraat", "1", "1234AB", "Amsterdam", reiziger.getId());

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(nieuwAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        nieuwAdres.setStraat("BijgewerkteStraat");
        adao.update(nieuwAdres);
        System.out.println("[Test] Geüpdatet adres: " + adao.findByReiziger(rdao.findById(78)));

        // Test de findByReiziger-functionaliteit
        System.out.print("[Test] Vind adres voor reiziger met id " + reizigerId + ".");
        Adres gevondenAdres = adao.findByReiziger(reiziger);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres: " + gevondenAdres);

        // Test de delete-functionaliteit
        System.out.print("[Test] Verwijder adres met id " + adresId);
        adao.delete(nieuwAdres);
        adressen = adao.findAll();
        System.out.println("Na delete heeft AdresDAO.findAll() nu " + adressen.size() + " adressen\n");
    }

}