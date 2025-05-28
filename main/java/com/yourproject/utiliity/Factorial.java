package main.java.com.yourproject.utiliity;

public class Factorial {
    public static int calculate(int x) {
        return x <= 1 ? 1 : x * calculate(x - 1);
    }
}
