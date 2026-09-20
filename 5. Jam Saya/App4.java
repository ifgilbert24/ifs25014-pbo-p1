import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1. Parsing & Validasi Jam Awal
        if (!sc.hasNextLine()) {
            sc.close();
            return;
        }
        
        String startLine = sc.nextLine().trim();
        String[] timeParts = startLine.split(":");
        
        if (timeParts.length != 2) {
            System.out.println("Jam tidak valid");
            sc.close();
            return;
        }
        
        int startH = 0, startM = 0;
        try {
            startH = Integer.parseInt(timeParts[0].trim());
            startM = Integer.parseInt(timeParts[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Jam tidak valid");
            sc.close();
            return;
        }
        
        // Rentang jam 0-23 dan menit 0-59
        if (startH < 0 || startH > 23 || startM < 0 || startM > 59) {
            System.out.println("Jam tidak valid");
            sc.close();
            return;
        }
        
        // 2. Inisialisasi State Tracker
        int currentMinutes = (startH * 60) + startM;
        long totalShift = 0;
        int pergantianHari = 0;
        
        // 3. Traversal Perintah Geser
        // 3. Traversal Perintah Geser
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            
            // PERBAIKAN: Pakai tiga tanda hubung (---) sesuai test case
            if (line.equals("---")) {
                break;
            }
            
            if (line.isEmpty()) continue;
            
            
            // Validasi string harus diawali '+' atau '-'
            if (!line.startsWith("+") && !line.startsWith("-")) {
                System.out.println("Perintah tidak valid");
                continue;
            }
            
            int shift = 0;
            try {
                shift = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak valid");
                continue;
            }
            
            // Akumulasi total geser mentah
            totalShift += shift;
            currentMinutes += shift;
            
            // 4. Normalisasi Rentang 0-1439 & Kalkulasi Pergantian Hari
            while (currentMinutes >= 1440) {
                currentMinutes -= 1440;
                pergantianHari++;
            }
            
            while (currentMinutes < 0) {
                currentMinutes += 1440;
                pergantianHari++;
            }
        }
        sc.close();
        
        // 5. Cetak Output Format 2-Digit
        System.out.printf("Jam Awal: %02d:%02d\n", startH, startM);
        
        int endH = currentMinutes / 60;
        int endM = currentMinutes % 60;
        System.out.printf("Jam Akhir: %02d:%02d\n", endH, endM);
        
        // Formatting Total Menit (Tambahin '+' kalau positif)
        if (totalShift > 0) {
            System.out.println("Total Menit: +" + totalShift);
        } else {
            System.out.println("Total Menit: " + totalShift);
        }
        
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}