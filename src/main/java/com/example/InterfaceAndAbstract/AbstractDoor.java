package com.example.InterfaceAndAbstract;

/**
 * Created by hy on 2017/10/18.
 */
public abstract class AbstractDoor implements Door {

    public int i = 1;

    @Override
    public void open() {
        System.out.println(i);
    }

    @Override
    public void close() {

    }

    abstract void alarm1();
}
