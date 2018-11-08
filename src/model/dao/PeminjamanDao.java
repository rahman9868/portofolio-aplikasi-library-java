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
import model.Peminjaman;
/**
 *
 * @author LABP2KOMP14
 */
public class PeminjamanDao {
    Connection connection;
    
    public PeminjamanDao(Connection connection){
        this.connection = connection;
    }
    
    public void insert(Peminjaman peminjaman) throws SQLException{
        String sql = "insert into peminjaman values (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, peminjaman.getKode_anggota());
        ps.setString(2, peminjaman.getKode_buku());
        ps.setString(3, peminjaman.getTgl_pinjam());
        ps.setString(4, peminjaman.getTgl_kembali());
        ps.setString(5, peminjaman.getStatus());
        ps.executeUpdate();
    }
    
    public void update(Peminjaman peminjaman) throws SQLException{
        String sql = "update peminjaman set tgl_pinjam=?, tgl_kembali=?, statu where kode_anggota = ? and kode_buku=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(4, peminjaman.getKode_anggota());
        ps.setString(5, peminjaman.getKode_buku());
        ps.setString(1, peminjaman.getTgl_pinjam());
        ps.setString(2, peminjaman.getTgl_kembali());
        ps.setString(3, peminjaman.getStatus());
        ps.executeUpdate();
    }
    
    public void delete(String kode_anggota, String kode_buku, String tgl_pinjam) throws SQLException{
        String sql = "delete from peminjaman where kode_anggota = ? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ps.executeUpdate();
    }
    
    public Peminjaman getPeminjaman(String kode_anggota,String kode_buku, String tgl_pinjam) throws SQLException{
        String sql = "select * from peminjaman where kode_anggota=? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ResultSet rs = ps.executeQuery();
        Peminjaman peminjaman = null;
        if(rs.next()){
        peminjaman =  new Peminjaman();
        peminjaman.setKode_anggota(rs.getString(1));
        peminjaman.setKode_buku(rs.getString(2));
        peminjaman.setTgl_pinjam(rs.getString(3));
        peminjaman.setTgl_kembali(rs.getString(4));
        }
        return peminjaman;
        
    }
  
    public ResultSet getAllPeminjaman(String sql) throws SQLException{
        PreparedStatement ps= connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
