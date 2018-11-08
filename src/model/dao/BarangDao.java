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
import model.Barang;

/**
 *
 * @author Dzier
 */
public class BarangDao {
    Connection connection;

    public BarangDao(Connection connection) {
        this.connection = connection;
    }
    public Barang getBarang(String kode_brg) throws SQLException{
        String sql = "select * from barang where kode_brg=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_brg);
        Barang barang = null;
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            barang = new Barang();
            barang.setKode_brg(rs.getString(1));
            barang.setNama_brg(rs.getString(2));
        }
        return barang;
    }
    
    public ResultSet getAllBarang(String sql) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
