package nl.hu.dp.application;

import nl.hu.dp.domain.*;
import nl.hu.dp.infra.dao.AdresDAOPsql;
import nl.hu.dp.infra.dao.OVChipkaartDAOPsql;
import nl.hu.dp.infra.dao.ProductDAOPsql;
import nl.hu.dp.infra.dao.ReizigerDAOPsql;

import nl.hu.dp.infra.hibernate.AdresDAOHibernate;
import nl.hu.dp.infra.hibernate.OVChipkaartDAOHibernate;
import nl.hu.dp.infra.hibernate.ProductDAOHibernate;
import nl.hu.dp.infra.hibernate.ReizigerDAOHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException {

        Connection conn = DatabaseConnection.getConnection();

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(conn);

        ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate(session);

        AdresDAOPsql adresDAO = new AdresDAOPsql(conn);

        AdresDAOHibernate adresDAOHibernate = new AdresDAOHibernate();

        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(conn);

        OVChipkaartDAOHibernate ovChipkaartDAOHibernate = new OVChipkaartDAOHibernate(session);

        ProductDAOPsql productDAO = new ProductDAOPsql(conn);

        ProductDAOHibernate productDAOHibernate = new ProductDAOHibernate(session);

        //testReizigerDAO(reizigerDAO);
        //testReizigerDAO(reizigerDAOHibernate);

        //testAdresDAO(adresDAO,reizigerDAO);
        //testAdresDAO(adresDAOHibernate,reizigerDAOHibernate);

        //testOVChipkaartDAO(ovChipkaartDAO, reizigerDAO);
        //testOVChipkaartDAO(ovChipkaartDAOHibernate, reizigerDAOHibernate);

        testProductDAO(productDAO, ovChipkaartDAO, reizigerDAO);
        //testProductDAO(productDAOHibernate, ovChipkaartDAOHibernate, reizigerDAOHibernate);


        session.getTransaction().commit();


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
        int reizigerId = 78;
        String gbdatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(reizigerId, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(reiziger); // Voeg de reiziger toe als deze nog niet bestaat

        // Maak een nieuw adres aan
        int adresId = 6;
        Adres nieuwAdres = new Adres(adresId, "Hoofdstraat", "1", "1234AB", "Amsterdam", reiziger);

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(nieuwAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        nieuwAdres.setStraat("BijgewerkteStraat");
        adao.update(nieuwAdres);
        System.out.println("[Test] Geüpdatet adres: " + adao.findByReiziger(rdao.findById(78)));
        System.out.println();

//        // Test de findByReiziger-functionaliteit
//        System.out.print("[Test] Vind adres voor reiziger met id " + reizigerId + "\n");
//        Adres gevondenAdres = adao.findByReiziger(reiziger);
//        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres: " + gevondenAdres);
//


        //TEST de delete als reiziger wordt gedelete en adres dan ook wordt gedelete
        System.out.print("[Test] Vind adres voor reiziger met id " + reizigerId + "\n");
        Adres gevondenAdres2 = adao.findByReiziger(reiziger);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres: " + gevondenAdres2);
        System.out.print("[Test] Verwijder reiziger met id " + reizigerId + "\n");
        rdao.delete(rdao.findById(reizigerId));
        System.out.print("[Test] Vind adres voor reiziger met id " + reizigerId + "\n");
        Adres gevondenAdres3 = adao.findByReiziger(reiziger);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres: " + gevondenAdres3);
        System.out.println();

        // Test de delete-functionaliteit
        System.out.print("[Test] Verwijder adres met id " + adresId + "\n");
        adao.delete(nieuwAdres);
        adressen = adao.findAll();
        System.out.println("Na delete heeft AdresDAO.findAll() nu " + adressen.size() + " adressen\n");

    }

    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle ovChipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende adressen:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        int reizigerId = 78; // Zorg ervoor dat deze reiziger bestaat
        String gbdatum = "1981-03-14";
        Reiziger reiziger = new Reiziger(reizigerId, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(reiziger); // Voeg de reiziger toe als deze nog niet bestaat

        // Maak een nieuw adres aan
        int kaartNummer = 99999; // Zorg ervoor dat dit een uniek id is
        String datum = "2022-03-31";
        OVChipkaart nieuwOV = new OVChipkaart(kaartNummer, java.sql.Date.valueOf(gbdatum), 2, 35.50, reiziger);

        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " ovchipkaarten, na OVChipkaartDAO.save() ");
        odao.save(nieuwOV);
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " ovchipkaarten\n");

        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende adressen:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        nieuwOV.setKlasse(1);
        odao.update(nieuwOV);
        System.out.println("[Test] Geüpdatet ovChipkaart: " + odao.findByReiziger(rdao.findById(78)));

        // Test de findByReiziger-functionaliteit
        System.out.print("[Test] Vind OV voor reiziger met id " + reizigerId + ".");
        List<OVChipkaart> gevondenOVChipkaart = odao.findByReiziger(reiziger);
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft het volgende OVChipkaart: " + gevondenOVChipkaart);

        // Test de delete-functionaliteit
        System.out.print("[Test] Verwijder ovchipkaart met kaartnummer " + kaartNummer);
        odao.delete(nieuwOV);
        ovChipkaarten = odao.findAll();
        System.out.println("Na delete heeft OVChipkaartDAO.findAll() nu " + ovChipkaarten.size() + " ovchipkaarten\n");

        //TEST de delete als reiziger wordt gedelete en alle ovchipkaarten dan ook wordt gedelete
        System.out.print("[Test] Vind alle kaarten voor reiziger met id " + reizigerId + "\n");
        List<OVChipkaart> gevondenOVChipkaarten = odao.findByReiziger(reiziger);
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft het volgende adres: " + gevondenOVChipkaarten);

        System.out.print("[Test] Verwijder reiziger met id " + reizigerId + "\n");
        rdao.delete(rdao.findById(reizigerId));
        System.out.print("[Test] Vind alle kaarten voor reiziger met id " + reizigerId + "\n");
        List<OVChipkaart> gevondenOVChipkaarten2 = odao.findByReiziger(reiziger);
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft het volgende adres: " + gevondenOVChipkaarten2);

    }

    private static void testProductDAO(ProductDAO pdao, OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een nieuwe OVChipkaart en product aan en persisteer deze in de database
        int kaartNummer = 98765; // Zorg ervoor dat dit een uniek id is
        String geldigTot = "2025-12-31";

        Reiziger reiziger = new Reiziger(12, "J", "de", "Vries", java.sql.Date.valueOf("1980-01-01"));
        rdao.save(reiziger);

        OVChipkaart nieuwOV = new OVChipkaart(kaartNummer, java.sql.Date.valueOf(geldigTot), 2, 50.00, reiziger);
        odao.save(nieuwOV);

        int productNummer = 7; // Zorg ervoor dat dit een uniek id is
        Product nieuwProduct = new Product(productNummer, "NS Flex", "Reis op rekening met korting", 5.00);

        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        pdao.save(nieuwProduct);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");

        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        // Test de associatie tussen OVChipkaart en Product
        System.out.println("[Test] Voeg OVChipkaart toe aan Product.");
        nieuwProduct.addOVChipkaart(nieuwOV);
        pdao.update(nieuwProduct);

        List<Product> productenVoorOVChipkaart = pdao.findByOVChipkaart(nieuwOV);
        System.out.println("[Test] ProductDAO.findByOVChipkaart() geeft de volgende producten voor OVChipkaart " + kaartNummer + ":");
        if (!productenVoorOVChipkaart.isEmpty()){
            for (Product p : productenVoorOVChipkaart) {
                System.out.println(p);
            }
            System.out.println();
        }


        // Test update functionaliteit van product
        nieuwProduct.setPrijs(7.50);
        pdao.update(nieuwProduct);
        System.out.println("[Test] Geüpdatet producten: " + pdao.findByOVChipkaart(nieuwOV));

        // Test de delete-functionaliteit
        System.out.print("[Test] Verwijder product met product_nummer " + productNummer);
        pdao.delete(nieuwProduct);
        producten = pdao.findAll();
        System.out.println("Na delete heeft ProductDAO.findAll() nu " + producten.size() + " producten\n");

        // Test de associatie met OVChipkaart na delete
        System.out.print("[Test] Producten geassocieerd met OVChipkaart na product delete: ");
        List<Product> productenNaDelete = pdao.findByOVChipkaart(nieuwOV);
        System.out.println(productenNaDelete.size() + " producten geassocieerd met OVChipkaart.\n");


        // Voeg de OVChipkaart toe aan de database (als deze nog niet bestaat)

        // Test verwijdering van OVChipkaart en kijken of de associatie met producten wordt verwijderd
        System.out.print("[Test] Verwijder OVChipkaart met kaartnummer " + kaartNummer);
        odao.delete(nieuwOV);
        productenVoorOVChipkaart = pdao.findByOVChipkaart(nieuwOV);
        System.out.println("Na OVChipkaart delete heeft ProductDAO.findByOVChipkaart() nu " + productenVoorOVChipkaart.size() + " producten geassocieerd met de OVChipkaart.");

//        session.getTransaction().commit();

    }


}