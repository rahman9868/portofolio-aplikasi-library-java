/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Buku;
import model.Koneksi;
import model.dao.BukuDao;
import view.FormBuku;

/**
 *
 * @author LABP2KOMP14
 */
public class BukuController {
    FormBuku view;
    BukuDao bukuDao;
    Connection connection;
    Buku buku;


    public BukuController(FormBuku view) {
        try {
            this.view = view;
            Koneksi k = new Koneksi();
            connection = k.getConnection();
            bukuDao = new BukuDao(connection);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

         public void insert_buku() {
        
        try {
            buku = new Buku();
            buku.setKode_buku(view.getTxtKodeBuku().getText());
            buku.setJudul(view.getTxtJudul().getText());
            buku.setPengarang(view.getTxtPengarang().getText());
            buku.setPenerbit(view.getTxtPenerbit().getText());
            buku.setTahun_terbit(view.getTxtTahun_terbit().getText());
            bukuDao.insert(buku);
            JOptionPane.showMessageDialog(view, "Entri  Data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public void update_buku(){
        
        try {
        buku = new Buku();
        buku.setKode_buku(view.getTxtKodeBuku().getText());
        buku.setJudul(view.getTxtJudul().getText());
        buku.setPengarang(view.getTxtPengarang().getText());
        buku.setPenerbit(view.getTxtPenerbit().getText());
        buku.setTahun_terbit(view.getTxtTahun_terbit().getText());
            bukuDao.update(buku);
            JOptionPane.showMessageDialog(view, "UPDATE DATA OK!!! :3");
        } catch (SQLException ex) {
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(view, "UPDATE ERROR NICH!!! X_X");
        }
    }
         
         public void delete_buku () {
        try {
            String kode_buku = view.getTxtKodeBuku().getText();
            bukuDao.delete(kode_buku);
            JOptionPane.showMessageDialog(view, "Delete data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public void cari() {
         try {
            String kodebuku = view.getTxtKodeBuku().getText();
            buku = bukuDao.getBuku(kodebuku);
            if(buku != null){
                view.getTxtJudul().setText(buku.getJudul());
                view.getTxtPengarang().setText(buku.getPengarang());
                view.getTxtPenerbit().setText(buku.getPenerbit());
                view.getTxtTahun_terbit().setText(buku.getTahun_terbit());
                
            }
            else{
                JOptionPane.showMessageDialog(view, "DATA TIDAK ADA");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "ERROR: " +ex.getMessage());
            Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public void UpdateTabel() {
        try {
            final ResultSet rs = bukuDao.getAllBuku("select * from buku");
            String[][] data = new String[1][5];
            if (rs !=null) {
                rs.last();
                int row = rs.getRow();
                rs.beforeFirst();
                int i=0;
                data= new String[row][5];
                while(rs.next()) {
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    data[i][3] = rs.getString(4);
                    data[i][4] = rs.getString(5);
                    i++;
                  }
            }
            else {
                data[0][0] = "No Data";
            }
            
            String[] kolom = {"Kode Buku","Judul Buku","Pengarang",
                "Penerbit","Tahun Terbit" };
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom) {
                public boolean isCellEditable(final int rowIndex, int colIndex) {
                    return false;
                }
            };
            view.getTabelBuku().setModel(tmodel);
            view.getTabelBuku().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
         
    
}
