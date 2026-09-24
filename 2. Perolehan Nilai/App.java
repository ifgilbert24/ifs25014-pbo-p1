import java.util.Scanner;
import java.util.Locale;

public class App {
    private static final int JUMLAH_KOMPONEN = 6;
    private static final int TOTAL_BOBOT = 100;
    private static final double SKALA_NILAI = 100.0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] bobot = new int[JUMLAH_KOMPONEN];
        int[] perolehan = new int[JUMLAH_KOMPONEN];

        bacaPerolehanNilai(sc, bobot, perolehan);
        sc.close();

        int sumBobot = 0;
        for (int b : bobot) {
            sumBobot += b;
        }

        if (sumBobot != TOTAL_BOBOT) {
            System.out.println("Total bobot harus 100");
            return;
        }

        hitungDanCetakNilaiAkhir(bobot, perolehan);
    }

    private static void bacaPerolehanNilai(Scanner sc, int[] bobot, int[] perolehan) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            try {
                String symbol = parts[0].trim();
                int b = Integer.parseInt(parts[1].trim());
                int p = Integer.parseInt(parts[2].trim());

                int idx = getSymbolIndex(symbol);
                if (idx == -1) {
                    System.out.println("Simbol tidak dikenal");
                    continue;
                }

                if (p > b) p = b;
                if (p < 0) p = 0;

                bobot[idx] += b;
                perolehan[idx] += p;
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
            }
        }
    }

    private static void hitungDanCetakNilaiAkhir(int[] bobot, int[] perolehan) {
        System.out.println("Perolehan Nilai:");
        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        double nilaiAkhir = 0;

        for (int i = 0; i < JUMLAH_KOMPONEN; i++) {
            double perolehan100 = 0;
            if (bobot[i] > 0) {
                perolehan100 = ((double) perolehan[i] * SKALA_NILAI) / bobot[i];
            }

            double kontribusi = (perolehan100 / SKALA_NILAI) * bobot[i];
            nilaiAkhir += kontribusi;

            System.out.printf(Locale.US, ">> %s: %.0f/100 (%.2f/%d)\n", names[i], perolehan100, kontribusi, bobot[i]);
        }

        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f\n", nilaiAkhir);

        nilaiAkhir = Math.round(nilaiAkhir * SKALA_NILAI) / SKALA_NILAI;
        System.out.println(">> Grade: " + konversiGrade(nilaiAkhir));
    }

    private static String konversiGrade(double nilaiAkhir) {
        if (nilaiAkhir >= 79.5) return "A";
        if (nilaiAkhir >= 72.00) return "AB";
        if (nilaiAkhir >= 64.50) return "B";
        if (nilaiAkhir >= 57.00) return "BC";
        if (nilaiAkhir >= 49.50) return "C";
        if (nilaiAkhir >= 34.00) return "D";
        return "E";
    }

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