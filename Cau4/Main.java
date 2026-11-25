import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
interface MienGiam {
    long tinhGiam(long hocPhi);
}
class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.round(hocPhi * 0.5);
    }
}
class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.round(hocPhi * 0.7);
    }
}
class HocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, 3000000L);
    }
}
abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam chinhSach;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
        this.chinhSach = null;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getMaHe() {
        return maHe;
    }

    public void setChinhSach(MienGiam chinhSach) {
        this.chinhSach = chinhSach;
    }

    public abstract long hocPhi();

    public long soTienGiam() {
        if (chinhSach == null) {
            return 0L;
        }
        return chinhSach.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
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
            Map<String, SinhVien> mapSV = new HashMap<>();

            for (int i = 0; i < n; i++) {
                String maHe = sc.next();
                String maSV = sc.next();
                int soTinChi = sc.nextInt();

                SinhVien sv = null;
                if (maHe.equals("CQ")) {
                    sv = new SinhVienCQ(maSV, soTinChi);
                } else if (maHe.equals("CLC")) {
                    sv = new SinhVienCLC(maSV, soTinChi);
                } else if (maHe.equals("LT")) {
                    sv = new SinhVienLT(maSV, soTinChi);
                }

                if (sv != null) {
                    danhSach.add(sv);
                    mapSV.put(maSV, sv);
                }
            }

            int m = sc.nextInt();
            List<String> notFoundList = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                String maSV = sc.next();
                String maCS = sc.next();

                SinhVien sv = mapSV.get(maSV);
                if (sv == null) {
                    notFoundList.add("NOT FOUND " + maSV);
                } else {
                    if (maCS.equals("CS")) {
                        sv.setChinhSach(new ConChinhSach());
                    } else if (maCS.equals("HN")) {
                        sv.setChinhSach(new HoNgheo());
                    } else if (maCS.equals("HB")) {
                        sv.setChinhSach(new HocBong());
                    }
                }
            }

            for (String msg : notFoundList) {
                System.out.println(msg);
            }

            for (SinhVien sv : danhSach) {
                System.out.println(sv);
            }

            String[] cacHe = {"CQ", "CLC", "LT"};
            for (String he : cacHe) {
                int count = 0;
                long tongNop = 0;
                for (SinhVien sv : danhSach) {
                    if (sv.getMaHe().equals(he)) {
                        count++;
                        tongNop += sv.phaiNop();
                    }
                }
                if (count > 0) {
                    System.out.println(he + " " + count + " " + tongNop);
                }
            }
        }
        sc.close();
    }
}