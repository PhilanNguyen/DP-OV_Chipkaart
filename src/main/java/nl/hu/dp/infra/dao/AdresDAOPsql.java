package nl.hu.dp.infra.dao;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.AdresDAO;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }
    @Override
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)\n" +
                    "VALUES (?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, adres.getId());
            pstmt.setString(2, adres.getPostcode());
            pstmt.setString(3, adres.getHuisnummer());
            pstmt.setString(4, adres.getStraat());
            pstmt.setString(5, adres.getWoonplaats());
            pstmt.setInt(6, adres.getReiziger().getId());

            int rijenInserted = pstmt.executeUpdate();
            pstmt.close();

            return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, adres.getPostcode());
            pstmt.setString(2, adres.getHuisnummer());
            pstmt.setString(3, adres.getStraat());
            pstmt.setString(4, adres.getWoonplaats());
            pstmt.setInt(5, adres.getReiziger().getId());
            pstmt.setInt(6, adres.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, adres.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Adres(
                        rs.getInt("adres_id"),
                        rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        reiziger
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();
        try {
            String query = "SELECT * FROM adres";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger();
                Adres adres = new Adres(
                        rs.getInt("adres_id"),
                        rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        reiziger
                );
                adressen.add(adres);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adressen;
    }
}

