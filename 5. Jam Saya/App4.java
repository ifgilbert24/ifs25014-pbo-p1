import java.util.Scanner;

public class App4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) {
            sc.close();
            return;
        }
        
        String startLine = sc.nextLine().trim();
        int[] jamAwal = parseJam(startLine);
        
        if (jamAwal == null) {
            System.out.println("Jam tidak valid");
            sc.close();
            return;
        }
        
        prosesPergeseranJam(sc, jamAwal[0], jamAwal[1]);
    }

    private static int[] parseJam(String line) {
        String[] timeParts = line.split(":");
        if (timeParts.length != 2) return null;
        
        try {
            int h = Integer.parseInt(timeParts[0].trim());
            int m = Integer.parseInt(timeParts[1].trim());
            
            if (h < 0 || h > 23 || m < 0 || m > 59) return null;
            return new int[]{h, m};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void prosesPergeseranJam(Scanner sc, int startH, int startM) {
        int currentMinutes = (startH * 60) + startM;
        long totalShift = 0;
        int pergantianHari = 0;
        
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;
            
            if (!line.startsWith("+") && !line.startsWith("-")) {
                System.out.println("Perintah tidak valid");
                continue;
            }
            
            try {
                int shift = Integer.parseInt(line);
                totalShift += shift;
                currentMinutes += shift;
                
                // Normalisasi & Tracking Hari
                while (currentMinutes >= 1440) {
                    currentMinutes -= 1440;
                    pergantianHari++;
                }
                while (currentMinutes < 0) {
                    currentMinutes += 1440;
                    pergantianHari++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak valid");
            }
        }
        sc.close();
        
        cetakHasil(startH, startM, currentMinutes, totalShift, pergantianHari);
    }

    private static void cetakHasil(int startH, int startM, int endMinutes, long totalShift, int pergantianHari) {
        System.out.printf("Jam Awal: %02d:%02d\n", startH, startM);
        
        int endH = endMinutes / 60;
        int endM = endMinutes % 60;
        System.out.printf("Jam Akhir: %02d:%02d\n", endH, endM);
        
        String sign = (totalShift > 0) ? "+" : "";
        System.out.println("Total Menit: " + sign + totalShift);
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}