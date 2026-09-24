import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine())
            return;

        String nim = scanner.nextLine().trim();
        scanner.close();

        if (nim.length() != 8) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        prosesDataNIM(nim);
    }

    private static void prosesDataNIM(String nim) {
        String prefix = nim.substring(0, 3);
        String prodi = parseProdi(prefix);

        if (prodi == null) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        try {
            int angkatan = Integer.parseInt("20" + nim.substring(3, 5));
            int urutan = Integer.parseInt(nim.substring(5, 8));
            cetakInformasi(nim, prodi, angkatan, urutan);
        } catch (NumberFormatException e) {
            System.out.println("NIM tidak valid");
        }
    }

    private static String parseProdi(String prefix) {
        switch (prefix) {
            case "11S":
                return "Sarjana Informatika";
            case "12S":
                return "Sarjana Sistem Informasi";
            case "13S":
                return "Sarjana Teknik Elektro";
            case "21S":
                return "Sarjana Manajemen Rekayasa";
            case "22S":
                return "Sarjana Teknik Metalurgi";
            case "31S":
                return "Sarjana Teknik Bioproses";
            case "32S":
                return "Sarjana Bioteknologi";
            case "114":
                return "Diploma 4 Teknologi Rekayasa Perangkat Lunak";
            case "113":
                return "Diploma 3 Teknologi Informasi";
            case "133":
                return "Diploma 3 Teknologi Komputer";
            default:
                return null;
        }
    }

    private static void cetakInformasi(String nim, String prodi, int angkatan, int urutan) {
        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}