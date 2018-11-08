/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Anggota;

/**
 *
 * @author LABP2KOMP11
 */
public class AnggotaDao {
    Connection connection;
    
    public AnggotaDao(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Anggota anggota) throws SQLException {
        String sql = "insert into anggota values (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, anggota.getKode_anggota());
        ps.setString(2, anggota.getNama_anggota());
        ps.setString(3, anggota.getAlamat());
        ps.setString(4, anggota.getJekel());
        ps.setString(5, anggota.getTgl_lahir());
        ps.executeUpdate();
    }
    
    public void update(Anggota anggota)throws SQLException{
        String sql = "update anggota set nama_anggota=?, alamat=?, "
                + "jekel=?, tgl_lahir=? where kode_anggota=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(5, anggota.getKode_anggota());
        ps.setString(1, anggota.getNama_anggota());
        ps.setString(2, anggota.getAlamat());
        ps.setString(3, anggota.getJekel());
        ps.setString(4, anggota.getTgl_lahir());
        ps.executeUpdate();
    }
    
    public void delete(String kode_anggota)throws SQLException {
        String sql = "delete from anggota where kode_anggota=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.executeUpdate();
    }
    
    public Anggota getAnggota(String kode)throws SQLException {
        String sql = "select * from anggota where kode_anggota=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode);
        Anggota anggota = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            anggota = new Anggota();
            anggota.setKode_anggota(rs.getString(1));
            anggota.setNama_anggota(rs.getString(2));
            anggota.setAlamat(rs.getString(3));
            anggota.setJekel(rs.getString(4));
            anggota.setTgl_lahir(rs.getString(5));
        }
        return anggota;      
    }
    
    public ResultSet getAllAnggota(String sql) throws SQLException{
        PreparedStatement ps= connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
