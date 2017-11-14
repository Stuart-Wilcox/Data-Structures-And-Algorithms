package ca.uwo.eng.se2205.lab6;

/**
 * Created by Stu on 2017-03-17.
 */
public class InsertionSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        //get a current element
        //if it is less than the one to the left, swap them

        for(int i = 1; i < sort.size(); i++){
            int j = i, k = i - 1;//get indices for later (current and previous)
            while( k>=0 && comparator.compare(sort.get(j), sort.get(k)) < 0){
                //swap current and previous as needed, moving from back to front of list
                swap(sort, j, k);
                k--;j--;//move backwards on list
            }
        }
    }

    private<E> void swap(DelayedList<E> sort, int pos1, int pos2){
        E temp = sort.get(pos1);
        sort.set(pos1, sort.get(pos2));
        sort.set(pos2, temp);
    }

    @Override
    public String toString() {
        return "Insertion";
    }
}
