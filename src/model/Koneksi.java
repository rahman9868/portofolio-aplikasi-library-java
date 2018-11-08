/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LABP2KOMP14
 */
public class Koneksi {
    Connection connection;
    String url = "jdbc:mysql://localhost:3306/pcs_1601082026";
    String username = "root";
    String password = "";
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
    
    public static void main(String[] args){
        try {
            Koneksi k = new Koneksi();
            k.getConnection();
            JOptionPane.showMessageDialog(null, "Koneksi Ok");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver Belum Ada");
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error");
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
