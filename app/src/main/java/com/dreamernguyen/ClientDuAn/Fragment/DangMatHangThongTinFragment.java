package com.dreamernguyen.ClientDuAn.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.R;

public class DangMatHangThongTinFragment extends Fragment {

    TextView tv, tvBack;
    ListView lvThongTin;
    ArrayAdapter<String> adapter;
    String[] DanhMuc = {"Bất Động Sản", "Xe Cộ", "Đồ Điện Tử", "Sách", "Thời Trang"};
    String[] DanhMucBatDongSan = {"Căn Hộ", "Chung Cư", "Nhà ở", "Văn Phòng, Mặt Bằng Kinh Doanh", "Phòng Trọ"};
    String[] DanhMucXeCo = {"Ô Tô", "Xe Máy", "Xe Tải, Xe Ben", "Xe Điện", "Xe Đạp", "Phụ Tùng", "Phương Tiện Khác"};
    String[] DanhMucDoDienTu = {"Điện Thoại", "Máy Tính Bảng", "LapTop", "Máy Tính Để Bàn", "Máy Ản", "Tivi", "Thiết Bị Đeo Thông Minh"};
    String[] DanhMucSach = {"Sách Giáo Khoa", "Truyện", "Sách Bài Tập"};
    String[] DanhMucThoiTrang = {"Quần Áo ", "Giày Dép", "Túi Xách", "Đồng Hồ, Nước Hoa"};

    String[] ThanhPho = {"Đà Nẵng", "Hà Nội", "Hồ Chí Minh", "Tiền Giang", "Hưng Yên", "Cà Mau", "Đắc Lắc", "Nam Định", "Quảng Ninh", "Đắk Nông", "Hải Dương", "Long An", "Bến Tre", "Đồng Tháp", "Vĩnh Long", "Kiên Giang", "Trà Vinh", "Sóc Trăng", "Bắc Ninh", "Thanh Hoá", "Vũng Tàu", "Đồng Nai", "Bình Dương", "Thái Nguyên", "Thái Bình", "Cần Thơ", "Nghệ An", "Huế", "Bình Phước", "Quảng Nam", "Quảng Ngãi", "Ninh Thuận", "Lào Cai", "Hải Phòng", "An Giang", "Phú Thọ", "Tây Ninh", "Phú Yên"};
    String[] QuanDaNang = {"Cẩm Lệ", "Hải Châu", " Liên Chiểu", "Ngũ Hành Sơn", "Sơn Trà", "Thanh Khê", "Hòa Vang"};
    String[] QuanHaNoi = {"Ba Đình", "Bắc Từ Liên", "Cầu Giấy", "Đống Đa", "Hà Đông", "Hai Bà Trưng", "Hoàn Kiếm", "Hoàng Mai", "Long Biên", "Nam Từ Liêm", "tay ho", "thanh xuan", "ba vi"};
    String[] QuanHoChiMinh = {"Quan 1", "Quan 2", "Quan 3", "Quan 4", "Quan 5", "Quan 6", "Quan 7", "Quan 8", "Quan 9", "Quan 10", "Quan 11", "Quan 12"};

