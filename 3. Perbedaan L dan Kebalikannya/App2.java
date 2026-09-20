import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Proteksi guard clause
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }

        int n = sc.nextInt();
        int[][] matrix = new int[n][n];

        // Baca seluruh elemen matriks
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        sc.close();

        // 1. Kalkulasi Nilai Tengah
        int nilaiTengah = 0;
        if (n == 1) {
            nilaiTengah = matrix[0][0];
        } else if (n % 2 != 0) { // Jika ganjil
            nilaiTengah = matrix[n / 2][n / 2];
        } else { // Jika genap
            int r = n / 2;
            int c = n / 2;
            nilaiTengah = matrix[r - 1][c - 1] + matrix[r - 1][c] + 
                          matrix[r][c - 1] + matrix[r][c];
        }

        // 2. Handling Kasus Khusus (1x1 dan 2x2)
        if (n < 3) {
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + nilaiTengah);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + nilaiTengah);
        } else {
            // 3. Kalkulasi Matriks >= 3x3
            int L = 0;
            int revL = 0;

            // Hitung Nilai L: Kolom 0 + Baris Terakhir (kecuali pojok kanan)
            for (int i = 0; i < n; i++) L += matrix[i][0];
            for (int j = 1; j < n - 1; j++) L += matrix[n - 1][j];

            // Hitung Kebalikan L: Kolom N-1 + Baris Pertama (kecuali pojok kiri)
            for (int i = 0; i < n; i++) revL += matrix[i][n - 1];
            for (int j = 1; j < n - 1; j++) revL += matrix[0][j];

            // 4. Kalkulasi Perbedaan & Dominan
            int perbedaan = Math.abs(L - revL);
            int dominan = (perbedaan == 0) ? nilaiTengah : Math.max(L, revL);

            // Output
            System.out.println("Nilai L: " + L);
            // Menggunakan huruf 'k' kecil sesuai dengan mayoritas test case di dokumen
            System.out.println("Nilai Kebalikan L: " + revL); 
            System.out.println("Nilai Tengah: " + nilaiTengah);
            System.out.println("Perbedaan: " + perbedaan);
            System.out.println("Dominan: " + dominan);
        }
    }
}