package support;

public class Age {
    private int value;

    public Age(int value) {
        this.value = value;
    }

    public String toString() {
        return Integer.toString(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
