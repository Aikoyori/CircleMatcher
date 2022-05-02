package xyz.aikoyori.shapeswap.game.utils;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class BagRandomizer {
    Random randomPicker;
    ArrayList<?> things;
    ArrayList<?> thingsAtTheMoment;
    boolean renewable;
    long bagSeed;
    int timeShuffled = 1;
    int bagSalt = 2;
    Cloner cloner=new Cloner();
    public BagRandomizer(ArrayList<?> arr,long seed)
    {
        renewable = true;
        randomPicker = new Random(bagSeed);
        randomPicker.setSeed(seed);
        bagSeed=seed;
        this.things = (ArrayList<?>)arr.clone();
        thingsAtTheMoment = (ArrayList<?>)arr.clone();
        shuffle();
    }

    public BagRandomizer(ArrayList<?> arr)
    {

        renewable = true;
        randomPicker = new Random();
        this.things = (ArrayList<?>)arr.clone();
        thingsAtTheMoment = (ArrayList<?>)arr.clone();
    }
    public <T> T pickThingOut(){
        T lol;

            if(thingsAtTheMoment.size()<1)
            {
                thingsAtTheMoment=(ArrayList<?>)things.clone();
                shuffle();
            }
        lol = (T)thingsAtTheMoment.get(0);
        //System.out.println(getNext());
        thingsAtTheMoment.remove(0);
        return cloner.deepClone(lol);

    }
    @SuppressWarnings("unchecked")
    public <T> T getNext(){
        T lol;

        if(thingsAtTheMoment.size()<1)
        {
            thingsAtTheMoment=(ArrayList<?>)things.clone();
            shuffle();
        }
        lol = (T)thingsAtTheMoment.get(0);
        //System.out.println(getNext());
        return cloner.deepClone(lol);
    }
    public void shuffle()
    {
        Collections.shuffle(thingsAtTheMoment,new Random(bagSeed*timeShuffled+bagSalt));
        timeShuffled++;
    }

    public void setBagSalt(int bagSalt) {
        this.bagSalt = bagSalt;
    }
}
