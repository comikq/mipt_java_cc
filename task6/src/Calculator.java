public class Calculator<T extends Number> {
    public double sum(T a, T b) {
        if (a == null || b == null) {
            return Double.NaN;
        }
        return a.doubleValue() + b.doubleValue();
    }

    public double subtract(T a, T b) {
        if (a == null || b == null) {
            return Double.NaN;
        }
        return a.doubleValue() - b.doubleValue();
    }

    public double multiply(T a, T b) {
        if (a == null || b == null) {
            return Double.NaN;
        }
        return a.doubleValue() * b.doubleValue();
    }

    public double divide(T a, T b) {
        if (a == null || b == null || b.doubleValue() == 0) {
            return Double.NaN;
        }
        return a.doubleValue() / b.doubleValue();
    }

    public static void main(String[] args) {

        final Calculator<Integer> intCalc = new Calculator<>();
        System.out.println(intCalc.sum(5, 3)); // 8.0
        System.out.println(intCalc.subtract(5, 3));
        System.out.println(intCalc.multiply(5, null));
        System.out.println(intCalc.divide(5, 0));
        System.out.println(intCalc.divide(null, 2));
        final Calculator<Double> doubleCalc = new Calculator<>();
        System.out.println(doubleCalc.divide(10.0, 4.0)); // 2.5
        System.out.println(doubleCalc.sum(10.0, 4.0));
        System.out.println(doubleCalc.divide(100.0, 0.0));
        System.out.println(doubleCalc.sum(null, 3.0));
        System.out.println(doubleCalc.divide(1.0, -0.0));

        final Calculator<Long> longCalc = new Calculator<>();
        System.out.println(longCalc.divide(5L, 0L));
    }
}
