package nl.hu.ipass.infra.dao;

import nl.hu.ipass.domain.Reiziger;
import nl.hu.ipass.domain.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;
    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }
    @Override
    public boolean save(Reiziger reiziger) {
        try {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)\n" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setInt(1, reiziger.getId());
        pstmt.setString(2, reiziger.getVoorletters());
        if (reiziger.getTussenvoegsel().isEmpty()){
            String tussenvoegsel = null;
            pstmt.setString(3,tussenvoegsel);
        } else{
            pstmt.setString(3, reiziger.getTussenvoegsel());
        }
        pstmt.setString(4, reiziger.getAchternaam());
        pstmt.setDate(5, reiziger.getGeboortedatum());
        int rijenInserted = pstmt.executeUpdate();
        pstmt.close();

        return rijenInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reiziger.getVoorletters());
            pstmt.setString(2, reiziger.getTussenvoegsel());
            pstmt.setString(3, reiziger.getAchternaam());
            pstmt.setDate(4, reiziger.getGeboortedatum());
            pstmt.setInt(5, reiziger.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, date);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM reiziger";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigers;
    }
}
