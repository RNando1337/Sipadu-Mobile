package com.example.myapplication;

public class DataLaporan {
    String KD_ADUAN,kat_aduan,Desk,Gambar,Status,koordinat,no_hp,tanggal,idpel,nama,nama_petugas,bukti_foto,response,Verifikasi;

    public DataLaporan(String KD_ADUAN,String kat_aduan,String Desk,String Gambar,String Status,String koordinat,String no_hp,String tanggal,String idpel,String nama,String nama_petugas,String bukti_foto,String Verifikasi,String response){
    this.KD_ADUAN=KD_ADUAN;
    this.kat_aduan=kat_aduan;
    this.Desk=Desk;
    this.Gambar=Gambar;
    this.Status=Status;
    this.koordinat=koordinat;
    this.no_hp=no_hp;
    this.tanggal=tanggal;
    this.idpel=idpel;
    this.nama=nama;
    this.nama_petugas=nama_petugas;
    this.bukti_foto=bukti_foto;
    this.Verifikasi=Verifikasi;
    this.response=response;
    }

    public String getVerifikasi() {
        return Verifikasi;
    }

    public void setVerifikasi(String verifikasi) {
        Verifikasi = verifikasi;
    }

    public String getBukti_foto() {
        return bukti_foto;
    }

    public void setBukti_foto(String bukti_foto) {
        this.bukti_foto = bukti_foto;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getNama_petugas() {
        return nama_petugas;
    }

    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }

    public String getKD_ADUAN() {
        return KD_ADUAN;
    }

    public void setKD_ADUAN(String KD_ADUAN) {
        this.KD_ADUAN = KD_ADUAN;
    }

    public String getKat_aduan() {
        return kat_aduan;
    }

    public void setKat_aduan(String kat_aduan) {
        this.kat_aduan = kat_aduan;
    }

    public String getDesk() {
        return Desk;
    }

    public void setDesk(String desk) {
        Desk = desk;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
