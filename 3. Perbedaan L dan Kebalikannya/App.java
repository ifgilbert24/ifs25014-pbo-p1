import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }

        int n = sc.nextInt();
        int[][] matrix = bacaMatriks(sc, n);
        sc.close();

        prosesMatriks(matrix, n);
    }

    private static int[][] bacaMatriks(Scanner sc, int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }

    private static void prosesMatriks(int[][] matrix, int n) {
        int nilaiTengah = hitungNilaiTengah(matrix, n);

        if (n < 3) {
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + nilaiTengah);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + nilaiTengah);
            return;
        }

        hitungDanCetakPolaL(matrix, n, nilaiTengah);
    }

    private static int hitungNilaiTengah(int[][] matrix, int n) {
        if (n == 1) return matrix[0][0];
        if (n % 2 != 0) return matrix[n / 2][n / 2];
        
        int r = n / 2;
        int c = n / 2;
        return matrix[r - 1][c - 1] + matrix[r - 1][c] + matrix[r][c - 1] + matrix[r][c];
    }

    private static void hitungDanCetakPolaL(int[][] matrix, int n, int nilaiTengah) {
        int L = 0;
        int revL = 0;

        for (int i = 0; i < n; i++) L += matrix[i][0];
        for (int j = 1; j < n - 1; j++) L += matrix[n - 1][j];

        for (int i = 0; i < n; i++) revL += matrix[i][n - 1];
        for (int j = 1; j < n - 1; j++) revL += matrix[0][j];

        int perbedaan = Math.abs(L - revL);
        int dominan = (perbedaan == 0) ? nilaiTengah : Math.max(L, revL);

        System.out.println("Nilai L: " + L);
        System.out.println("Nilai Kebalikan L: " + revL); 
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}