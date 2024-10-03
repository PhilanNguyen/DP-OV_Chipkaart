package nl.hu.dp.infra.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.OVChipkaartDAO;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }
    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)\n" +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, ovChipkaart.getKaartNummer());
            pstmt.setDate(2, ovChipkaart.getGeldigTot());
            pstmt.setInt(3, ovChipkaart.getKlasse());
            pstmt.setDouble(4, ovChipkaart.getSaldo());
            pstmt.setInt(5, ovChipkaart.getReizigerId());
            int rijenInserted = pstmt.executeUpdate();
            pstmt.close();

            return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String query = "UPDATE ov_chipkaart SET reiziger_id = ?, geldig_tot = ?, klassse = ?, saldo = ? WHERE kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ovChipkaart.getReizigerId());
            pstmt.setDate(2, ovChipkaart.getGeldigTot());
            pstmt.setInt(3, ovChipkaart.getKlasse());
            pstmt.setDouble(4, ovChipkaart.getSaldo());

            pstmt.setInt(5, ovChipkaart.getKaartNummer());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ovChipkaart.getKaartNummer());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        reiziger.getId()
                );
                ovChipkaarten.add(ovChipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ovChipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        rs.getInt("reiziger_id")
                );
                ovChipkaarten.add(ovChipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ovChipkaarten;
    }
}
