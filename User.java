package com.example.quanlynguoidung;

public class User {
    private int id;
    private String name;
    private String taikhoan;
    private String matkhau;
    private String diachi;
    private String sdt;

    public User() {
        this.id = id;
        this.name = name;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
