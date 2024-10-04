    package nl.hu.dp.domain;

    import java.sql.Date;
    import java.util.ArrayList;
    import java.util.List;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "reiziger")
    public class Reiziger {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "reiziger_id")
        private int id;

        @Column(name = "voorletters")
        private String voorletters;

        @Column(name = "tussenvoegsel")
        private String tussenvoegsel;

        @Column(name = "achternaam")
        private String achternaam;

        @Column(name = "geboortedatum")
        private Date geboortedatum;

        @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL)
        private Adres adres;
        @OneToMany(mappedBy = "reiziger", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

        // Constructors, getters en setters

        public Reiziger() {}

        public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
            this.id = id;
            this.voorletters = voorletters;
            this.tussenvoegsel = tussenvoegsel;
            this.achternaam = achternaam;
            this.geboortedatum = geboortedatum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVoorletters() {
            return voorletters;
        }

        public void setVoorletters(String voorletters) {
            this.voorletters = voorletters;
        }

        public String getTussenvoegsel() {
            return tussenvoegsel;
        }

        public void setTussenvoegsel(String tussenvoegsel) {
            this.tussenvoegsel = tussenvoegsel;
        }

        public String getAchternaam() {
            return achternaam;
        }

        public void setAchternaam(String achternaam) {
            this.achternaam = achternaam;
        }

        public Date getGeboortedatum() {
            return geboortedatum;
        }

        public void setGeboortedatum(Date geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        public String getNaam() {
            if (tussenvoegsel == null || tussenvoegsel.isEmpty()) {
                return voorletters + " " + achternaam;
            } else {
                return voorletters + " " + tussenvoegsel + " " + achternaam;
            }
        }

        public List<OVChipkaart> getOvChipkaarten() {
            return ovChipkaarten;
        }

        public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
            this.ovChipkaarten = ovChipkaarten;
        }

        public void addOvChipkaart(OVChipkaart ovChipkaart) {
            ovChipkaarten.add(ovChipkaart);
            ovChipkaart.setReiziger(this);  // Zorg dat de OV-chipkaart naar de juiste reiziger wijst
        }

        public void removeOvChipkaart(OVChipkaart ovChipkaart) {
            ovChipkaarten.remove(ovChipkaart);
            ovChipkaart.setReiziger(null);  // Ontkoppel de OV-chipkaart van de reiziger
        }


        // toString-methode om de Reiziger als String weer te geven
        @Override
        public String toString() {
            return "Reiziger{" +
                    "id=" + id +
                    ", voorletters='" + voorletters + '\'' +
                    ", tussenvoegsel='" + tussenvoegsel + '\'' +
                    ", achternaam='" + achternaam + '\'' +
                    ", geboortedatum=" + geboortedatum + '\'' +
                    ", ovChipkaarten=" + ovChipkaarten +
                    '}';
        }
    }
