package main.java.com.yourproject.objects;


public class NullObj implements EvaluatorObject {
    public static final NullObj NULL = new NullObj();

    private NullObj() {}

    @Override public String type() { return "NULL"; }
    @Override public String inspect() { return "null"; }
}