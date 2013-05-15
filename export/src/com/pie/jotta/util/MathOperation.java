package com.pie.jotta.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  This file is part of Jotta.
 *
 *  Jotta is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Jotta is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jotta.  If not, see <http://www.gnu.org/licenses/>.
 */

public class MathOperation {

   /*
    * @Author Abraham2119
    */
   public static Float solve(String s) {
      s = parenthesis(s.replaceAll("\\s", ""));
      s = oper(new char[]{'^'}, s);
      s = oper(new char[]{'*','/'}, s);
      s = oper(new char[]{'+', '-'}, s);
      return (Float)Float.parseFloat(s);
   }
   
   private static String parenthesis(String s) {
      while(s.indexOf("(") > -1) {
         Matcher m = Pattern.compile("\\(([\\d\\+-/\\*\\^]+)\\)").matcher(s);
         while(m.find()) {
            String g = m.group(1);
            s = s.replaceFirst("\\("+toRegex(g)+"\\)", ""+solveOperation(g));
         }
      }      
      return s;
   }
   
   private static String oper(char[] ops, String s) {
      String op = "(";
      for (int i = 0; i < ops.length; i++) {
         op += (i == 0) ? "("+toRegex(""+ops[i])+")" : "|("+toRegex(""+ops[i])+")";
      }
      op += ")";

      while (hasChar(s, ops)) {
         Matcher m = Pattern.compile("(\\d{1,}"+op+"\\d{1,})").matcher(s);
         while(m.find()) {
            String g = m.group(1);
            s = s.replaceFirst(toRegex(g), ""+solveOperation(g));
         }
      }
      return s;
   }
   
   private static boolean hasChar(String s, char[] c) {
      boolean has = false;
      for (int i = 0; i < c.length; i++) {
         has = (has) ? true : ((s.indexOf(c[i]) > -1) ? true : false);
      }
      return has;
   }
   
   private static float solveOperation(String oper) {
      /*Should only be one operation, as in: 2 + 2, and not: 2 + 2 + 4 * 7 / 8 */
      oper = oper.replaceAll("\\s", "");
      char[] operators = {'+', '-', '*', '/', '^'};
      float[] num = new float[2];      
      char operator = (char) 0;
      
      for (int i = 0; i < operators.length; i++) {         
         int in = oper.indexOf(operators[i]);
         if (in > -1) {
            operator = operators[i];
            num[0] = Integer.parseInt(oper.substring(0, in));
            num[1] = Integer.parseInt(oper.substring(in + 1, oper.length()));
            break;
         }
      }
      
      switch(operator)  {
         case '+':
            return num[0] + num[1];
         case '-':
            return num[0] - num[1];
         case '*':
            return num[0] * num[1];
         case '/':
            return num[0] / num[1];
         case '^':
            return (float) Math.pow(num[0], num[1]);
      }
      
      return Float.parseFloat(oper);
   }
   
   private static String toRegex(String s) {
      Matcher m = Pattern.compile("([\\+\\-\\*\\^\\(\\)\\[\\]\\\\])").matcher(s);
      
      while(m.find()) {
         s = s.replaceAll("\\"+m.group(1), "\\\\"+m.group(1));
      }
      
      return s;
   }
}
