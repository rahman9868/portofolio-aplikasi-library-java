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

/**
 *
 * @author Dzier
 */
public class PelangganDao {
    Connection connection;

    public PelangganDao(Connection connection) {
        this.connection = connection;
    }
    public ResultSet getAllPelanggan(String sql) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
