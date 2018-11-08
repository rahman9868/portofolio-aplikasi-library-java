/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LABP2KOMP14
 */
public class Anggota {
    private String kode_anggota;
    private String nama_anggota;
    private String alamat;
    private String jekel;
    private String tgl_lahir;
    
    public String getKode_anggota(){
        return kode_anggota;
    }
    
    public void setKode_anggota(String kode_anggota){
        this.kode_anggota = kode_anggota;
    }
    
    public String getNama_anggota(){
        return nama_anggota;
    }
    
    public void setNama_anggota(String nama_anggota){
        this.nama_anggota = nama_anggota;
    }
    
    public String getAlamat(){
        return alamat;
    }
    
    public void setAlamat(String alamat){
        this.alamat = alamat;
    }
    
    public String getJekel(){
        return jekel;
    }
    
    public void setJekel(String jekel){
        this.jekel = jekel;
    }
    
    public String getTgl_lahir(){
        return tgl_lahir;
    }
    
    public void setTgl_lahir(String tgl_lahir){
        this.tgl_lahir = tgl_lahir;
    }

   
}

