package Test;

import java.util.Arrays;
import java.util.HashMap;

//TODO: Return me the number of combination whose sum is equal to the greatest number
public class Test {
    public static void main(String[] args) {
      int[] num  = {1,2,3,4,5,1,2};
      int output=5;
      int left = 0;
      int right = num.length-1;
      for(int i=0;i<num.length;i++){
          while(left<right){
              if(num[left]+num[right]==output){
                  System.out.println("combinaition---"+num[left]+","+num[right]);
                  right--;
              }else {
                  right--;
              }
          }
          left++;
          right = num.length-1;
      }

    }
}
