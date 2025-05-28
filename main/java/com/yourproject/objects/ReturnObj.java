package main.java.com.yourproject.objects;


public class ReturnObj implements EvaluatorObject {
    private final EvaluatorObject value;

    public ReturnObj(EvaluatorObject value) {
        this.value = value;
    }

    public EvaluatorObject getValue() { return value; }
    @Override public String type() { return "RETURN"; }
    @Override public String inspect() { return value.inspect(); }
}
