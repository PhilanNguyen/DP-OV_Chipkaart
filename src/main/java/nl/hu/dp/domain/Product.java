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

    @ManyToMany(mappedBy = "producten", cascade = CascadeType.ALL)
    private List<OVChipkaart> ovChipkaarten;
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

    public List<OVChipkaart> getOVChipkaarten() {
        return ovChipkaarten;
    }

    public void setOVChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public boolean addOVChipkaart(OVChipkaart k) {
        if (ovChipkaarten == null) {
            ovChipkaarten = new ArrayList<>();
        }
        if (!ovChipkaarten.contains(k)) {
            ovChipkaarten.add(k);
            k.addProduct(this); // Ensure bidirectional relationship
            return true;
        }
        return false;
    }

    public boolean removeOVChipkaart(OVChipkaart k) {
        if (ovChipkaarten != null && ovChipkaarten.contains(k)) {
            ovChipkaarten.remove(k);
            k.removeProduct(this); // Ensure bidirectional relationship
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNummer=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }

}
