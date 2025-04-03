package io.github.atholton.sigmath.util;

import java.util.Objects;

public final class Arrays2 {
    private Arrays2() {
        throw new AssertionError();
    }

    public static boolean contains(Object[] array, Object test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(Object[] array, Object test) {
        for (int i = 0; i < array.length; i++) {
            if(Objects.equals(array[i], test)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(int[] array, int test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(int[] array, int test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(long[] array, long test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(long[] array, long test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(double[] array, double test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(double[] array, double test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(float[] array, float test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(float[] array, float test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(short[] array, short test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(short[] array, short test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(byte[] array, byte test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(byte[] array, byte test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(char[] array, char test) {
        return indexOf(array, test) >= 0;
    }

    public static int indexOf(char[] array, char test) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == test) {
                return i;
            }
        }
        return -1;
    }
}
