import java.util.Scanner;
import java.util.Locale;

public class App1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }

        int[] bobot = bacaBobot(sc);
        if (bobot == null) return; 

        int[] total = new int[6];
        int[] perolehan = new int[6];

        bacaPerolehanNilai(sc, total, perolehan, bobot);
        sc.close();

        hitungDanCetakNilaiAkhir(bobot, total, perolehan);
    }

    private static int[] bacaBobot(Scanner sc) {
        int[] bobot = new int[6];
        int sumBobot = 0;
        for (int i = 0; i < 6; i++) {
            bobot[i] = sc.nextInt();
            sumBobot += bobot[i];
        }
        sc.nextLine(); 

        if (sumBobot != 100) {
            System.out.println("Total bobot harus 100");
            sc.close();
            return null;
        }
        return bobot;
    }

    private static void bacaPerolehanNilai(Scanner sc, int[] total, int[] perolehan, int[] bobot) {
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

                total[idx] += b;
                perolehan[idx] += p;
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
            }
        }
    }

    private static void hitungDanCetakNilaiAkhir(int[] bobot, int[] total, int[] perolehan) {
        System.out.println("Perolehan Nilai:");
        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        double nilaiAkhir = 0;

        for (int i = 0; i < 6; i++) {
            double perolehan100 = 0;
            if (total[i] > 0) {
                // Perbaikan potensi integer division
                perolehan100 = ((double) perolehan[i] * 100) / total[i]; 
            }
            
            double kontribusi = (perolehan100 / 100.0) * bobot[i];
            nilaiAkhir += kontribusi;

            System.out.printf(Locale.US, ">> %s: %.0f/100 (%.2f/%d)\n", names[i], perolehan100, kontribusi, bobot[i]);
        }

        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f\n", nilaiAkhir);
        
        nilaiAkhir = Math.round(nilaiAkhir * 100.0) / 100.0;
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