public class Pair<K, V> {

    private K key;
    private V value;

    public static void main(String[] args) {
        final Pair<String, Integer> pair = new Pair<>("Age", 25);
        System.out.println(pair); // Ожидаем: Pair{key=Age, value=25}
        if (pair.toString().equals("Pair{key=Age, value=25}")) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");
        }
        final Pair<Integer, String> swapped = pair.swap();
        System.out.println(swapped); // Ожидаем: Pair{key=25, value=Age}
        if (swapped.toString().equals("Pair{key=25, value=Age}")) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");
        }
        final Pair<Object, Object> nullPair = new Pair<>(null, null);
        System.out.println(nullPair); // Ожидаем: Pair{key=null, value=null}
        if (nullPair.toString().equals("Pair{key=null, value=null}")) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");
        }
        final Pair<Object, Object> nullSwapped = nullPair.swap();
        System.out.println(nullSwapped); // Ожидаем: Pair{key=null, value=null}
        if (nullSwapped.toString().equals("Pair{key=null, value=null}")) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");
        }
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Pair<V, K> swap() {
        return new Pair<>(value, key);
    }

    @Override
    public String toString() {
        return "Pair{key=" + key + ", value=" + value + "}";
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}