package lab6.server.commands;

import java.util.concurrent.RecursiveAction;

public class Concurrent_MergeSort extends RecursiveAction {
    long low;
    long high;
    long[] arr;

    public Concurrent_MergeSort(long[] arr, long low, long high) {
        this.low = low;
        this.high = high;
        this.arr = arr;
    }

    protected void compute() {
        //System.out.println("called compute with low = "+low+" high = "+high);

        if (low < high) {

            long mid = (low + high) / 2;

            Concurrent_MergeSort lower = new Concurrent_MergeSort(arr, low, mid);

            Concurrent_MergeSort higher = new Concurrent_MergeSort(arr, mid + 1, high);

            invokeAll(lower, higher);

            merge(arr, low, mid, high);


        }
    }

    static void merge(long[] arr, long l, long m, long r) {
        // Find sizes of two subarrays to be merged
        long n1 = m - l + 1;
        long n2 = r - m;

        /* Create temp arrays */
        long[] L = new long[(int) n1];
        long[] R = new long[(int) n2];

        /*Copy data to temp arrays*/
        for (long i = 0; i < n1; ++i)
            L[(int) i] = arr[(int) (l + i)];
        for (long j = 0; j < n2; ++j)
            R[(int) j] = arr[(int) (m + 1 + j)];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        long i = 0, j = 0;

        // Initial index of merged subarry array
        long k = l;
        while (i < n1 && j < n2) {
            if (L[(int) i] <= R[(int) j]) {
                arr[(int) k] = L[(int) i];
                i++;
            } else {
                arr[(int) k] = R[(int) j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[(int) k] = L[(int) i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[(int) k] = R[(int) j];
            j++;
            k++;
        }

    }
}
