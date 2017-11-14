package ca.uwo.eng.se2205.lab6;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Stu on 2017-03-17.
 */
public class SorterTesting {
    private static final List<Integer> integerList = initList();
    DelayedComparator<Integer> comparator;
    DelayedList<Integer> list;

    @Test
    public void SelectionSorterTesting(){
        initList();//must be done before
        init();//   any testing can occur


        SelectionSorter sorter = new SelectionSorter();//make a new sorter instance
        sorter.sort(list, comparator);//call the sort function

        Integer curr = list.get(0);
        Integer prev = curr;
        for(int i = 0; i<list.size()-1; i++){
            curr = list.get(i+1);
            assertTrue(curr.compareTo(prev) >= 0);//make sure each element is greater/equal to previous
            prev = list.get(i+1);
        }
    }
    @Test
    public void InsertionSorterTesting(){
        initList();//must be done before
        init();//   any testing can occur

        InsertionSorter sorter = new InsertionSorter();//make new sorter instance
        sorter.sort(list, comparator);//call the sort function

        Integer curr = list.get(0);
        Integer prev = curr;
        for(int i = 0; i<list.size()-1; i++){
            curr = list.get(i+1);
            assertTrue(curr.compareTo(prev) >= 0);//make sure each element is greater/equal to previous
            prev = list.get(i+1);
        }
    }
    @Test
    public void QuickSorterTesting(){
        initList();//must be done before
        init();//   any testing can occur

        QuickSorter sorter = new QuickSorter();//make new sorter instance
        sorter.sort(list, comparator);//call the sort function

        Integer curr = list.get(0);
        Integer prev = curr;
        for(int i = 0; i<list.size()-1; i++){
            curr = list.get(i+1);
            assertTrue(curr.compareTo(prev) >= 0);//make sure each element is greater/equal to previous
            prev = list.get(i+1);
        }
    }
    @Test
    public void MergeSorterTesting(){
        initList();//must be done before
        init();//   any testing can occur

        MergeSorter sorter = new MergeSorter();//make new sorter instance
        sorter.sort(list, comparator);//call the sort function

        Integer curr = list.get(0);
        Integer prev = curr;
        for(int i = 0; i<list.size()-1; i++){
            curr = list.get(i+1);
            assertTrue(curr.compareTo(prev) >= 0);//make sure each element is greater/equal to previous
            prev = list.get(i+1);
        }
    }

    private static ArrayList<Integer> initList(){
        ArrayList<Integer> myList = new ArrayList<>();
        for(int i = 0; i<20;i++){
            myList.add(i);
            myList.add((-i));
            //myList.add(2*i);
            //myList.add((int)Math.pow(i,2.5));
        }
        return myList;
    }

    private void init(){
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return integer-t1;
            }
        };
        comparator = DelayedComparator.create(c, Delayed.Time.Fast);
        list = DelayedList.create(Delayed.Time.Normal, integerList);
    }

}

