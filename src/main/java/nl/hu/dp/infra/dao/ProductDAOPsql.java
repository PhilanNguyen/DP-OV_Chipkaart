package nl.hu.dp.infra.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.ProductDAO;

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
            String query = "UPDATE ov_chipkaart SET reiziger_id = ?, geldig_tot = ?, klassse = ?, saldo = ? WHERE kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, product.getProductNummer());
            pstmt.setString(2, product.getNaam());
            pstmt.setString(3, product.getBeschrijving());
            pstmt.setDouble(4, product.getPrijs());

            int rijenInserted = pstmt.executeUpdate();
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
//        List<Product> producten = new ArrayList<>();
//        try {
//            String query = "SELECT p FROM product p JOIN ov_chipkaart o ON WHERE ov = ?";
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, reiziger.getId());
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                OVChipkaart ovChipkaart = new OVChipkaart(
//                        rs.getInt("kaart_nummer"),
//                        rs.getDate("geldig_tot"),
//                        rs.getInt("klasse"),
//                        rs.getDouble("saldo"),
//                        reiziger.getId()
//                );
//                ovChipkaarten.add(ovChipkaart);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
