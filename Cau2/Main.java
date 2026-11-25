import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSV, int soTinChi) {
        super(maSV, "CQ", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSV, int soTinChi) {
        super(maSV, "CLC", soTinChi);
    }

    @Override
    public long hocPhi() {
        return Math.round(soTinChi * DON_GIA * 1.5);
    }
}

class SinhVienLT extends SinhVien {
    public SinhVienLT(String maSV, int soTinChi) {
        super(maSV, "LT", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + 2000000L;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            List<SinhVien> danhSach = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String maHe = sc.next();
                String maSV = sc.next();
                int soTinChi = sc.nextInt();

                if (maHe.equals("CQ")) {
                    danhSach.add(new SinhVienCQ(maSV, soTinChi));
                } else if (maHe.equals("CLC")) {
                    danhSach.add(new SinhVienCLC(maSV, soTinChi));
                } else if (maHe.equals("LT")) {
                    danhSach.add(new SinhVienLT(maSV, soTinChi));
                }
            }

            for (SinhVien sv : danhSach) {
                System.out.println(sv);
            }
        }
        sc.close();
    }
}