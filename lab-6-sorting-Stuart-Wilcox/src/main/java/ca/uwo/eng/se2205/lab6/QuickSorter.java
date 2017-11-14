package ca.uwo.eng.se2205.lab6;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Stu on 2017-03-17.
 */
public class QuickSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        //pick a random element x and partition S into:
        //  L elements less than x
        //  E elements equal to x
        //  G elements greater than x
        //Recur
        //Conquer
        quickSort(sort, 0, sort.size()-1, comparator);
    }

    private<E> void quickSort(DelayedList<E> list, int left, int right, DelayedComparator<E> comparator){
        if(left<right){
            int pos = partition(list, left, right, comparator);
            quickSort(list, left, pos-1, comparator);
            quickSort(list, pos+1, right, comparator);
        }
    }

    private<E> int partition(DelayedList<E> list, int left, int right, DelayedComparator<E> comparator){
        E pivot = list.get(right);
        int small = left-1;

        for(int k = left; k<right; k++){
            if(comparator.compare(list.get(k), pivot) < 0){
                small++;
                swap(list, k, small);
            }
        }

        swap(list, right, small+1);
        return small+1;
    }

    private<E> void swap(DelayedList<E> sort, int pos1, int pos2){
        E temp = sort.get(pos1);
        sort.set(pos1, sort.get(pos2));
        sort.set(pos2, temp);
    }

    @Override
    public String toString() {
        return "Quick";
    }
}
