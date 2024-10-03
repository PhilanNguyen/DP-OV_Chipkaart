package nl.hu.dp.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int productNummer;

    @Column(name = "naam")
    private String naam;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "prijs")
    private double prijs;

//    @ManyToMany(mappedBy = "producten")
//    private List<OVChipkaart> ovChipkaarten;
    public Product() {}


    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

//    public List<OVChipkaart> getOVChipkaarten() {
//        return ovChipkaarten;
//    }
//
//    public void setOVChipkaarten(List<OVChipkaart> ovChipkaarten) {
//        this.ovChipkaarten = ovChipkaarten;
//    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "productNummer=" + productNummer +
                ", naam=" + naam +
                ", beschrijving=" + beschrijving +
                ", prijs=" + prijs +
                '}';
    }

    public boolean addOVChipkaart(OVChipkaart k){
        return false;
    }

    public boolean removeOVChipkaart(OVChipkaart k){
        return false;
    }

}
