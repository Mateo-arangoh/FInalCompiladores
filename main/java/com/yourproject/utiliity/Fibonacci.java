package main.java.com.yourproject.utiliity;

public class Fibonacci {
    public static int calculate(int n) {
        if (n == 1 || n == 2) return 1;
        return calculate(n - 1) + calculate(n - 2);
    }
}
