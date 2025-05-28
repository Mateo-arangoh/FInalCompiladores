package main.java.com.yourproject.utiliity;

public class DoubleSum {
    public static int calculate(int x, int y, int n) {
        int total = 0;
        while (n > 0) {
            total += (x + y) * 2;
            n--;
        }
        return total;
    }
}
