package ca.uwo.eng.se2205.lab6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Stu on 2017-03-18.
 */
public class Driver {
    public static void main(String[] args){
        BatchRunner batchRunner = new BatchRunner();
        Driver driver = new Driver();
        FileWriter fw;
        try {
            fw = new FileWriter("./results_time.csv", true);
            fw.write("\n\n\n***STARTING NEW TEST***\n\n\n");
            fw.write("Algorithm, n, List Speed, List Ops/Sort, Comparison Speed, Comparison Ops/Sort, Time(ns)\n");
        }catch(IOException e){
            throw new Error("Could not find file");
        }


        for(int i = 0; i<3;i++){
            RunTest runTest;
            //outer loop for controlling DelayedList speed
            Delayed.Time listSpeed = driver.getTime(i);
            for(int j = 0; j<3;j++){
                //loop for controlling DelayedComparator speed
                Delayed.Time compSpeed = driver.getTime(j);
                for(int k = 0;k<2;k++){
                    //loop for controlling how many elements
                    int listSize;
                    if(k==1){listSize=40;}
                    else{listSize=10;}
                    for(int l=0;l<4;l++){
                        runTest = new RunTest(listSpeed, compSpeed, listSize, driver.getSorter(l), fw);
                        batchRunner.enqueue(runTest);
                        //System.out.println(listSpeed.toString() + ", " + compSpeed.toString() + ", " + listSize + ", " + driver.getSorter(l).toString());
                    }
                }
            }
        }

        batchRunner.run();
        try {
            fw.close();
        }catch(IOException e){
            throw new Error("Unable to close the file.");
        }
    }

    private Delayed.Time getTime(int i){
        if(i<0||i>2){
            throw new IllegalArgumentException("Unacceptable argument");
        }
        switch(i){
            case 0:
                return Delayed.Time.Fast;
            case 1:
                return Delayed.Time.Normal;
            case 2:
                return Delayed.Time.Slow;
            default:
                return null;
        }
    }

    private Sorter getSorter(int i){
        if(i<0||i>3){
            throw new IllegalArgumentException("Unacceptable argument");
        }
        switch(i){
            case 0:
                return new SelectionSorter();
            case 1:
                return new InsertionSorter();
            case 2:
                return new MergeSorter();
            case 3:
                return new QuickSorter();
            default:
                return null;
        }
    }

    private static class RunTest implements Runnable{
        Delayed.Time listSpeed;
        Delayed.Time compSpeed;
        int numElements;
        Sorter algorithm;
        DelayedList<Integer> backingList;
        FileWriter fw;



        RunTest(Delayed.Time ListSpeed, Delayed.Time ComparatorSpeed, int ListSize, Sorter Algorithm, FileWriter fileWriter){
            this.listSpeed = ListSpeed;
            this.compSpeed = ComparatorSpeed;
            this.numElements = ListSize;
            this.algorithm = Algorithm;
            this.fw = fileWriter;
        }

        @Override
        public void run() {
            backingList = DelayedList.create(listSpeed, RandomNumbers.get(numElements));
            Comparator<Integer> c = new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer t1) {
                    return integer-t1;
                }
            };
            DelayedComparator<Integer> comparator = DelayedComparator.create(c, compSpeed);
            Long time1 = System.nanoTime();
            algorithm.sort(backingList, comparator);
            Long time2 = System.nanoTime();
            try{
                fw.write(algorithm.toString()+", ");
                fw.write(numElements+", ");
                fw.write(listSpeed.toString()+", ");
                fw.write(Long.toString(backingList.getOperationsPerformed())+", ");
                fw.write(compSpeed.toString()+", ");
                fw.write(comparator.getComparisonsPerformed()+", ");
                fw.write(Long.toString(time2-time1)+"\n");
            }catch(IOException e){
                throw new Error("Unable to write to file.");
            }
        }
    }

}
