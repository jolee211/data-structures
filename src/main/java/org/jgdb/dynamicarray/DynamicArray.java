package org.jgdb.dynamicarray;

import java.util.Iterator;

/**
 * A generic dynamic array implementation
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {
    private T[] array;
    private int len = 0;    // length that the user thinks the array is
    private int capacity = 0;   // actual array size

    public DynamicArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }

        this.capacity = capacity;
        array = (T[]) new Object[capacity];
    }

    public DynamicArray() {
        this(16);
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return array[index];
    }

    public void set(int index, T elem) {
        array[index] = elem;
    }

    public void clear() {
        for (int i = 0; i < len; i++) {
            array[i] = null;
        }

        len = 0;
    }

    public void add(T elem) {
        if (len + 1 >= capacity) {
            if (capacity == 0) {
                capacity = 1;
            } else {
                capacity *= 2;  // double the size
            }

            T[] new_array = (T[]) new Object[capacity];
            for (int i = 0; i < len; i++) {
                new_array[i] = array[i];
            }

            array = new_array;  // we added extra nulls to the end
        }

        array[len++] = elem;
    }

    /**
     * @param rmIndex
     * @return
     */
    public T removeAt(int rmIndex) {
        if (rmIndex >= len || rmIndex < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        T data = array[rmIndex];
        T[] new_array = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == rmIndex) {
                j--;    // skip over rmIndex by fixing j temporarily
            } else {
                new_array[j] = array[i];
            }
        }

        array = new_array;
        capacity = --len;
        return data;
    }

    private int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (obj == null) {
                if (array[i] == null) {
                    return i;
                }
            } else {
                if (obj.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        removeAt(index);
        return true;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0) {
            return "[]";
        } else {
            StringBuffer sb = new StringBuffer(len).append("[");
            for (int i = 0; i < len - 1; i++) {
                sb.append(array[i] + ", ");
            }
            return sb.append(array[len - 1] + "]").toString();
        }
    }
}
