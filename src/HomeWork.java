import java.util.Arrays;

public class HomeWork {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];


    //Первый метод просто бежит по массиву и вычисляет значения.
    public static void method1() {
        Arrays.fill(arr, 1);

        var startTime = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        var stopTime = System.currentTimeMillis();
        var executionTime = stopTime - startTime;

        System.out.printf("method1 execution time %s ms \n", executionTime);
    }


    /**
     * Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
     * Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки
     */
    public static void method2() {
        Arrays.fill(arr, 1);
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        var startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = getThread(a1);
        Thread t2 = getThread(a2);

        t1.start();
        t2.start();

        var result = new float[size];

        System.arraycopy(a1, 0, result, 0, h);
        System.arraycopy(a1, 0, result, h, h);

        var stopTime = System.currentTimeMillis();
        var executionTime = stopTime - startTime;

        System.out.printf("method 2 execution time %s ms \n", executionTime);
    }

    //method helper for execute calculation of equation in single Thread
    private static Thread getThread(float[] a1) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }

        });
        return t1;
    }


    public static void main(String[] args) {
    method1();
    method2();
    }
}
