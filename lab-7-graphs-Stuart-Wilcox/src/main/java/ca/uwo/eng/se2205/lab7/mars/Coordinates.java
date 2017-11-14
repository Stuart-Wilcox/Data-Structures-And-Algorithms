package ca.uwo.eng.se2205.lab7.mars;

import java.util.List;

/**
 * Created by Stu on 2017-04-09.
 */

//just a simple class to clean up mars planner a little bit
public class Coordinates {

    private int i, j;
    private Integer _i, _j;

    public Coordinates(int i, int j){
        this.i = i;
        this.j = j;
        this._i = i;
        this._j = j;
    }

    public int i(){
        return this.i;
    }

    public int j(){
        return this.j;
    }

    public int[] toArray(){
        return new int[]{i,j};
    }

    public Integer[] toIntegerArray(){
        return new Integer[]{_i,_j};
    }

    @Override
    public boolean equals(Object o) {
        return this==o || this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return 191*_i.hashCode() + 9973*_j.hashCode();
    }

    @Override
    public String toString() {
        return "{" + _i.toString() + ", " + _j.toString() + "}";
    }

    public static Coordinates toCoordinates(int[] c){
        if(c.length != 2){
            throw new IllegalArgumentException("Not valid coordinates.");
        }
        return new Coordinates(c[0], c[1]);
    }

    public static Coordinates flip(Coordinates c){
        return new Coordinates(c.j, c.i);
    }
}
