import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> data = new ArrayList<>();
        
        // Parsing input sampai ketemu "--"
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("--")) {
                break;
            }
            if (line.isEmpty()) continue;
            
            try {
                data.add(Integer.parseInt(line));
            } catch (NumberFormatException e) {
                // Abaikan baris yang bukan angka
            }
        }
        sc.close();
        
        // Proteksi Input Kosong: Langsung terminate tanpa output
        if (data.isEmpty()) return;
        
        // 1. Cari Tertinggi & Terendah absolut (O(N))
        int tertinggi = data.get(0);
        int terendah = data.get(0);
        for (int num : data) {
            if (num > tertinggi) tertinggi = num;
            if (num < terendah) terendah = num;
        }
        
        // 2. Sorting array untuk grouping frekuensi (O(N log N))
        Collections.sort(data);
        
        // Inisialisasi variabel tracker statistik
        int currentVal = data.get(0);
        int currentFreq = 1;
        
        int maxFreq = -1;
        int terbanyakVal = 0;
        
        int minFreq = Integer.MAX_VALUE;
        int tersedikitVal = 0;
        
        // Pakai tipe long untuk sum buat jaga-jaga integer overflow
        long maxSum = Long.MIN_VALUE; 
        int maxJumlahVal = 0;
        int maxJumlahFreq = 0;
        
        long minSum = Long.MAX_VALUE;
        int minJumlahVal = 0;
        int minJumlahFreq = 0;
        
        // 3. State Machine Traversal (O(N))
        // Iterasi sampai size() inklusif untuk ngebungkus kalkulasi elemen terakhir
        for (int i = 1; i <= data.size(); i++) {
            boolean isLast = (i == data.size());
            
            // Kalau angkanya masih sama, tambahin frekuensinya
            if (!isLast && data.get(i) == currentVal) {
                currentFreq++;
            } else {
                // Kalkulasi saat angka berubah atau udah di ujung array
                long currentSum = (long) currentVal * currentFreq;
                
                // Terbanyak: Frekuensi lebih besar, atau seri tapi ambil nilai lebih besar
                if (currentFreq > maxFreq || (currentFreq == maxFreq && currentVal > terbanyakVal)) {
                    maxFreq = currentFreq;
                    terbanyakVal = currentVal;
                }
                
                // Tersedikit: Frekuensi lebih kecil, atau seri tapi ambil nilai lebih kecil
                if (currentFreq < minFreq || (currentFreq == minFreq && currentVal < tersedikitVal)) {
                    minFreq = currentFreq;
                    tersedikitVal = currentVal;
                }
                
                // Jumlah Tertinggi: Sum lebih besar, atau seri tapi ambil nilai lebih besar
                if (currentSum > maxSum || (currentSum == maxSum && currentVal > maxJumlahVal)) {
                    maxSum = currentSum;
                    maxJumlahVal = currentVal;
                    maxJumlahFreq = currentFreq;
                }
                
                // Jumlah Terendah: Sum lebih kecil, atau seri tapi ambil nilai lebih kecil
                if (currentSum < minSum || (currentSum == minSum && currentVal < minJumlahVal)) {
                    minSum = currentSum;
                    minJumlahVal = currentVal;
                    minJumlahFreq = currentFreq;
                }
                
                // Reset tracker untuk angka berikutnya
                if (!isLast) {
                    currentVal = data.get(i);
                    currentFreq = 1;
                }
            }
        }
        
        // 4. Print Output Akhir
        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyakVal + " (" + maxFreq + "x)");
        System.out.println("Tersedikit: " + tersedikitVal + " (" + minFreq + "x)");
        System.out.println("Jumlah Tertinggi: " + maxJumlahVal + " * " + maxJumlahFreq + " = " + maxSum);
        System.out.println("Jumlah Terendah: " + minJumlahVal + " * " + minJumlahFreq + " = " + minSum);
    }
}