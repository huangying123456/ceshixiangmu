package com.interview;

/**
 * Created by hy on 2017/10/12.
 */
public class Three {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public static void main(String[] args) {
        Three three = new Three();
        three.print(three);
        System.out.println(three.getName());
    }

    public void print(Three three) {
        three.setName("xxxx");

        three = null;
        System.out.println("lambda");
    }
}
