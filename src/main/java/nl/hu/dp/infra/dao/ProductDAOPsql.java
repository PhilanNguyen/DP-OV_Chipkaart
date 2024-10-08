package nl.hu.dp.infra.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.ProductDAO;
import nl.hu.dp.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        try {
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs)\n" +
                    "VALUES (?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, product.getProductNummer());
            pstmt.setString(2, product.getNaam());
            pstmt.setString(3, product.getBeschrijving());
            pstmt.setDouble(4, product.getPrijs());

            int rijenInserted = pstmt.executeUpdate();

            if (rijenInserted > 0 && product.getOVChipkaarten() != null) {
                for (OVChipkaart ovChipkaart : product.getOVChipkaarten()) {
                    String insertQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
                    PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
                    insertPstmt.setInt(1, ovChipkaart.getKaartNummer());
                    insertPstmt.setInt(2, product.getProductNummer());
                    insertPstmt.executeUpdate();
                    insertPstmt.close();
                }
            }

            pstmt.close();

            return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, product.getNaam());
            pstmt.setString(2, product.getBeschrijving());
            pstmt.setDouble(3, product.getPrijs());
            pstmt.setInt(4, product.getProductNummer());

            int rijenInserted = pstmt.executeUpdate();

            //delete oude relaties
            String deleteRelationQuery = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement deleteRelationPstmt = conn.prepareStatement(deleteRelationQuery);
            deleteRelationPstmt.setInt(1, product.getProductNummer());
            deleteRelationPstmt.executeUpdate();
            deleteRelationPstmt.close();

            //voeg toe nieuwe relaties
            if (product.getOVChipkaarten() != null) {
                for (OVChipkaart ovChipkaart : product.getOVChipkaarten()) {
                    String insertQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
                    PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
                    insertPstmt.setInt(1, ovChipkaart.getKaartNummer());
                    insertPstmt.setInt(2, product.getProductNummer());
                    insertPstmt.executeUpdate();
                    insertPstmt.close();
                }
            }

            pstmt.close();

            return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            String deleteQuery = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement deletepstmt = conn.prepareStatement(deleteQuery);

            deletepstmt.setInt(1, product.getProductNummer());
            deletepstmt.executeUpdate();
            deletepstmt.close();

            String query = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, product.getProductNummer());
            int rijenInserted = pstmt.executeUpdate();

            pstmt.close();

            return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> producten = new ArrayList<>();
        try {
            String query = "SELECT p.* FROM product p JOIN ov_chipkaart_product o ON p.product_nummer = o.product_nummer WHERE o.kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ovChipkaart.getKaartNummer());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_nummer"),
                        rs.getString("naam"),
                        rs.getString("beschrijving"),
                        rs.getDouble("prijs")
                );
                producten.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producten;
    }

    @Override
    public List<Product> findAll() {
        List<Product> producten = new ArrayList<>();
        try {
            String query = "SELECT * FROM product   ";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_nummer"),
                        rs.getString("naam"),
                        rs.getString("beschrijving"),
                        rs.getDouble("prijs")
                );
                producten.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producten;
    }
}
