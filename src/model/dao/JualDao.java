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
import model.Jual;

/**
 *
 * @author Dzier
 */
public class JualDao {
    Connection connection;

    public JualDao(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Jual jual) throws SQLException{
        String sql="insert into jual values (?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, jual.getNo_faktur());
        ps.setString(2, jual.getTgl_faktur());
        ps.setString(3, jual.getKode_pelanggan());
        ps.setString(4, jual.getKode_brg());
        ps.setDouble(5, jual.getHarga());
        ps.setInt(6, jual.getJumlah());
        ps.setDouble(7, jual.getTotal());
        ps.executeUpdate();
    }
     public void update(Jual jual) throws SQLException{
        String sql = "update jual set tgl_faktur=?, harga=?, jumlah=?, total=?"+
                "where no_faktur=? and kode_pelanggan=? and kode_brg=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, jual.getTgl_faktur());
        ps.setDouble(2, jual.getHarga());
        ps.setInt(3, jual.getJumlah());
        ps.setDouble(4, jual.getTotal());
        ps.setString(5, jual.getNo_faktur());
        ps.setString(6, jual.getKode_pelanggan());
        ps.setString(7, jual.getKode_brg());
        ps.executeUpdate();
    }
     
     public void delete(String nofaktur, String kodepelanggan, String kodebrg) throws SQLException{
        String sql = "delete from jual where no_faktur=? and kode_pelanggan=? and kode_brg=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nofaktur);
        ps.setString(2, kodepelanggan);
        ps.setString(3, kodebrg);
        ps.executeUpdate();
    }
     
      public Jual getJual(String nofaktur, String kodepelanggan, String kodebrg) throws SQLException{
        String sql = "select * from jual where no_faktur=? and kode_pelanggan=? and kode_brg=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nofaktur);
        ps.setString(2, kodepelanggan);
        ps.setString(3, kodebrg);
       
        ResultSet rs = ps.executeQuery();
         Jual jual=null;
        if(rs.next()){
            jual = new Jual();
            jual.setNo_faktur(rs.getString(1));
            jual.setTgl_faktur(rs.getString(2));
            jual.setKode_pelanggan(rs.getString(3));
            jual.setKode_brg(rs.getString(4));
            jual.setHarga(rs.getDouble(5));
            jual.setJumlah(rs.getInt(6));
            jual.setTotal(rs.getDouble(7));
        }
        return jual;
    }
      public ResultSet getAllJual(String sql) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    
}
