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
import model.Koneksi;
import model.Peminjaman;
import model.dao.AnggotaDao;
import model.dao.BukuDao;
import model.dao.PeminjamanDao;
import view.FormPeminjaman;
/**
 *
 * @author LABP2KOMP14
 */
public class PeminjamanController {
    FormPeminjaman view;
    Koneksi k;
    Connection con;
    PeminjamanDao peminjamanDao;
    AnggotaDao anggotaDao;
    BukuDao bukuDao;
    Peminjaman peminjaman;
    String[] tkodeanggota;
    String[] tkodebuku;
    
    public PeminjamanController(FormPeminjaman view){
       
        try {
            this.view = view;
            this.k = new Koneksi();
            this.con = k.getConnection();
            peminjamanDao = new PeminjamanDao(con);
            anggotaDao = new AnggotaDao(con);
            bukuDao = new BukuDao(con);
            peminjaman = new Peminjaman();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void isiCboKodeAnggota(){
        view.getCboKodeAnggota().removeAllItems();
        try {
            
            ResultSet rs=anggotaDao.getAllAnggota("select * from anggota");
            rs.last();
            int jumdata=rs.getRow();
            tkodeanggota=new String [jumdata];
            int counter=0;
            rs.beforeFirst();
            while(rs.next()){
                view.getCboKodeAnggota().addItem(rs.getString(2));
                tkodeanggota[counter] = rs.getString(1);
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void isiCboKodeBuku(){
       view.getCboKodeBuku().removeAllItems();
        try {
            
            ResultSet rs = bukuDao.getAllBuku("select * from buku");
            rs.last();
            int jumdata = rs.getRow();
            tkodebuku = new String[jumdata];
            int counter = 0;
            rs.beforeFirst();
            while (rs.next()){
                view.getCboKodeBuku().addItem(rs.getString(2));
                tkodebuku[counter] = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void insert_peminjaman(){
        try {
            
            peminjaman.setKode_anggota(tkodeanggota [view.getCboKodeAnggota().getSelectedIndex()]);
            peminjaman.setKode_buku(tkodebuku [view.getCboKodeBuku().getSelectedIndex()]);
            peminjaman.setTgl_pinjam(view.getTxtTgl_pinjam().getText());
            peminjaman.setTgl_kembali(view.getTxtTgl_kembali().getText());
            peminjaman.setStatus("0");
            peminjamanDao.insert(peminjaman);
            JOptionPane.showMessageDialog(view, "Entri  Data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update_peminjaman(){
        try {
            peminjaman.setKode_anggota(tkodeanggota [view.getCboKodeAnggota().getSelectedIndex()]);
            peminjaman.setKode_buku(tkodebuku [view.getCboKodeBuku().getSelectedIndex()]);
            peminjaman.setTgl_pinjam(view.getTxtTgl_pinjam().getText());
            peminjaman.setTgl_kembali(view.getTxtTgl_kembali().getText());
            peminjaman.setStatus("0");
            peminjamanDao.update(peminjaman);
            JOptionPane.showMessageDialog(view, "Entri  Data Ok");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete_peminjaman(){
        try {
            String kodeanggota = view.getCboKodeAnggota().toString();
            String kodebuku = view.getCboKodeBuku().toString();
            String tglpinjam = view.getTxtTgl_pinjam().getText();
            peminjamanDao.delete(kodeanggota, kodebuku, tglpinjam);
            JOptionPane.showMessageDialog(view, "Delete data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cari(){
        try {
            String kodeanggota = view.getCboKodeAnggota().toString();
            String kodebuku = view.getCboKodeBuku().toString();
            String tglpinjam = view.getTxtTgl_pinjam().getText();
            peminjaman = peminjamanDao.getPeminjaman(kodeanggota, kodebuku, tglpinjam);
            if (peminjaman != null){
                view.getCboKodeAnggota().setSelectedItem(peminjaman.getKode_anggota());
                view.getCboKodeBuku().setSelectedItem(peminjaman.getKode_buku());
                view.getTxtTgl_pinjam().setText(peminjaman.getTgl_pinjam());
                view.getTxtTgl_kembali().setText(peminjaman.getTgl_kembali());
            }
            else {
                JOptionPane.showMessageDialog(view, "DATA TIDAK ADA");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "ERROR: " +ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void UpdateTabel(){
        try {
            final ResultSet rs = peminjamanDao.getAllPeminjaman("select * from peminjaman");
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
                    i++;}
            }
            else {
                data[0][0] = "No Data";
            }
            String[] kolom = {"Kode Anggota", "Kode Buku", "Tgl Pinjam", "Tgl Kembali","Status" };
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom) {
               
                @Override
               public boolean isCellEditable(final int rowIndex, int cellIndex ) { 
            return false;
               }
           };
              view.getTabelPeminjaman().setModel(tmodel);
              view.getTabelPeminjaman().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
