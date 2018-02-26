package com.example.think.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Stack;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_0 ;
    Button btn_1;
    Button btn_2;
    Button btn_3 ;
    Button btn_4 ;
    Button btn_5 ;
    Button btn_6 ;  //数字按钮
    Button btn_7 ;
    Button btn_8 ;
    Button btn_9 ;
    Button btn_point ;  //小数点按钮
    Button btn_clear ;
    Button btn_del ;
    Button btn_plus ;
    Button btn_minus ;
    Button btn_multiply ;
    Button btn_divide ;
    Button btn_equal ;
    EditText et_input ;
    boolean clear_flag ;//清空标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_0 = (Button) findViewById(R.id.btn_00) ;
        btn_1 = (Button) findViewById(R.id.btn_11) ;
        btn_2 = (Button) findViewById(R.id.btn_22) ;
        btn_3 = (Button) findViewById(R.id.btn_33) ;
        btn_4 = (Button) findViewById(R.id.btn_44) ;
        btn_5 = (Button) findViewById(R.id.btn_55) ;
        btn_6 = (Button) findViewById(R.id.btn_66) ;
        btn_7 = (Button) findViewById(R.id.btn_77) ;
        btn_8 = (Button) findViewById(R.id.btn_88) ;
        btn_9 = (Button) findViewById(R.id.btn_99) ;
        btn_point = (Button) findViewById(R.id.btn_pointt) ;
        btn_clear = (Button) findViewById(R.id.btn_clearr) ;
        btn_del = (Button) findViewById(R.id.btn_dell) ;
        btn_plus = (Button) findViewById(R.id.btn_pluss) ;
        btn_minus = (Button) findViewById(R.id.btn_minuss) ;
        btn_multiply = (Button) findViewById(R.id.btn_multiplyy) ;
        btn_divide = (Button) findViewById(R.id.btn_dividee) ;
        btn_equal = (Button) findViewById(R.id.btn_equall) ;
