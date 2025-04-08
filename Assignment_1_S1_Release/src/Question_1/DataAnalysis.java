/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

import java.util.Arrays;

/**
 *
 * @author xhu
 */
public class DataAnalysis <E extends Comparable>{
    
    private E[] data;
    
    public DataAnalysis(E[] data)
    {
        this.data = data;
    }
    
    public boolean bracketEvaluator()
    {
        Stack<Character> stack = new Stack<>();
        
        String string = Arrays.toString(data);
        string = string.toLowerCase().replaceAll(" ", "");
        
        for(int i = 0; i < string.length(); i++){
            char openBracket = string.charAt(i);
            char closeBracket = string.charAt(i);
            
            if (openBracket == '(' || openBracket == '{' || openBracket == '[' || openBracket == '<'){ //pushing open brackets found in data/string
                stack.push(openBracket);
            }
            if (closeBracket == ')' || closeBracket == '}' || closeBracket == ']' || closeBracket == '>'){ //pushing close brackets found in data/string
                if(stack.getSize() == 0){ //avoid pushing after closing bracket is found
                      return false;
                  }
                closeBracket = stack.pop();
            }
            if(openBracket == '(' && closeBracket == ')' ||
                   openBracket == '{' && closeBracket == '}' ||         //checking if both open and close bracket match
                   openBracket == '[' && closeBracket == ']' || 
                   openBracket == '<' && closeBracket == '>'){
                    return true;
                }
        }
        return stack.getSize() == 0;
    }
        
}
