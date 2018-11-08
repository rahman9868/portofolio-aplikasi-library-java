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
import model.Buku;


/**
 *
 * @author LABP2KOMP14
 */
public class BukuDao {
     Connection connection;
    
    public BukuDao(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Buku buku) throws SQLException {
        String sql = "insert into buku values (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, buku.getKode_buku());
        ps.setString(2, buku.getJudul());
        ps.setString(3, buku.getPengarang());
        ps.setString(4, buku.getPenerbit());
        ps.setString(5, buku.getTahun_terbit());
        ps.executeUpdate();
    }
    
    public void update(Buku buku)throws SQLException{
        String sql = "update buku set judul=?, pengarang=?, penerbit=?, tahun_terbit=? where kode_buku=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(5, buku.getKode_buku());
        ps.setString(1, buku.getJudul());
        ps.setString(2, buku.getPengarang());
        ps.setString(3, buku.getPenerbit());
        ps.setString(4, buku.getTahun_terbit());
        ps.executeUpdate();
    }
    
    public void delete(String kode_buku)throws SQLException {
        String sql = "delete from buku where kode_buku=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_buku);
        ps.executeUpdate();
    }
    
    public Buku getBuku(String kode)throws SQLException {
        String sql = "select * from buku where kode_buku=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode);
        Buku buku = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            buku = new Buku();
            buku.setKode_buku(rs.getString(1));
            buku.setJudul(rs.getString(2));
            buku.setPengarang(rs.getString(3));
            buku.setPenerbit(rs.getString(4));
            buku.setTahun_terbit(rs.getString(5));
        }
        return buku;      
    }
    
    public ResultSet getAllBuku(String sql) throws SQLException{
        PreparedStatement ps= connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    
}
