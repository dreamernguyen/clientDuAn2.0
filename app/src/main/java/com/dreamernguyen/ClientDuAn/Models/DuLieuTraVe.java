package com.dreamernguyen.ClientDuAn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DuLieuTraVe {
    @SerializedName("thongBao")
    @Expose
    private String thongBao;
    @SerializedName("danhSachBaiViet")
    @Expose
    private List<BaiViet> danhSachBaiViet = null;
    @SerializedName("danhSachBinhLuan")
    @Expose
    private List<BinhLuan> danhSachBinhLuan = null;
    @SerializedName("danhSachMatHang")
    @Expose
    private List<MatHang> danhSachMatHang = null;
    @SerializedName("danhSachNguoiDung")
    @Expose
    private List<NguoiDung> danhSachNguoiDung = null;
    @SerializedName("baiViet")
    @Expose
    private BaiViet baiViet;
    @SerializedName("nguoiDung")
    @Expose
    private NguoiDung nguoiDung;
    @SerializedName("matHang")
    @Expose
    private MatHang matHang;

    public String getThongBao() {
        return thongBao;
    }

    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
    }

    public List<BaiViet> getDanhSachBaiViet() {
        return danhSachBaiViet;
    }

    public void setDanhSachBaiViet(List<BaiViet> danhSachBaiViet) {
        this.danhSachBaiViet = danhSachBaiViet;
    }

    public List<BinhLuan> getDanhSachBinhLuan() {
        return danhSachBinhLuan;
    }

    public void setDanhSachBinhLuan(List<BinhLuan> danhSachBinhLuan) {
        this.danhSachBinhLuan = danhSachBinhLuan;
    }

    public List<MatHang> getDanhSachMatHang() {
        return danhSachMatHang;
    }

    public void setDanhSachMatHang(List<MatHang> danhSachMatHang) {
        this.danhSachMatHang = danhSachMatHang;
    }
    public List<NguoiDung> getDanhSachNguoiDung() {
        return danhSachNguoiDung;
    }

    public void setDanhSachNguoiDung(List<NguoiDung> danhSachNguoiDung) {
        this.danhSachNguoiDung = danhSachNguoiDung;
    }

    public BaiViet getBaiViet() {
        return baiViet;
    }

    public void setBaiViet(BaiViet baiViet) {
        this.baiViet = baiViet;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public MatHang getMatHang() {
        return matHang;
    }

    public void setMatHang(MatHang matHang) {
        this.matHang = matHang;
    }
}

