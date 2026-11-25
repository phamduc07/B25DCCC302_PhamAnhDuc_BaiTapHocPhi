import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SinhVien {
    private String maSV;
    private String hoTen;
    private String ngaySinh;

    public SinhVien(String maSV, String hoTen, String ngaySinh) {
        this.maSV = maSV.trim();
        this.hoTen = chuanHoaTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    private String chuanHoaTen(String ten) {
        String[] words = ten.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                sb.append(Character.toUpperCase(words[i].charAt(0)))
                  .append(words[i].substring(1));
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    private String chuanHoaNgaySinh(String ns) {
        String[] parts = ns.trim().split("/");
        String d = parts[0].length() == 1 ? "0" + parts[0] : parts[0];
        String m = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
        String y = parts[2];
        return d + "/" + m + "/" + y;
    }

    @Override
    public String toString() {
        return maSV + " " + hoTen + " " + ngaySinh;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            int n = Integer.parseInt(sc.nextLine().trim());
            List<SinhVien> danhSach = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String ma = sc.nextLine();
                String ten = sc.nextLine();
                String ns = sc.nextLine();
                danhSach.add(new SinhVien(ma, ten, ns));
            }
            for (SinhVien sv : danhSach) {
                System.out.println(sv);
            }
        }
        sc.close();
    }
}