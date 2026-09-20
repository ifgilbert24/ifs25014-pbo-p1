import java.util.Scanner;
import java.util.Locale;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Proteksi awal jika file kosong
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }

        int[] bobot = new int[6];
        int sumBobot = 0;
        
        // Membaca 6 baris pertama untuk bobot komponen
        for (int i = 0; i < 6; i++) {
            bobot[i] = sc.nextInt();
            sumBobot += bobot[i];
        }
        sc.nextLine(); // Membersihkan sisa buffer newline dari nextInt()

        // Validasi total bobot wajib 100
        if (sumBobot != 100) {
            System.out.println("Total bobot harus 100");
            sc.close();
            return;
        }

        // Array akumulator: indeks 0=PA, 1=T, 2=K, 3=P, 4=UTS, 5=UAS
        int[] total = new int[6];
        int[] perolehan = new int[6];

        // Parsing data nilai komponen
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\|");
            
            // Validasi format wajib 3 bagian yang dipisah karakter '|'
            if (parts.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            String symbol = parts[0].trim();
            int b, p;
            
            try {
                b = Integer.parseInt(parts[1].trim());
                p = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            int idx = getSymbolIndex(symbol);
            if (idx == -1) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            // Clamping value sesuai rule spesifikasi
            if (p > b) p = b;
            if (p < 0) p = 0;

            total[idx] += b;
            perolehan[idx] += p;
        }
        sc.close();

        // Proses Perhitungan Nilai Akhir
        System.out.println("Perolehan Nilai:");
        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        double nilaiAkhir = 0;

        for (int i = 0; i < 6; i++) {
            int perolehan100 = 0;
            // Cegah error Division by Zero (NaN) jika total = 0
            if (total[i] > 0) {
                perolehan100 = (perolehan[i] * 100) / total[i]; 
            }
            
            // Kalkulasi kontribusi bobot (wajib konversi ke double via 100.0)
            double kontribusi = (perolehan100 / 100.0) * bobot[i];
            nilaiAkhir += kontribusi;

            // Locale.US memaksa output desimal menggunakan titik (.), bukan koma (,)
            System.out.printf(Locale.US, ">> %s: %d/100 (%.2f/%d)\n", names[i], perolehan100, kontribusi, bobot[i]);
        }

        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f\n", nilaiAkhir);

        nilaiAkhir = Math.round(nilaiAkhir * 100.0) / 100.0;

        // Konversi Skor ke Grade
        String grade = "";
        if (nilaiAkhir >= 79.5) grade = "A";
        else if (nilaiAkhir >= 72.00) grade = "AB";
        else if (nilaiAkhir >= 64.50) grade = "B";
        else if (nilaiAkhir >= 57.00) grade = "BC";
        else if (nilaiAkhir >= 49.50) grade = "C";
        else if (nilaiAkhir >= 34.00) grade = "D";
        else grade = "E";

        System.out.println(">> Grade: " + grade);
    }

    // Method helper buat nyari indeks dari simbol tanpa bikin nested if yang panjang
    private static int getSymbolIndex(String symbol) {
        switch (symbol) {
            case "PA": return 0;
            case "T": return 1;
            case "K": return 2;
            case "P": return 3;
            case "UTS": return 4;
            case "UAS": return 5;
            default: return -1;
        }
    }
}