    String[] PhuongCamLe = {"Hòa An", "Hòa Phát", "Hòa Thọ Đông", "Hòa Thọ Tây", "Hòa Xuân", "Khuê Trung"};
    String[] PhuongHaiChau = {"Bình Hiển", "Bình Thuận", "Hải Châu 1", "Hải Châu 2", "Hòa Cường Bắc", "Hòa Cường Nam", "hoa thuan tay", "hoa thuan dong", "nam duong", "phuoc ninh", "thạch thang", "Thanh bình", "thuan phuoc"};
    String[] PhuongLienChieu = {"hoa hiep bac", "hoa hiep nam", "hoa khanh bac", "hoa khanh nam", "hoa minh"};
    String[] PhuongBaDinh = {"Cong vi", "dien bien", "doi can", "giang vo", "kim ma", "lieu giai", "ngoc ha", "ngoc khanh", "nguyen trung truc"};
    String[] PhuongBacTuLiem = {" co nhue 1", "co nhue 2", "dong ngac", "duc thang", "lien mac", "Minh Khai", "Phu dien", ""};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_thong_tin, container, false);

        tvBack = view.findViewById(R.id.tvBack);
        tv = view.findViewById(R.id.tv);
        lvThongTin = view.findViewById(R.id.lvThongTin);


        themThongTin(DangMatHangActivity.viTri);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void themThongTin(int a) {
        switch (a) {

            case 0:
                tvBack.setOnClickListener(v -> {
                    Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(v).navigate(R.id.actionThongTinToDanhMuc);
                });
                if (DangMatHangActivity.DanhMuc.isEmpty()) {
                    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DanhMuc);
                    lvThongTin.setAdapter(adapter);
                    lvThongTin.setOnItemClickListener((parent, view, position, id) -> {
                        DangMatHangActivity.DanhMuc = lvThongTin.getItemAtPosition(position).toString();
                        themDanhMucCon(DangMatHangActivity.DanhMuc);
                    });
                } else {
                    themDanhMucCon(DangMatHangActivity.DanhMuc);
                }
                break;
            case 2:
                tvBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.actionThongTinToDiaChi));
                if (DangMatHangActivity.ThanhPho.isEmpty()) {
                    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ThanhPho);
                    lvThongTin.setAdapter(adapter);
                    lvThongTin.setOnItemClickListener((parent, view, position, id) -> {
                        DangMatHangActivity.ThanhPho = lvThongTin.getItemAtPosition(position).toString();
                        Toast.makeText(getActivity(), DangMatHangActivity.ThanhPho, Toast.LENGTH_SHORT).show();
                        themQuan(DangMatHangActivity.ThanhPho);
                    });
                } else {
                    themQuan(DangMatHangActivity.ThanhPho);
                }
                break;
        }
    }

    public void themDanhMucCon(String a) {
        tvBack.setOnClickListener(v -> {
            DangMatHangActivity.DanhMuc = "";
            themThongTin(0);

        });
        switch (a) {
            case "Bất Động Sản":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucBatDongSan);
                break;
            case "Xe Cộ":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucXeCo);
                break;
            case "Đồ Điện Tử":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucDoDienTu);
                break;
            case "Sách":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucSach);
                break;
            case "Thời Trang":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucThoiTrang);
                break;
        }

        lvThongTin.setAdapter(adapter);

        lvThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Navigation.findNavController(view).navigate(R.id.actionThongTinToDanhMuc);
                DangMatHangActivity.DanhMucCon = lvThongTin.getItemAtPosition(position).toString();
            }
        });
    }

    public void themQuan(String a) {
        tvBack.setOnClickListener(v -> {
            DangMatHangActivity.ThanhPho = "";
            themThongTin(2);
        });
        switch (a) {
            case "Đà Nẵng":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, QuanDaNang);
                break;
            case "Hà Nội":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, QuanHaNoi);
                break;
            case "Hồ Chí Minh":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, QuanHoChiMinh);
                break;
        }
        lvThongTin.setAdapter(adapter);
        lvThongTin.setOnItemClickListener((parent, view, position, id) -> {
            DangMatHangActivity.QuanHuyen = lvThongTin.getItemAtPosition(position).toString();
            themPhuong(DangMatHangActivity.QuanHuyen);
        });
    }

    public void themPhuong(String a) {
        tvBack.setOnClickListener(v -> themQuan(DangMatHangActivity.QuanHuyen));
        switch (a) {
            case "Cẩm Lệ":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PhuongCamLe);
                break;
            case "Hải Châu":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PhuongHaiChau);
                break;
            case "Liên Chiểu":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PhuongLienChieu);
                break;

            case "Ba Đình":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PhuongBaDinh);
                break;
            case "Bắc Từ Liên":

                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PhuongBacTuLiem);
                break;
        }
        lvThongTin.setAdapter(adapter);
        lvThongTin.setOnItemClickListener((parent, view, position, id) -> {
            DangMatHangActivity.PhuongXa = lvThongTin.getItemAtPosition(position).toString();
            Navigation.findNavController(view).navigate(R.id.actionThongTinToDiaChi);
        });
    }
}
