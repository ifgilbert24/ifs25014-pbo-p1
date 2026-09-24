import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    private static final int MENIT_PER_HARI = 1440;

    public static void main(String[] args) {
        ArrayList<Integer> data = bacaData();
        if (data.isEmpty()) return;

        Collections.sort(data);
        hitungStatistik(data);
    }

    private static ArrayList<Integer> bacaData() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> data = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("--")) break;
            if (line.isEmpty()) continue;

            try {
                data.add(Integer.parseInt(line));
            } catch (NumberFormatException e) {
                // input tidak valid diabaikan sesuai spesifikasi
            }
        }
        sc.close();
        return data;
    }

    private static void hitungStatistik(ArrayList<Integer> data) {
        int tertinggi = data.get(data.size() - 1);
        int terendah = data.get(0);

        int currentVal = data.get(0);
        int currentFreq = 1;

        int maxFreq = -1, terbanyakVal = 0;
        int minFreq = Integer.MAX_VALUE, tersedikitVal = 0;

        long maxSum = Long.MIN_VALUE;
        int maxJumlahVal = 0, maxJumlahFreq = 0;

        long minSum = Long.MAX_VALUE;
        int minJumlahVal = 0, minJumlahFreq = 0;

        for (int i = 1; i <= data.size(); i++) {
            boolean isLast = (i == data.size());

            if (!isLast && data.get(i) == currentVal) {
                currentFreq++;
            } else {
                long currentSum = (long) currentVal * currentFreq;

                if (currentFreq > maxFreq || (currentFreq == maxFreq && currentVal > terbanyakVal)) {
                    maxFreq = currentFreq;
                    terbanyakVal = currentVal;
                }

                if (currentFreq < minFreq || (currentFreq == minFreq && currentVal < tersedikitVal)) {
                    minFreq = currentFreq;
                    tersedikitVal = currentVal;
                }

                if (currentSum > maxSum || (currentSum == maxSum && currentVal > maxJumlahVal)) {
                    maxSum = currentSum;
                    maxJumlahVal = currentVal;
                    maxJumlahFreq = currentFreq;
                }

                if (currentSum < minSum || (currentSum == minSum && currentVal < minJumlahVal)) {
                    minSum = currentSum;
                    minJumlahVal = currentVal;
                    minJumlahFreq = currentFreq;
                }

                if (!isLast) {
                    currentVal = data.get(i);
                    currentFreq = 1;
                }
            }
        }

        cetakOutput(tertinggi, terendah, terbanyakVal, maxFreq, tersedikitVal, minFreq,
                    maxJumlahVal, maxJumlahFreq, maxSum, minJumlahVal, minJumlahFreq, minSum);
    }

    private static void cetakOutput(int max, int min, int bVal, int bFreq, int sVal, int sFreq,
                                    int maxJVal, int maxJFreq, long maxSum, int minJVal, int minJFreq, long minSum) {
        System.out.println("Tertinggi: " + max);
        System.out.println("Terendah: " + min);
        System.out.println("Terbanyak: " + bVal + " (" + bFreq + "x)");
        System.out.println("Tersedikit: " + sVal + " (" + sFreq + "x)");
        System.out.println("Jumlah Tertinggi: " + maxJVal + " * " + maxJFreq + " = " + maxSum);
        System.out.println("Jumlah Terendah: " + minJVal + " * " + minJFreq + " = " + minSum);
    }
}