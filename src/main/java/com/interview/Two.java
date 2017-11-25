package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hy on 2017/10/11.
 */
public class Two {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int in = scanner.nextInt();
        PrintTheMachineNumber(in);
    }

    private static void PrintTheMachineNumber(int in) {
        if (in > 0) {
            List<Integer> list = new ArrayList<>();
            while (true) {
                if (in == 0) {
                    break;
                }
                if (in % 2 == 1) {
                    list.add(1);
                    in = (in - 1) / 2;
                } else if (in % 2 == 0) {
                    list.add(2);
                    in = (in - 2) / 2;
                }
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                System.out.print(list.get(i));
            }
        }
    }
}
