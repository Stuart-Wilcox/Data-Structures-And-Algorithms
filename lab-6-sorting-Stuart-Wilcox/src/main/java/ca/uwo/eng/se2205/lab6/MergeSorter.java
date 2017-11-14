package ca.uwo.eng.se2205.lab6;

/**
 * Created by Stu on 2017-03-17.
 */
public class MergeSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        //divide sort into 2 sub lists
        //recur on each subset
        //merge subsets back into original

        //COPIED FROM SLIDES!

        if(sort.size() < 2){
            return;
        }

        int mid = sort.size()/2;

        DelayedList<E> l1 = sort.subList(0, mid);
        DelayedList<E> l2 = sort.subList(mid, sort.size());

        sort(l1, comparator);
        sort(l2, comparator);

        merge(sort, l1, l2, comparator);
    }

    public<E> void merge(DelayedList<E> sort, DelayedList<E> l1, DelayedList<E> l2, DelayedComparator<E> comparator){

        //COPIED FROM SLIDES!

        int i = 0, j = 0;
        while (i+j < sort.size()){
            if(j ==  l2.size() || (i < l1.size() && comparator.compare(l1.get(i), l2.get(j))<0)){
                sort.set(i+j, l1.get(i++));
            }
            else{
                sort.set(i+j, l2.get(j++));
            }
        }
    }

    @Override
    public String toString() {
        return "Merge";
    }
}
