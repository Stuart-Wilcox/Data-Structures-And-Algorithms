package ca.uwo.eng.se2205.lab6;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

/**
 * Created by Stu on 2017-03-21.
 */
public class Driver2 {
    public static void main(String[] args){
        BatchRunner batchRunner = new BatchRunner();
        Driver2 driver = new Driver2();
        FileWriter fw;
        try {
            fw = new FileWriter("./results_time_n.csv", true);
            fw.write("\n\n\n***STARTING NEW TEST***\n\n\n");
            fw.write("Algorithm, n, List Ops/Sort, Comparison Ops/Sort, Time(ns)\n");
        }catch(IOException e){
            throw new Error("Could not find file");
        }


        for(int i = 0 ; i < 5 ; i++){
            int size = driver.getSize(i);
            RunTest runTest;
            for(int j = 0;j<4;j++){
                runTest = new Driver2.RunTest(size, driver.getSorter(j), fw);
                batchRunner.enqueue(runTest);
                //System.out.println(driver.getSorter(j).toString()+", "+size);
            }
        }

        batchRunner.run();
        try {
            fw.close();
        }catch(IOException e){
            throw new Error("Unable to close the file.");
        }
    }

    private int getSize(int i){
        switch(i){
            case 0:
                return 5;
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 40;
            case 4:
                return 100;
            default:
                return 0;
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



        RunTest(int ListSize, Sorter Algorithm, FileWriter fileWriter){
            this.listSpeed = Delayed.Time.Fast;
            this.compSpeed = Delayed.Time.Fast;
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
                fw.write(Long.toString(backingList.getOperationsPerformed())+", ");
                fw.write(comparator.getComparisonsPerformed()+", ");
                fw.write(Long.toString(time2-time1)+"\n");
            }catch(IOException e){
                throw new Error("Unable to write to file.");
            }
        }
    }
}
