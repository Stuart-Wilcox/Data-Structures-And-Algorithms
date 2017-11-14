package ca.uwo.eng.se2205.lab6;

/**
 * Created by Stu on 2017-03-17.
 */
public class SelectionSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        if(sort == null || comparator == null){
            return;
        }
        if(sort.size() < 2){
            return;
        }
        //select the smallest element
        //swap the current element
        //repeat until array is sorted
        E smallest = sort.get(0);
        int smallestPosition = 0;
        for(int j = 0; j<sort.size();j++) {
            smallest = sort.get(j);
            smallestPosition = j;
            for (int i = j+1; i < sort.size(); i++) {
                if (comparator.compare(sort.get(i), smallest) < 0) {
                smallest = sort.get(i);smallestPosition = i;
                }
            }
            swap(sort, j, smallestPosition);
        }
    }

    private<E> void swap(DelayedList<E> sort, int pos1, int pos2){
        E temp = sort.get(pos1);
        sort.set(pos1, sort.get(pos2));
        sort.set(pos2, temp);
    }

    @Override
    public String toString() {
        return "Selection";
    }
}