//以上实例化按钮
        et_input = (EditText) findViewById(R.id.et_input);  //实例化之后的显示屏

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        //设置以上按钮的点击事件


    }
    @Override
    public void onClick(View v) {
        String str = et_input.getText().toString();
        switch (v.getId()) {
            case R.id.btn_00:
            case R.id.btn_11:
            case R.id.btn_22:
            case R.id.btn_33:
            case R.id.btn_44:
            case R.id.btn_55:
            case R.id.btn_66:
            case R.id.btn_77:
            case R.id.btn_88:
            case R.id.btn_99:
            case R.id.btn_pointt:
                if (clear_flag) {
                    clear_flag =false ;
                    str ="" ;
                    et_input.setText("");
                }
                et_input.setText(str + ((Button)v).getText());
                break ;
            case R.id.btn_pluss:
            case R.id.btn_minuss:
            case R.id.btn_multiplyy:
            case R.id.btn_dividee:
                if (clear_flag) {
                    clear_flag =false ;
                    str ="" ;
                    et_input.setText("");
                }
                et_input.setText(str+ " " + ((Button)v).getText()+" ");
                break;
            case R.id.btn_dell:
                if (clear_flag) {
                    clear_flag =false ;
                    str ="" ;
                    et_input.setText("");
                }else if (str!=null&&!str.equals("")){
                    et_input.setText(str.substring(0,str.length()-1));
                }
                break;
            case R.id.btn_clearr:
                clear_flag =false ;
                str ="" ;
                et_input.setText("");
            case R.id.btn_equall:
                getResult();
                break ;

        }
    }

    // 将中缀表达式字符串计算得到结果
    public static double stringToArithmetic(String string) {
        return suffixToArithmetic(infixToSuffix(string));
    }

    // 将中缀表达式转换为后缀表达式
    public static String infixToSuffix(String exp) {
        // 创建操作符堆栈
        Stack<Character> s = new Stack<Character>();
        // 要输出的后缀表达式字符串
        String suffix = "";
        int length = exp.length(); // 输入的中缀表达式的长度
        for (int i = 0; i < length; i++) {
            char temp;// 临时字符变量
            // 获取该中缀表达式的每一个字符并进行判断
            char ch = exp.charAt(i);
            switch (ch) {
                // 忽略空格
                case ' ':
                    break;
                // 如果是左括号直接压入堆栈
                case '(':
                    s.push(ch);
                    break;

                // 碰到'+' '-'，将栈中的所有运算符全部弹出去，直至碰到左括号为止，输出到队列中去
                case '+':
                case '-':
                    while (s.size() != 0) {
                        temp = s.pop();
                        if (temp == '(') {
                            // 重新将左括号放回堆栈，终止循环
                            s.push('(');
                            break;
                        }
                        suffix += temp;
                    }
                    // 没有进入循环说明是当前为第一次进入或者其他前面运算都有括号等情况导致栈已经为空,此时需要将符号进栈
                    s.push(ch);
                    break;

                // 如果是乘号或者除号，则弹出所有序列，直到碰到加好、减号、左括号为止，最后将该操作符压入堆栈
                case '*':
                case '/':
                    while (s.size() != 0) {
                        temp = s.pop();
                        // 只有比当前优先级高的或者相等的才会弹出到输出队列，遇到加减左括号，直接停止当前循环
                        if (temp == '+' || temp == '-' || temp == '(') {
                            s.push(temp);
                            break;
                        } else {
                            suffix += temp;
                        }
                    }
                    // 没有进入循环说明是当前为第一次进入或者其他前面运算都有括号等情况导致栈已经为空,此时需要将符号进栈
                    s.push(ch);
                    break;

                // 如果碰到的是右括号，则距离栈顶的第一个左括号上面的所有运算符弹出栈并抛弃左括号
                case ')':
                    // 这里假设一定会遇到左括号了，此为自己改进版，已经验证可以过
                    // while ((temp = s.pop()) != '(') {
                    // suffix += temp;
                    // }
                    while (!s.isEmpty()) {
                        temp = s.pop();
                        if (temp == '(') {
                            break;
                        } else {
                            suffix += temp;
                        }
                    }
                    break;
                // 默认情况，如果读取到的是数字，则直接送至输出序列
                default:
                    suffix += ch;
                    break;
            }

        }
        // 如果堆栈不为空，则把剩余运算符一次弹出，送至输出序列
        while (s.size() != 0) {
            suffix += s.pop();
        }
        //
        return suffix;
    }

    // 将后缀表达式的进行计算得到运算结果 eg:325-6*3/+
    public static double suffixToArithmetic(String exp) {
        // 使用正则表达式匹配数字
        Pattern pattern = Pattern.compile("\\d+||(\\d+\\.\\d+)");
        // 将后缀表达式分割成字符串数组,此处直接使用空白也可以对字符串进行分割！！
        String[] strings = exp.split("");
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < strings.length; i++) {
            // 这里最好是进行判断彻底消除空格，在该数组的第一位为一个隐形的空格，这里一定要注意在使用exp.split("")剔除空白""
            // 由于使用的是""截取导致在数组第一位上面的值为空白
            if (strings[i].equals("")) {
                continue;
            }
            // 如果遇到了数字则直接进栈
            if (pattern.matcher(strings[i]).matches()) {
                stack.push(Double.parseDouble(strings[i]));
            }
            // 如果是运算符，则弹出栈顶的两个数进行计算
            else {
                // ！！！这里需要注意，先弹出的那个数其实是第二个计算数值，这里记作y！
                // 自己书写的时候出错
                double y = stack.pop();
                double x = stack.pop();
                // 将运算结果重新压栈
                stack.push(calculate(x, y, strings[i]));
            }
        }
        // 弹出栈顶元素就是最终结果
        return stack.pop();
    }

    private static Double calculate(double x, double y, String string) {
        // TODO Auto-generated method stub
        // 其实使用case逻辑也可以
        if (string.trim().equals("+")) {
            return x + y;
        }
        if (string.trim().equals("-")) {
            return x - y;
        }
        if (string.trim().equals("*")) {
            return x * y;
        }
        if (string.trim().equals("/")) {
            return x / y;
        }
        return (double) 0;
    }

    /* 单独的调用运算结果
    *
    *
    * */
    private void getResult(){
        String exp = et_input.getText().toString();
        if (exp == null||exp.equals("")){
            return;
        }
        if(!exp.contains(" ")) {
            return;
        }
        if (clear_flag){
            clear_flag = false ;
            return;

        }
        clear_flag = true ;
        double result = 0 ;
        result=stringToArithmetic(exp);
        et_input.setText(result+"");
        }
    }
