import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

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

    public String getMaSV() {
        return maSV;
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

class QuanLyHocPhi {
    private ArrayList<SinhVien> danhSach;
    private Set<String> tapMaSV;

    public QuanLyHocPhi() {
        danhSach = new ArrayList<>();
        tapMaSV = new HashSet<>();
    }

    public void themSinhVien(String maHe, String maSV, String strTinChi) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException(maSV);
        }

        int soTinChi;
        try {
            soTinChi = Integer.parseInt(strTinChi);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException(maSV);
        }

        if (soTinChi < 1 || soTinChi > 30) {
            throw new DuLieuKhongHopLeException(maSV);
        }

        if (tapMaSV.contains(maSV)) {
            throw new DuLieuKhongHopLeException(maSV);
        }

        tapMaSV.add(maSV);
        if (maHe.equals("CQ")) {
            danhSach.add(new SinhVienCQ(maSV, soTinChi));
        } else if (maHe.equals("CLC")) {
            danhSach.add(new SinhVienCLC(maSV, soTinChi));
        } else {
            danhSach.add(new SinhVienLT(maSV, soTinChi));
        }
    }

    public void inKetQua() {
        Collections.sort(danhSach, new Comparator<SinhVien>() {
            @Override
            public int compare(SinhVien a, SinhVien b) {
                long hpA = a.hocPhi();
                long hpB = b.hocPhi();
                if (hpA != hpB) {
                    return Long.compare(hpB, hpA);
                }
                return a.getMaSV().compareTo(b.getMaSV());
            }
        });

        long tong = 0;
        for (SinhVien sv : danhSach) {
            System.out.println(sv);
            tong += sv.hocPhi();
        }
        System.out.println("TONG " + tong);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            int n = Integer.parseInt(sc.nextLine().trim());
            QuanLyHocPhi ql = new QuanLyHocPhi();
            List<String> invalidList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String line = sc.nextLine().trim();
                String[] parts = line.split("\\s+");
                String maHe = parts[0];
                String maSV = parts[1];
                String strTinChi = parts[2];

                try {
                    ql.themSinhVien(maHe, maSV, strTinChi);
                } catch (DuLieuKhongHopLeException e) {
                    invalidList.add("INVALID " + e.getMessage());
                }
            }

            for (String msg : invalidList) {
                System.out.println(msg);
            }
            ql.inKetQua();
        }
        sc.close();
    }
}