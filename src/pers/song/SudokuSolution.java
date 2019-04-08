package pers.song;

public class SudokuSolution {
    /**
     * 输入格式：0代表空格，需要确定值
     *          其余数字则是出题给的数值
     */
    //记录输入的数独二维数组
    private  int[][] inputArr ;
    //记录处理后最终结果的数独二维数组
    private  int[][] resultArr = new int[9][9];
    //一个空格所有可能的数值
    private final int[] possibleArr = {1,2,3,4,5,6,7,8,9};
    //当前空格可能的数值
    private int[] currentPossibleArr;


    public void setInputArr(int[][] inputArr) {
        this.inputArr = inputArr;
    }

    public SudokuSolution(){

    }

    public SudokuSolution(int[][] inputArr){
        this.inputArr = inputArr;
    }

    /**
     * 主要代码在这里！
     * @return
     */
    public int solute(){
        if(inputArr == null || inputArr.length < 1){
            System.out.println("请先给定需要求解的数独数组！");
            return -1;
        }
        for(int i = 0;i < inputArr.length;i++) {
            System.arraycopy(inputArr[i], 0, resultArr[i],
                    0, inputArr[i].length);
        }
        System.out.println("待求解的数独：");
        printSudoku(resultArr);

        return 0;
    }

    /**
     * 打印数独数组
     * @param inputArr
     */
    public void printSudoku(int[][] inputArr){
        System.out.println("- - - - - - - - - - - - -");
        for(int i = 0; i < 9 ; i++){
            if(i%3 == 0 && i > 0)
                System.out.println("- - - - - - - - - - - - -");

            System.out.print("| ");
            for(int j = 0 ; j < 9 ; j++){
                if(j%3 == 0 && j > 0)
                    System.out.print("| ");

                System.out.print(inputArr[i][j]);
                System.out.print(" ");
            }
            System.out.print("| \r\n");
        }
        System.out.println("- - - - - - - - - - - - -");
    }

}
