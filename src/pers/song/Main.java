package pers.song;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] input = new int[9][9];
//        int[][] input = {{2,3,9,0,1,8,4,0,0},
//                         {5,1,6,3,4,7,9,0,8},
//                         {7,8,0,6,2,9,3,5,1},
//                         {0,0,7,1,0,4,5,6,3},
//                         {6,0,3,7,5,2,1,8,0},
//                         {8,5,0,9,6,3,7,0,2},
//                         {1,7,0,2,3,0,8,9,4},
//                         {3,6,8,4,9,0,2,0,7},
//                         {4,0,2,8,0,1,0,3,5}
//                        };
        Scanner in = new Scanner(System.in);

        for (int i = 0 ; i < 9 ; i ++){
            System.out.println("请录入第" + (i+1) + "行数据:");
            String s =in.nextLine();
            if(s.length() != 9){
                System.out.println("录入数据有误！");
                return ;
            }
            for(int j = 0 ; j < 9 ; j++){
                input[i][j] = Integer.parseInt( s.charAt(j)+"" );
            }

        }

        SudokuSolution sudokuSolution = new SudokuSolution(input);
        sudokuSolution.solute();
    }
}
