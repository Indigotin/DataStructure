package Project2.version1;

import Project2.version3.PointGameOf24;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluateExpression {

    public static void main(String[] args) {
        System.out.println(evaluateExpression("8/(3-(8/3))",false));
    }

    //flag判断是计算还是去重
    public static double evaluateExpression(String expression,boolean flag) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(expression, "()+-/*", true);

        try{
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken().trim();
                if (token.length() == 0)
                    continue;
                else if (token.charAt(0) == '+' || token.charAt(0) == '-') {

                    while (!operatorStack.isEmpty() &&
                          (operatorStack.peek() == '+' ||
                                  operatorStack.peek() == '-' ||
                                  operatorStack.peek() == '*' ||
                                  operatorStack.peek() == '/')) {
                        processAnOperator(operandStack, operatorStack);
                    }
                    operatorStack.push(token.charAt(0));
                }

                else if (token.charAt(0) == '*' || token.charAt(0) == '/') {

                    while (!operatorStack.isEmpty() &&
                          (operatorStack.peek() == '*' ||
                              operatorStack.peek() == '/')) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
                }

                else if (token.trim().charAt(0) == '(') {
                    operatorStack.push('('); // Push '(' to stack
                }

                else if (token.trim().charAt(0) == ')') {

                    while (operatorStack.peek() != '(') {
                          processAnOperator(operandStack, operatorStack);
                    }
                    operatorStack.pop();
                }

                //是数字
                else {
                    if(flag){
                        operandStack.push((Double) PointGameOf24.map.get(Integer.parseInt(token)));
                    }else{
                        operandStack.push(Double.parseDouble(token));
                    }
                }
            }

            while (!operatorStack.isEmpty()) {
                processAnOperator(operandStack, operatorStack);
            }

        }catch (ArithmeticException ex){
            return -2862.5;
        }
        return operandStack.pop();
    }

    public static void processAnOperator(Stack<Double> operandStack,Stack<Character> operatorStack){
        char op = operatorStack.pop();
        Double op1 = operandStack.pop();
        Double op2 = operandStack.pop();
        if (op == '+')
            operandStack.push(op2 + op1);
        else if (op == '-')
            operandStack.push(op2 - op1);
        else if (op == '*')
            operandStack.push(op2 * op1);
        else if (op == '/')
            operandStack.push(op2 / op1);
    }

    /**
     * 检查表达式是否合法，合法返回true
     * @param expression
     * @return
     */
    public static boolean checkExpression(String expression) {
        //去除空格
        expression = expression.replaceAll(" ","");

        Set<Character> operate_set = new HashSet<>();
        operate_set.add('-');
        operate_set.add('+');
        operate_set.add('*');
        operate_set.add('/');

        //拆分字符串
        char[] arr = expression.toCharArray();
        int len = arr.length;

        //前后括号计数，用来判断括号是否合法
        int checkNum=0;

        //数字集合
        List<Character> digit_list = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        //循环判断
        for (int i = 0; i < len; i++) {
            if(Character.isDigit(arr[i])|| arr[i] == '.'){ //数字和小数点判断
                //把数字和小数点加入到集合中，为了下一步判断数字是否合法
                digit_list.add(arr[i]);
            }else { //非数字和小数点
                //如果集合中有值，取出来判断这个数字整体是否合法
                if(digit_list.size()>0) {
                    //判断字符串是否合法
                    boolean result = getNumberStringFromList(digit_list);
                    if(result){
                        //如果合法，清空集合，为了判断下一个
                        digit_list.clear();
                    }else{
                        //不合法，返回false
                        return false;
                    }
                }

                if (arr[i] == '+' || arr[i] == '*' || arr[i] == '/') {
                    //判断规则(1.不能位于首位 2.不能位于末尾 3.后面不能有其他运算符 4.后面不能有后括号)
                    if (i == 0 || i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
                        System.out.println("error type : '+' or '*' or '/' ->"+ arr[i]);
                        return false;
                    }
                } else if (arr[i] == '-') {
                    //减号判断规则(1.不能位于末尾 2.后面不能有其他运算符 3.后面不能有后括号)
                    if (i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
                        System.out.println("error type : '-' ->"+ arr[i]);
                        return false;
                    }

                } else if (arr[i] == '(') {
                    checkNum++;
                    //判断规则(1.不能位于末尾 2.后面不能有+，*，/运算符和后括号 3.前面不能为数字)
                    if (i == (len - 1) || arr[i + 1] == '+' || arr[i + 1] == '*' || arr[i + 1] == '/' || arr[i + 1] == ')'||(i != 0 && Character.isDigit(arr[i-1]))) {
                        System.out.println("error type : '(' ->"+ arr[i]);
                        return false;
                    }
                } else if (arr[i] == ')') {
                    checkNum--;
                    //判定规则(1.不能位于首位 2.后面不能是前括号 3.括号计数不能小于0，小于0说明前面少了前括号)
                    if (i == 0 || (i < (len - 1) && arr[i + 1] == '(') || checkNum < 0) {
                        System.out.println("error type : ')' ->"+ arr[i]);
                        return false;
                    }
                }else{
                    //非数字和运算符
                    System.out.println("error type : not number and operator ->"+ arr[i]);
                    return false;
                }
            }
        }

        //如果集合中有值，取出来判断这个数字整体是否合法
        if(digit_list.size()>0) {
            //判断字符串是否合法
            boolean result = getNumberStringFromList(digit_list);
            if(result){
                //如果合法，清空集合，为了判断下一个
                digit_list.clear();
            }else{
                //不合法，返回false
                return false;
            }
        }

        //不为0,说明括号不对等，可能多前括号
        if(checkNum!=0){
            return false;
        }
        return true;
    }

    /**
     * 把集合中的字符，拼接成字符串，并校验字符串是否是数值
     * @param list
     * @return
     */
    private static boolean getNumberStringFromList(List<Character> list){
        //如果集合中有值，取出来判断这个数字整体是否合法
        if(list != null){
            StringBuffer stringBuffer = new StringBuffer();
            for(Character character : list){
                stringBuffer.append(character);
            }
            //正则判断数字是否合法
            boolean result = isNumber(stringBuffer.toString());

            if(!result){
                System.out.println("error type: digit ->"+stringBuffer.toString());
            }
            return result;
        }
        return false;
    }

    static boolean  isNumber(String str){ Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d+|0\\.\\d*[1-9]\\d*|[1-9]\\d*|0)$");
        Matcher isNum = pattern.matcher(str); if( !isNum.matches() ){ return false; } return true;}

}
