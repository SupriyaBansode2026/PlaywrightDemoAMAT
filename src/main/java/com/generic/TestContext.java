package com.generic;

public class TestContext {

    private static final ThreadLocal<String> testName = new ThreadLocal<>();
    private static final ThreadLocal<Integer> stepCounter =
            ThreadLocal.withInitial(() -> 1);

    public static void setTestName(String name) {
        testName.set(name);
    }

    public static String getTestName() {
        return testName.get();
    }

    public static int nextStep() {
        int step = stepCounter.get();
        stepCounter.set(step + 1);
        return step;
    }

    public static void clear() {
        testName.remove();
        stepCounter.remove();
    }
}
