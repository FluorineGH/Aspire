/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.util.Comparator;

public class AScore implements Comparator<AScore>, Comparable<AScore>{
    String Name;
    int Score;
    
    public AScore(String s, int i){
        Name = s;
        Score = i;
    }
  
    public String getName(){
      return Name;
   }

   public int getScore(){
      return Score;
   }

   // Overriding the compareTo method
   public int compareTo(AScore as){
      return (this.Name).compareTo(as.Name);
   }

   // Overriding the compare method to sort the age 
   public int compare(AScore as, AScore as1){
      return as.Score - as1.Score;
   }
    
}
