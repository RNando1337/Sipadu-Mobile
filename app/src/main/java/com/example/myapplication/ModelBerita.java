package com.example.myapplication;

public class ModelBerita {
    String kd_brt,jdl_brt,informasi,gambar,timeupload;

    public ModelBerita(String kd_brt,String jdl_brt,String informasi,String gambar,String timeupload){
        this.kd_brt = kd_brt;
        this.jdl_brt = jdl_brt;
        this.informasi = informasi;
        this.gambar = gambar;
        this.timeupload = timeupload;
    }

    public String getKd_brt() {
        return kd_brt;
    }

    public void setKd_brt(String kd_brt) {
        this.kd_brt = kd_brt;
    }

    public String getJdl_brt() {
        return jdl_brt;
    }

    public void setJdl_brt(String jdl_brt) {
        this.jdl_brt = jdl_brt;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTimeupload() {
        return timeupload;
    }

    public void setTimeupload(String timeupload) {
        this.timeupload = timeupload;
    }
}
