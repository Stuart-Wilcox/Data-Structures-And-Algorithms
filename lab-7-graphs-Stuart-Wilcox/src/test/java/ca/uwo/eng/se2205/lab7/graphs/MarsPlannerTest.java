package ca.uwo.eng.se2205.lab7.graphs;

import ca.uwo.eng.se2205.lab7.mars.MarsPlanner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Created by Stu on 2017-04-09.
 */
//public class MarsPlannerTest {
//    @Test
//    public void test(){
//        int[][] topology = new int[][]{ { 6, 4, 3},
//                                        { 4,12, 2},
//                                        { 8,10, 3} };
//
//        MarsPlanner mp = new MarsPlanner(topology, Arrays.asList(new int[]{ 1, 0 }, new int[]{ 2, 2 }));
//
//        //fuelAvail = 8 should return {0,1}
//        //fuelAvail = 10 should return {0,0}
//
//        int[] coords = mp.bestLandingSpot(4);
//        assertArrayEquals(new int[]{2,0}, coords);
////        coords = mp.bestLandingSpot(10);
////        assertArrayEquals(new int[]{0,0}, coords);
//
//    }
//
//    @Test
//    public void buildModelTest(){
//        List<int[]> coordList = new ArrayList<>();
//
//        coordList.add(new int[]{2,0});
//        coordList.add(new int[]{6,0});
//        coordList.add(new int[]{0,7});
//        coordList.add(new int[]{9,5});
//
//        MarsPlanner mp1 = MarsPlanner.buildModel(coordList);
//        int fuel1 = 26;
//        int[] coords1 = new int[]{6,0};
//        assertArrayEquals(coords1, mp1.bestLandingSpot(fuel1));
//
//        coordList.clear();
//        coordList.add(new int[]{4,4});
//        coordList.add(new int[]{2,8});
//        coordList.add(new int[]{1,7});
//        coordList.add(new int[]{8,6});
//        coordList.add(new int[]{1,3});
//
//        MarsPlanner mp2 = MarsPlanner.buildModel(coordList);
//        int fuel2 = 38;
//        int[] coords2 = new int[]{0,5};
//        assertArrayEquals(coords2, mp2.bestLandingSpot(fuel2));
//
//        coordList.clear();
//        coordList.add(new int[]{5,4});
//        coordList.add(new int[]{8,9});
//        coordList.add(new int[]{3,1});
//        coordList.add(new int[]{8,1});
//        coordList.add(new int[]{0,0});
//
//        MarsPlanner mp3 = MarsPlanner.buildModel(coordList);
//        int fuel3 = 50;
//        int[] coords3 = new int[]{7,0};
//        assertArrayEquals(coords3, mp3.bestLandingSpot(50));
//
//        coordList.clear();
//        coordList.add(new int[]{3,4});
//        coordList.add(new int[]{4,2});
//
//        MarsPlanner mp4 = MarsPlanner.buildModel(coordList);
//        int fuel4 = 10;
//        int[] coords4 = new int[]{4,3};
//        assertArrayEquals(coords4, mp4.bestLandingSpot(fuel4));
//    }
//}


import ca.uwo.eng.se2205.lab7.mars.MarsPlanner;

        import org.junit.jupiter.api.BeforeEach;

        import org.junit.jupiter.api.Nested;

        import org.junit.jupiter.api.Test;



        import java.util.Arrays;

        import java.util.Set;

        import java.util.TreeSet;



        import static org.junit.jupiter.api.Assertions.*;



/**

 * Tests for {@link MarsPlanner}

 */

class MarsPlannerTest {





    static void checkCoords(Set<int[]> correctCoords, int[] coords) {

        assertNotNull(coords, "coords == null");



        Set<int[]> inv = invertedCoords(correctCoords);



        if (!correctCoords.contains(coords)) {

            // Could not find the coords

            assertTrue(inv.contains(coords), "Incorrect result found.");

            fail(() -> "Found coords in inverted form " + Arrays.toString(coords));

        }

    }



    @Nested

    class Small {

        int[][] topology = new int[][] {{6, 4, 3},

                {4, 12, 2},

                {8, 10, 3}};



        MarsPlanner underTest;



        Set<int[]> correctAnswers;



        @BeforeEach

        public void init() {

            underTest = new MarsPlanner(topology,

                    Arrays.asList(new int[]{ 1, 0 }, new int[]{ 2, 2 }));



            correctAnswers = new TreeSet<>(MarsPlannerTest::lexCompareArrs);

            correctAnswers.add(new int[]{ 2, 0 });

            correctAnswers.add(new int[]{ 2, 1 });

        }



        @Test

        void check() {

            int[] coords = underTest.bestLandingSpot(4);

            checkCoords(correctAnswers, coords);

        }

    }



    @Nested

    class Large {

        final int[][] topology =

                new int[][] {{8,10,16,14,10,6,3,0,1,1},

                        {12,14,20,16,12,8,4,0,2,1},

                        {14,18,24,18,16,10,0,1,3,1},

                        {14,26,26,23,20,8,0,3,2,0},

                        {18,25,28,25,20,8,0,2,0,3},

                        {13,24,26,26,14,0,0,0,2,6},

                        {8,16,24,13,3,1,1,2,6,8},

                        {4,10,3,3,0,0,5,6,8,13},

                        {4,4,2,0,0,4,9,15,16,20},

                        {2,2,2,1,0,10,14,18,23,24}};



        MarsPlanner underTest;



        Set<int[]> correctAnswers;



        @BeforeEach

        public void init() {

            underTest = new MarsPlanner(topology,

                    Arrays.asList(new int[]{ 2, 0 }, new int[]{ 8, 1 },

                            new int[]{ 7, 9 }));



            correctAnswers = new TreeSet<>(MarsPlannerTest::lexCompareArrs);

            correctAnswers.add(new int[]{ 7, 0 });

            correctAnswers.add(new int[]{ 7, 1 });

        }



        @Test

        void check() {

            int[] coords = underTest.bestLandingSpot(40);

            checkCoords(correctAnswers, coords);

        }



        @Test

        void noPath() {

            int[] coords = underTest.bestLandingSpot(3);

            assertNull(coords, () -> "Should have not found valid result, found: " + Arrays.toString(coords));

        }

    }





    private static Set<int[]> invertedCoords(Set<int[]> toInvert) {

        Set<int[]> reversed = new TreeSet<>(MarsPlannerTest::lexCompareArrs);

        for (int[] coords: toInvert) {

            reversed.add(new int[] { coords[1], coords[0] });

        }



        return reversed;

    }



    private static int lexCompareArrs(int[] lhs, int[] rhs) {



        for (int i = 0; i < lhs.length; ++i) {

            int out = Integer.compare(lhs[i], rhs[i]);

            if (out != 0) {

                return out;

            }

        }



        return 0;

    }

}