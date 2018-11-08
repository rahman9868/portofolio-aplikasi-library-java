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
import model.Pengembalian;

/**
 *
 * @author LABP2KOMP14
 */
public class PengembalianDao {
    Connection connection;

    public PengembalianDao(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Pengembalian pengembalian) throws SQLException {
        String sql = "insert into pengembalian values (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, pengembalian.getKode_anggota());
        ps.setString(2, pengembalian.getKode_buku());
        ps.setString(3, pengembalian.getTgl_kembali());
        ps.setString(4, pengembalian.getTgl_dikembalikan());
        ps.setInt(5, pengembalian.getTerlambat());
        ps.setDouble(6, pengembalian.getDenda());
        ps.executeUpdate();
    }
    
    public void update(Pengembalian pengembalian) throws SQLException{
        String sql = "update pengembalian set  tgl_dikembalikan=?, terlambat=?, denda=? where kode_anggota=? and kode_buku=? and tgl_pinjam=? ";
         PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(4, pengembalian.getKode_anggota());
        ps.setString(5, pengembalian.getKode_buku());
        ps.setString(6, pengembalian.getTgl_kembali());
        ps.setString(1, pengembalian.getTgl_dikembalikan());
        ps.setInt(2, pengembalian.getTerlambat());
        ps.setDouble(3, pengembalian.getDenda());
        ps.executeUpdate();
    }
    
    public void delete(String kode_anggota, String kode_buku, String tgl_pinjam) throws SQLException {
        String sql = "delete from pengembalian where kode_anggota = ? and kode_buku=? and tgl_pinjam=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ps.executeUpdate();
    }
    
    public Pengembalian getPengembalian (String kode_anggota, String kode_buku, String tgl_pinjam) throws SQLException{
         String sql = "select * from pengembalian where kode_anggota=? and kode_buku=? and tgl_pinjam=?";
         PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_anggota);
        ps.setString(2, kode_buku);
        ps.setString(3, tgl_pinjam);
        ResultSet rs = ps.executeQuery();
        Pengembalian pengembalian = null;
        if(rs.next()){
            pengembalian = new Pengembalian();
            pengembalian.setKode_anggota(rs.getString(1));
            pengembalian.setKode_buku(rs.getString(2));
            pengembalian.setTgl_kembali(rs.getString(3));
            pengembalian.setTgl_dikembalikan(rs.getString(4));
            pengembalian.setTerlambat(rs.getInt(5));
            pengembalian.setDenda(rs.getDouble(6));
            
        }
        return pengembalian;
    }
    
    public ResultSet getAllPengembalian(String sql) throws SQLException{
        PreparedStatement ps= connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    
    public int kurangTanggal(String tgl1,String tgl2) throws SQLException{
        String sql = "SELECT datediff(?,?) as hasil";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, tgl1);
        ps.setString(2, tgl2);
        ResultSet rs = ps.executeQuery();
        int hasil=0;
        if(rs.next()){
            hasil=rs.getInt(1);
        }
        return hasil;
               
    }
}
