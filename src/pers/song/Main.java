package pers.song;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] input = new int[9][9];
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
