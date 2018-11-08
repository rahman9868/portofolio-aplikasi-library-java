/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Barang;
import model.Jual;
import model.Koneksi;
import model.dao.BarangDao;
import model.dao.JualDao;
import model.dao.PelangganDao;
import view.FormJual;

/**
 *
 * @author Dzier
 */
public class JualController {
    FormJual view;
    JualDao jualDao;
    Connection connection;
    Koneksi k;
    Jual jual;
    Barang barang;
    BarangDao barangDao;
    PelangganDao pelangganDao;
    String []tkodepelanggan;
    String []tkodebrg;

    public JualController(FormJual view) {
        try {
            this.view = view;
            this.k = new Koneksi();
            connection = k.getConnection();
            jualDao = new JualDao(connection);
            barangDao = new BarangDao(connection);
            pelangganDao = new PelangganDao(connection);            
            jual = new Jual();
            barang = new Barang();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JualController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JualController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isiCboPelanggan(){
        view.getCboPelanggan().removeAllItems();
        try {
            ResultSet rs = pelangganDao.getAllPelanggan("select * from pelanggan");
            rs.last();
            int jumlahdata = rs.getRow();
            tkodepelanggan = new String[jumlahdata];
            int counter=0;
            rs.beforeFirst();
            while(rs.next()){
                view.getCboPelanggan().addItem(rs.getString(2));
                tkodepelanggan[counter] = rs.getString(1);
                counter++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isiCboBarang(){
        view.getCboBarang().removeAllItems();
        try {
            ResultSet rs = barangDao.getAllBarang("select * from barang");
            rs.last();
            int jumlahdata = rs.getRow();
            tkodebrg = new String[jumlahdata];
            int counter=0;
            rs.beforeFirst();
            while(rs.next()){
                view.getCboBarang().addItem(rs.getString(1));
                tkodebrg[counter] = rs.getString(1);
                counter++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert_jual(){
        try {
            jual.setNo_faktur(view.getTxtNoFaktur().getText());
            jual.setTgl_faktur(view.getTxtTglFaktur().getText());
            jual.setKode_pelanggan(tkodepelanggan[view.getCboPelanggan().getSelectedIndex()]);
            jual.setKode_brg(tkodebrg[view.getCboBarang().getSelectedIndex()]);
            double harga = Double.parseDouble(view.getTxtHarga().getText());
            int jumlah = Integer.parseInt(view.getTxtJumlah().getText());
            double total = Double.parseDouble(view.getTxtTotal().getText());
            jual.setHarga(harga);
            jual.setJumlah(jumlah);
            jual.setTotal(total);
            jualDao.insert(jual);
            JOptionPane.showMessageDialog(view, "Insert Penjualan OK");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(JualController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    public void update_tabel(){
        try {
            final ResultSet rs = jualDao.getAllJual("select * from jual");
            String [][]data = new String[1][7];
            if(rs!=null){
                rs.last();
                int row = rs.getRow();
                rs.beforeFirst();
                int i = 0;
                data = new String[row][7];
                while(rs.next()){
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    data[i][3] = rs.getString(4);
                    data[i][4] = rs.getString(5);
                    data[i][5] = rs.getString(6);
                    data[i][6] = rs.getString(7);
                    i++;
                }
            }else{
                data[0][0] = "Tidak Ada";
            }
            
            String []kolom = {"No Faktur", "Tgl Faktur", "Kode Pelanggan", "Kode Barang", "Harga","Jumlah","Total"};
            DefaultTableModel tmodel = new DefaultTableModel(data,kolom){
                public boolean isCellEditable(final int rowIndex, int colIndex){
                    return false;
                }
            };
            view.getTabelJual().setModel(tmodel);
            view.getTabelJual().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void proses(){
        double harga = Double.parseDouble(view.getTxtHarga().getText());
            int jumlah = Integer.parseInt(view.getTxtJumlah().getText());
            double total = harga*jumlah;
            String x  = Double.toString(total);
            view.getTxtTotal().setText(x);
            
            
    }
    public void cari_edit(){
        try {
            String nofaktur = view.getTxtNoFaktur().getText();
            String kodepelanggan = tkodepelanggan[view.getCboPelanggan().getSelectedIndex()];
            
            String kodebrg = view.getCboBarang().getSelectedItem().toString();
            jual = jualDao.getJual(nofaktur, kodepelanggan, kodebrg);
            if (jual != null){
                view.getTxtNoFaktur().setText(jual.getNo_faktur());
                view.getCboPelanggan().setSelectedItem(jual.getKode_pelanggan());
                view.getCboBarang().setSelectedItem(jual.getKode_brg());
                view.getTxtTglFaktur().setText(jual.getTgl_faktur());
                view.getTxtNamaBrg().setText(jual.getNama_brg());
                view.getTxtHarga().setText(Double.toString(jual.getHarga()));
                view.getTxtJumlah().setText(Integer.toString(jual.getJumlah()));
                view.getTxtTotal().setText(Double.toString(jual.getTotal()));
            }
            else {
                JOptionPane.showMessageDialog(view, "Faktur : "+nofaktur+" pel : "+kodepelanggan+"brg : "+kodebrg);
                JOptionPane.showMessageDialog(view, "DATA TIDAK ADA");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "ERROR: " +ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public void cari_tabel(){
        try {
            String nofaktur = view.getTxtNoFaktur().getText();
            String sql = "select * from jual where no_faktur=?";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1, nofaktur);
            final ResultSet rs = jualDao.getAllJual(sql);
            
            String [][]data = new String[1][7];
            if(rs!=null){
                rs.last();
                int row = rs.getRow();
                rs.beforeFirst();
                int i = 0;
                data = new String[row][7];
                while(rs.next()){
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    data[i][3] = rs.getString(4);
                    data[i][4] = rs.getString(5);
                    data[i][5] = rs.getString(6);
                    data[i][6] = rs.getString(7);
                    i++;
                }
            }else{
                data[0][0] = "Tidak Ada";
            }
            
            String []kolom = {"No Faktur", "Tgl Faktur", "Kode Pelanggan", "Kode Barang", "Harga","Jumlah","Total"};
            DefaultTableModel tmodel = new DefaultTableModel(data,kolom){
                public boolean isCellEditable(final int rowIndex, int colIndex){
                    return false;
                }
            };
            view.getTabelJual().setModel(tmodel);
            view.getTabelJual().setSelectionMode(0);
        } catch (SQLException ex) {
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cari() {
        try {
            String nomorfaktur = view.getTxtNoFaktur().getText();
            String kodepelanggan = tkodepelanggan[view.getCboPelanggan().getSelectedIndex()];
            String kodebarang = tkodebrg[view.getCboBarang().getSelectedIndex()];
            jual = jualDao.getJual(nomorfaktur, kodepelanggan, kodebarang);
            if(jual!=null){
                view.getTxtTglFaktur().setText(jual.getTgl_faktur());
                view.getTxtNamaBrg().setText(jual.getNama_brg());
                view.getTxtHarga().setText(String.valueOf(jual.getHarga()));
                view.getTxtJumlah().setText(String.valueOf(jual.getJumlah()));
                view.getTxtTotal().setText(String.valueOf(jual.getTotal()));
            } else{
                JOptionPane.showMessageDialog(view, "Data Tidak Ada");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : " + ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_jual() {
        try {
            jual.setNo_faktur(view.getTxtNoFaktur().getText());
            jual.setTgl_faktur(view.getTxtTglFaktur().getText());
            jual.setKode_pelanggan(tkodepelanggan[view.getCboPelanggan().getSelectedIndex()]);
            jual.setKode_brg(tkodebrg[view.getCboBarang().getSelectedIndex()]);
            jual.setNama_brg(view.getTxtNamaBrg().getText());
            jual.setHarga(Double.parseDouble(view.getTxtHarga().getText()));
            jual.setJumlah(Integer.parseInt(view.getTxtJumlah().getText()));
            jual.setTotal(Double.parseDouble(view.getTxtTotal().getText()));
            jualDao.update(jual);
            JOptionPane.showMessageDialog(view, "Update Pengembalian OK");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : "+ex.getMessage());
            Logger.getLogger(PengembalianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_jual()  {
        try {
            String nomorfaktur = view.getTxtNoFaktur().getText();
            String kodepelanggan = tkodepelanggan[view.getCboPelanggan().getSelectedIndex()];
            String kodebarang = tkodebrg[view.getCboBarang().getSelectedIndex()];
            jualDao.delete(nomorfaktur,kodepelanggan,kodebarang);
            JOptionPane.showMessageDialog(view, "Delete Peminjaman OK");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error : " + ex.getMessage());
            Logger.getLogger(PeminjamanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
