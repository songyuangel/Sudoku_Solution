package pers.song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuSolution2 {

    /**
     * 输入格式：0代表空格，需要确定值
     *          其余数字则是出题给的数值
     */
    //记录输入的数独二维数组
    private  int[][] inputArr ;
    //记录处理后最终结果的数独二维数组
    private  int[][] resultArr = new int[9][9];
    //当前求解的数组
    private  int[][] currentArr = new int[9][9];
    //一个空格所有可能的数值
    private final int[] possibleArr = {1,2,3,4,5,6,7,8,9};
    //当前空格可能的数值
    private int[] currentPossibleArr;
    //开始求解位置的坐标
    private int startX = -1;
    private int startY = -1;
    //结束位置的坐标
    private int endX = -1;
    private int endY = -1;

    private boolean done = false ;


    public void setInputArr(int[][] inputArr) {
        this.inputArr = inputArr;
    }

    public SudokuSolution2(){

    }

    public SudokuSolution2(int[][] inputArr){
        this.inputArr = inputArr;
        //this.currentArr = inputArr;
        //记录求解开始和结束的坐标
        for (int i = 0 ;i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                if(inputArr[i][j] == 0 ){
                    if(startX == -1 && startY == -1 ){
                        startX = i;
                        startY = j;
                    }
                    endX = i;
                    endY = j;
                }
            }
        }
        System.out.println("startX=" + startX + ",startY=" + startY + ",endX=" + endX + ",endY=" + endY);
    }

    /**
     * 主要代码在这里！
     * @return
     */
    public int solute() {
        if (inputArr == null || inputArr.length < 1) {
            System.out.println("请先给定需要求解的数独数组！");
            return -1;
        }
        for (int i = 0; i < inputArr.length; i++) {
            System.arraycopy(inputArr[i], 0, resultArr[i],
                    0, inputArr[i].length);
            System.arraycopy(inputArr[i], 0, currentArr[i],
                    0, inputArr[i].length);
        }
        System.out.println("待求解的数独：");
        printSudoku(inputArr);
        go(startX,startY,0);
        if(done){
            System.out.println("成功求解：");
            printSudoku(resultArr);
        }else{
            System.out.println("未能成功求解：");
            printSudoku(currentArr);
        }

        return 0;
    }

    /**
     * 递归 传入坐标和值
     * 如果num为0 ， 则为第一次开始，取所有可能的值进行递归调用
     * 如果不为0 ，则认为当前坐标填入该值，取下一个待填的空格进行调用
     */

    private boolean go(int x , int y ,int num){

//        System.out.println("**************"+x+","+y+","+num);
//        printSudoku(currentArr);

        if(x == endX && y == endY && num != 0){
            currentArr[x][y] = num ;
            //resultArr = currentArr.clone() ;
            for (int a = 0; a < currentArr.length; a++) {
                System.arraycopy(currentArr[a], 0, resultArr[a],
                        0, currentArr[a].length);
            }
            done = true;
            return true;
        }

        if(num != 0 ){
            cleanCurrent(x,y);
            currentArr[x][y] = num ;
            int next[] = getNextSpace(x,y);
            go(next[0],next[1],0);
        }else{
            int possible[] = checkPossible(x,y);
            if(possible.length == 0) return false;
            for(int i = 0 ; i < possible.length ; i++){
                if (go(x,y,possible[i])){

                    return true;
                }
            }
        }
        return false;
    }

    private int[] getNextSpace(int x , int y){
        y =y + 1;
        for(int i = x ; i < 9 ; i ++){
            if( i > x) y = 0;
            for(int j = y ; j < 9 ; j ++){
                if(inputArr[i][j] == 0 ){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
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
                if(currentArr[ii][jj] != 0){
                    int value = currentArr[ii][jj];
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
        if(currentArr[i][j] != 0){
            //如果不为0，说明已经填入的，则返回本身
            return new int[]{resultArr[i][j]};
        }

        List<Integer> possibleList = Arrays.stream( possibleArr ).boxed().collect(Collectors.toList());

        //1.检查该行和该列
        for(int ii = 0 ; ii < 9 ; ii ++){
            if(currentArr[ii][j] == 0) continue;
            if(possibleList.contains(currentArr[ii][j])){
                int index = possibleList.indexOf(currentArr[ii][j]);
                possibleList.remove(index);
            }
        }

        for(int jj = 0 ; jj < 9 ; jj ++){
            if(currentArr[i][jj] == 0) continue;
            if(possibleList.contains(currentArr[i][jj])){
                int index = possibleList.indexOf(currentArr[i][jj]);
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

    private void cleanCurrent(int x , int y){
        int xx = x;
        int yy = y;

        for(int i = xx ; i < 9 ; i ++){
            if( i > xx) yy = 0;
            for(int j = yy ; j < 9 ; j ++){
                currentArr[i][j] = inputArr[i][j];
            }
        }
    }
}
