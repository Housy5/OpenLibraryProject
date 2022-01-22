package housy.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Library {
    
    private static final String DEFAULT_FILE_NAME = "/housy/lib/words.txt";
    private Set<String> words;
    
    private void initSet(String fileName) {
        words = new HashSet<>();
        try (var in = new Scanner(getClass().getResourceAsStream(fileName))) {
            while (in.hasNext())
                words.add(in.nextLine().toLowerCase());
        }
    }
    
    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Quicksort an array of strings alphabetically
     * @param arr an sorted version of arr.
     */
    public static void quicksort(String[] arr) {
        int pivot = new Random().nextInt(arr.length);
        swap(arr, pivot, arr.length - 1);
        quicksort(arr, 0, arr.length - 1);
    }
    
    private static void quicksort(String[] arr, int low, int high) {
        if (low > high)
            return;
        
        String pivot = arr[high];
        
        int left = low;
        int right = high;
        
        while (left < right) {
            while (pivot.compareTo(arr[left]) >= 0 && left < right)
                left++;
            while (pivot.compareTo(arr[right]) <= 0 && right > left)
                right--;
            
            swap(arr, left, right);
        }
        
        swap(arr, left, high);
        
        quicksort(arr, low, left - 1);
        quicksort(arr, left + 1, high);
    }
    
    public Library() {
        initSet(DEFAULT_FILE_NAME);
    }
    
    public Library(String fileName) {
        initSet(fileName);
    }
    
    /**
     * Returns all the words in the current instance of the library.
     * WARNING: Unordered but O(n)
     * @return all the words stored in the library. 
     */
    public String[] toArray() {
        String[] arr = new String[words.size()];
        words.toArray(arr);
        return arr;
    }
    
    /**
     * toArray() but alphabetically sorted.
     * WARNING: Ordered but O(2n * log(n))
     * @return All the words in the library in a alphabetically ordered fashion.
     */
    public String[] toSortedArray() {
        String[] arr = new String[words.size()];
        words.toArray(arr);
        quicksort(arr);
        return arr;
    }
    
    private String[] resizeArray(String[] arr, int size) {
        if (size <= 0)
            throw new IllegalArgumentException();
        String[] newArray = new String[size];
        for (int i = 0; i < arr.length; i++) {
            if (i < newArray.length)
                newArray[i] = arr[i];
            else
                break;
        }
        return newArray;
    }
    /**
     * O(n)
     * @param arr
     * @param last
     * @return 
     */
    private String[] cut(String[] arr, int last) {
        String[] newArr = new String[arr.length - (arr.length - last)];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }
    
    /**
     * Filters through the library to find strings with a length of max.
     * @param max The maximum amount of characters in a string.
     * @return An array with all Strings that contain <= max characters.
     */
    public String[] filterBySize(int max) {
        if (max <= 0)
            throw new IllegalArgumentException();
        int cap = 32;
        int size = 0;
        String[] arr = new String[cap];
        for (String s : words) {
            if (s.length() <= max) {
                if (size == cap) {
                    arr = resizeArray(arr, cap * 2);
                    cap = arr.length;
                }
                
                arr[size] = s;
                size++;
            }
        }

        return cut(arr, size);
    }
    
    /**
     * Returns a random word.
     * @return a random word.
     */
    public String getRandomString() {
        int index = new Random().nextInt(words.size());
        for (String word : words) {
            if (index == 0)
                return word;
            index--;
        }
        return null;
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public boolean contains(Object o) {
        return words.contains(o);
    }

    public Iterator<String> iterator() {
        return words.iterator();
    }

    public boolean containsAll(Collection<?> c) {
        return words.containsAll(c);
    }

    public Stream<String> stream() {
        return words.stream();
    }
    
    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
