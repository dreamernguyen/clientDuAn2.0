package com.dreamernguyen.ClientDuAn;

import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.BinhLuan;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.Models.ThongBao;
import com.dreamernguyen.ClientDuAn.Models.TinNhan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(MainApplication.HOST) //đang chạy host nếu muốn debug trên visual code thì bỏ localhost vô đây
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //Người dùng
    @FormUrlEncoded
    @POST("nguoiDung/theoDoi")
    Call<DuLieuTraVe> theoDoi(@Field("idNguoiTheoDoi") String idNguoiTheoDoi,@Field("idNguoiDuocTheoDoi") String idNguoiDuocTheoDoi);

    @FormUrlEncoded
    @POST("nguoiDung/huyTheoDoi")
    Call<DuLieuTraVe> huyTheoDoi(@Field("idNguoiTheoDoi") String idNguoiTheoDoi,@Field("idNguoiDuocTheoDoi") String idNguoiDuocTheoDoi);

    @POST("nguoiDung/dangNhap")
    Call<DuLieuTraVe> dangNhap(@Body NguoiDung nguoiDung);

    @FormUrlEncoded
    @POST("nguoiDung/dangKy")
    Call<DuLieuTraVe> dangKy(@Field("hoTen") String hoTen,@Field("soDienThoai") int soDienThoai,@Field("matKhau") String matKhau);

    @FormUrlEncoded
    @POST("nguoiDung/dangNhapGG")
    Call<DuLieuTraVe> dangNhapGG(@Field("email") String email,@Field("avatar") String avatar,@Field("hoTen") String hoTen);

    @GET("nguoiDung/xemTrangCaNhan/{id}")
    Call<DuLieuTraVe> xemTrangCaNhan(@Path("id") String idNguoiDung);

    //Bài viết
    @GET("baiViet/danhSach")
    Call<DuLieuTraVe> danhSachBaiViet();

    @GET("baiViet/dangTheoDoi/{id}")
    Call<DuLieuTraVe> danhSachTheoDoi(@Path("id") String idNguoiDung);

    @POST("baiViet/dangBai/{id}")
    Call<BaiViet> dangBai(@Path("id") String idNguoiDung, @Body BaiViet baiViet);

    @POST("baiViet/capNhat/{id}")
    Call<DuLieuTraVe> capNhatBaiViet(@Path("id") String idBaiViet, @Body BaiViet baiViet);

    @GET("baiViet/chiTiet/{id}")
    Call<DuLieuTraVe> xemChiTiet(@Path("id") String idBaiViet);

    @POST("baiViet/an/{id}")
    Call<DuLieuTraVe> anBaiViet(@Path("id") String id);

    @POST("baiViet/huyAn/{id}")
    Call<DuLieuTraVe> huyAnBaiViet(@Path("id") String id);

    @POST("baiViet/xoa/{id}")
    Call<DuLieuTraVe> xoaBaiViet(@Path("id") String id);

    @GET("baiViet/danhSachYeuThich/:{id}")
    Call<DuLieuTraVe> danhSachYeuThich(@Path("id") String idNguoiDung);

    @FormUrlEncoded
    @POST("baiViet/thich")
    Call<DuLieuTraVe> thichBaiViet(@Field("idBaiViet") String idBaiViet,@Field("idNguoiDung") String idNguoiDung);

    @FormUrlEncoded
    @POST("baiViet/boThich")
    Call<DuLieuTraVe> boThichBaiViet(@Field("idBaiViet") String idBaiViet,@Field("idNguoiDung") String idNguoiDung);

    @FormUrlEncoded
    @POST("baiViet/anKhoiToi")
    Call<DuLieuTraVe> anBaiVietKhoiToi(@Field("idBaiViet") String idBaiViet,@Field("idNguoiDung") String idNguoiDung);

    @FormUrlEncoded
    @POST("baiViet/baoCao")
    Call<DuLieuTraVe> baoCao(@Field("idBaiViet") String idBaiViet,@Field("idNguoiDung") String idNguoiDung,@Field("noiDungBaoCao") String noiDungBaoCao);


    //Bình luận
    @POST("binhLuan/them/{id}")
    Call<DuLieuTraVe> binhLuan(@Path("id") String idNguoiDung, @Body BinhLuan binhLuan);

    @GET("binhLuan/baiViet/{id}")
    Call<DuLieuTraVe> danhSachBinhLuan(@Path("id") String idBaiViet);

    //Tin nhắn
    @FormUrlEncoded
    @POST("tinNhan/xoaTinNhan/{id}")
    Call<DuLieuTraVe> xoaTinNhan(@Path("id") String id,@Field("idNguoiDung") String idNguoiDung);

    @GET("tinNhan/xoaToanCuocTroChuyen/{idNguoiDung}")
    Call<DuLieuTraVe> xoaToanCuocTroChuyen(@Path("idNguoiDung") String idNguoiDung);

    @FormUrlEncoded
    @POST("tinNhan/troChuyen")
    Call<List<TinNhan>> danhSachTinNhan(@Field("idNguoi1") String idNguoi1,@Field("idNguoi2") String idNguoi2);

    @FormUrlEncoded
    @POST("tinNhan/chat")
    Call<DuLieuTraVe> chat(@Field("idNguoiGui") String idNguoiGui,@Field("idNguoiNhan") String idNguoiNhan,@Field("noiDung") String noiDung,@Field("linkAnh") String linkAnh);

    @GET("tinNhan/danhSachLienHe/{id}")
    Call<List<NguoiDung>> danhSachLienHe(@Path("id") String id);

    //Mặt hàng
    @POST("matHang/dangBai")
    Call<MatHang> testMatHang(@Path("idNguoiDung") String idNguoiDung, @Body MatHang matHang);

    @GET("matHang/danhSach")
    Call<DuLieuTraVe> danhSachMatHang();

    @POST("matHang/xoa/{id}")
    Call<DuLieuTraVe> xoaMatHang(@Path("id") String id);

    @POST("matHang/dangMatHang/{id}")
    Call<DuLieuTraVe> dangMatHang(@Path("id") String idNguoiDung,@Body MatHang matHang);

    @POST("matHang/chinhSua/{id}")
    Call<DuLieuTraVe> capNhapMatHang(@Path("id") String idMatHang,@Body MatHang matHang);

    @POST("matHang/chiTiet/{id}")
    Call<DuLieuTraVe> xemChiTietMatHang(@Path("id") String id);

    //Thông báo
    @GET("thongBao/danhSach/{id}")
    Call<List<ThongBao>> danhSachThongBao(@Path("id")String id);
}
