package main.java.com.yourproject.objects;


public class ErrorObj implements EvaluatorObject {
    private final String message;

    public ErrorObj(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    @Override public String type() { return "ERROR"; }
    @Override public String inspect() { return "ERROR: " + message; }
}
