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
import model.Pengembalian;
import model.dao.AnggotaDao;
import model.dao.BukuDao;
import model.dao.PeminjamanDao;
import model.dao.PengembalianDao;
import view.FormPengembalian;

/**
 *
 * @author LABP2KOMP14
 */
public class PengembalianController {
    
    FormPengembalian view;
    AnggotaDao anggotaDao;
    BukuDao bukuDao;
    Pengembalian pengembalian;
    PeminjamanDao peminjamanDao;
    PengembalianDao pengembalianDao;
    String[] tkodeanggota;
    String[] tkodebuku;
    Connection con;

    public PengembalianController(FormPengembalian view) {
        try {
            this.view = view;
            Koneksi k = new Koneksi();
            con = k.getConnection();
            anggotaDao = new AnggotaDao(con);
            bukuDao = new BukuDao(con);
            peminjamanDao = new PeminjamanDao(con);
            pengembalianDao = new PengembalianDao(con);
            pengembalian = new Pengembalian();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void isiCboAnggota(){
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
    
    public void proses(){
        try {
            String tglkembali = view.getTxtTgl_kembali().getText();
            String tgldikembalikan = view.getTxtTgl_dikembalikan().getText();
            int terlambat = pengembalianDao.kurangTanggal(tgldikembalikan, tglkembali);
            double denda = 5000 * terlambat;
            view.getTxtTerlambat().setText(""+terlambat);
            view.getTxtDenda().setText(""+denda);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cari_peminjaman(){
        try {
            String kodeanggota = tkodeanggota[view.getCboKodeAnggota().getSelectedIndex()];
            String kodebuku = tkodebuku[view.getCboKodeBuku().getSelectedIndex()];
            String tglpinjam = view.getTxtTgl_pinjam().getText();
            Peminjaman peminjaman = peminjamanDao.getPeminjaman(kodeanggota, kodebuku, tglpinjam);
            view.getTxtTgl_kembali().setText(peminjaman.getTgl_kembali());
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert_pengembalian(){
        try {
            pengembalian.setKode_anggota(tkodeanggota [view.getCboKodeAnggota().getSelectedIndex()]);
            pengembalian.setKode_buku(tkodebuku [view.getCboKodeBuku().getSelectedIndex()]);
            pengembalian.setTgl_kembali(view.getTxtTgl_kembali().getText());
            pengembalian.setTgl_dikembalikan(view.getTxtTgl_dikembalikan().getText());
            pengembalian.setTerlambat(Integer.parseInt(view.getTxtTerlambat().getText()));
            pengembalian.setDenda(Double.parseDouble(view.getTxtDenda().getText()));
            pengembalianDao.insert(pengembalian);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update_pengembalian(){
        try {
            pengembalian.setKode_anggota(tkodeanggota [view.getCboKodeAnggota().getSelectedIndex()]);
            pengembalian.setKode_buku(tkodebuku [view.getCboKodeBuku().getSelectedIndex()]);
            pengembalian.setTgl_kembali(view.getTxtTgl_kembali().getText());
            pengembalian.setTgl_dikembalikan(view.getTxtTgl_dikembalikan().getText());
            pengembalian.setTerlambat(Integer.parseInt(view.getTxtTerlambat().getText()));
            pengembalian.setDenda(Double.parseDouble(view.getTxtDenda().getText()));
            pengembalianDao.update(pengembalian);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete_peminjaman(){
        try {
            String kodeanggota = view.getCboKodeAnggota().toString();
            String kodebuku = view.getCboKodeBuku().toString();
            String tglpinjam = view.getTxtTgl_pinjam().getText();
            pengembalianDao.delete(kodeanggota, kodebuku, tglpinjam);
            JOptionPane.showMessageDialog(view, "Delete data Ok");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTabel(){
        try {
            final ResultSet rs = pengembalianDao.getAllPengembalian("select * from peminjaman");
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
            String[] kolom = {"Kode Anggota", "Kode Buku", "Tgl Pinjam", "Tgl Kembali","Tanggal Dikembalikan","Terlambat","Denda" };
            DefaultTableModel tmodel = new DefaultTableModel(data, kolom) {
               
                @Override
               public boolean isCellEditable(final int rowIndex, int cellIndex ) { 
            return false;
               }
           };
              view.getTabelPengembalian().setModel(tmodel);
              view.getTabelPengembalian().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }
    

