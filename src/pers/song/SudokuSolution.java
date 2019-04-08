package pers.song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        printSudoku(inputArr);
        boolean flag = false ;
        boolean done = false ;
        do {
            flag = false;
            for(int i = 0 ; i < 9 ; i++){
                for (int j = 0 ; j < 9 ; j++){
                    if(resultArr[i][j] != 0) continue;
                    int[] possibleArr = checkPossible(i,j);
                    //只有一个元素的即可确定填入
                    if(possibleArr.length == 1){
                        int value = possibleArr[0];
                        resultArr[i][j] = value ;
                        flag = true;
                    }

                }
            }
            done = checkDone();

        }while (flag && !done);

        done = checkDone();
        if(done){
            System.out.println("成功求解：");
            printSudoku(resultArr);
        }else{
            System.out.println("未能成功求解：");
            printSudoku(resultArr);
        }

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

    /**
     * 获取一个空格所在的宫中已填入的数据
     * @return
     */
    private int[] getSubGrids(int i ,int j){
        int istart = (i / 3) * 3 ;
        int jstart = (j / 3) * 3 ;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int ii = istart ; ii < istart + 3 ; ii ++){
            for(int jj = jstart ; jj < jstart + 3 ; jj++){
                if(resultArr[ii][jj] != 0){
                    int value = resultArr[ii][jj];
                    result.add(value);
                }
            }
        }

        //List转Int[]的方法 jdk8
        return result.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 获取一个空格所有可能填入的值
     * @return
     */
    private int[] checkPossible(int i , int j){
        if(resultArr[i][j] != 0){
            //如果不为0，说明已经填入的，则返回本身
            return new int[]{resultArr[i][j]};
        }

        List<Integer> possibleList = Arrays.stream( possibleArr ).boxed().collect(Collectors.toList());

        //1.检查该行和该列
        for(int ii = 0 ; ii < 9 ; ii ++){
            if(resultArr[ii][j] == 0) continue;
            if(possibleList.contains(resultArr[ii][j])){
                int index = possibleList.indexOf(resultArr[ii][j]);
                possibleList.remove(index);
            }
        }

        for(int jj = 0 ; jj < 9 ; jj ++){
            if(resultArr[i][jj] == 0) continue;
            if(possibleList.contains(resultArr[i][jj])){
                int index = possibleList.indexOf(resultArr[i][jj]);
                possibleList.remove(index);
            }
        }

        //2.检查宫内的数据
        int[] subgrids = getSubGrids(i,j);
        for(int k = 0 ; k < subgrids.length ; k ++){
            if(subgrids[k] == 0 ) continue;
            if(possibleList.contains(subgrids[k])){
                int index = possibleList.indexOf(subgrids[k]);
                possibleList.remove(index);
            }

        }

        return possibleList.stream().mapToInt(Integer::valueOf).toArray();

    }

    private boolean checkDone(){
        boolean flag = true;
        for(int i = 0 ; i < 9 ; i++) {
            for (int j = 0; j < 9; j++) {
                if (resultArr[i][j] == 0) {
                    flag = false ;
                    break;
                }
            }
        }

        return flag;
    }

}
