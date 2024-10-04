package nl.hu.dp.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int kaartNummer;
    @Column(name = "geldig_tot")
    private Date geldigTot;
    @Column(name = "klasse")
    private int klasse;
    @Column(name = "saldo")
    private double saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ov_chipkaart_product", // Join table name
            joinColumns = @JoinColumn(name = "kaart_nummer"), // Foreign key in the join table
            inverseJoinColumns = @JoinColumn(name = "product_nummer") // Foreign key for Product
    )
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart(){}
    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public boolean addProduct(Product p) {
        if (producten == null) {
            producten = new ArrayList<>();
        }
        if (!producten.contains(p)) {
            producten.add(p);
            p.addOVChipkaart(this);
            return true;
        }
        return false;
    }

    public boolean removeProduct(Product p) {
        if (producten != null && producten.contains(p)) {
            producten.remove(p);
            p.removeOVChipkaart(this); // Ensure bidirectional relationship
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger.getNaam() +
                '}';
    }

}
