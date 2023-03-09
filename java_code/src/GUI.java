import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.*;
import javax.swing.JOptionPane;

public class GUI extends methods {
    private JButton button_confrim, button_close, button_output,
                    button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15,button16,button17;
    private JPanel panel__confirm_close, panel__button, panel__textarea;
    public JFrame frame;
    public JFrame text_input;
    public static JFrame sign_in;
    public JTextArea textArea;
    public TextField textField;
    public JScrollPane scrollbar;
    public static String user_name;
    public static int out_counter = 0;

    //open and close database
    private static Connection con = null;
    private static boolean openDB(String password_input) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        Properties prop = new Properties();  //Properties prop = new Properties(default_prop);
        prop.put("user", user_name);
        prop.put("password", password_input); // postgres  11271049as
        String host = "localhost";
        String database = "postgres";
        String url = "jdbc:postgresql://" + host + "/" + database;

        try {
            con = DriverManager.getConnection(url, prop);
            con.setAutoCommit(false);
            System.out.printf("Connect to database as %s.\n\n", user_name);
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            return false;
        }
    }
    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception ignored) {}
        }
    }

    public static void main(String[] args) {


        //登录窗口框架
        sign_in = new JFrame();
        sign_in.setTitle("用户登录");
        sign_in.setBounds(600, 400, 300, 300);
        sign_in.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel input = new JPanel();
        GridLayout gl = new GridLayout(2,1);
        input.setLayout(gl);
        JTextField input_name = new JTextField("用户名：");
        JTextField input_password = new JTextField("密码：");
        input.add(input_name);
        input.add(input_password);

        JPanel confirm_close = new JPanel();
        JButton confirm = new JButton("确认");
        JButton close = new JButton("关闭");
        confirm_close.add(confirm);
        confirm_close.add(close);
        close.addActionListener(e -> {
            sign_in.dispose();
        });
        confirm.addActionListener(e -> {
            user_name = input_name.getText().substring(4);
            String password = input_password.getText().substring(3);
            if(openDB(password)){
                new GUI().initView();
                sign_in.dispose();
            } else {
                JOptionPane.showMessageDialog(input,"用户名或密码错误");
            }

        });

        sign_in.add(input);
        sign_in.add(confirm_close, BorderLayout.SOUTH);
        sign_in.setVisible(true);


    }

    private void initView() {
        // 窗口框架
        frame = new JFrame();
        frame.setTitle("数据库操作界面");
        frame.setBounds(275, 300, 950, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            panel__button = new JPanel();
        frame.getContentPane().add(panel__button, BorderLayout.NORTH);
        {
            button1 = new JButton("1");
            button2 = new JButton("2");
            button3 = new JButton("3");
            button4 = new JButton("4");
            button5 = new JButton("5");
            button6 = new JButton("6");
            button7 = new JButton("7");
            button8 = new JButton("8");
            button9 = new JButton("9");
            button10 = new JButton("10");
            button11 = new JButton("11");
            button12 = new JButton("12");
            button13 = new JButton("13");
            button14 = new JButton("14");
            button15 = new JButton("15");
            button16 = new JButton("16");
            button17 = new JButton("17");
            button_output = new JButton("输出");
        } //create buttons
        {
            panel__button.add(button1);
            if(user_name.equals("super_user")){
                panel__button.add(button2);
                panel__button.add(button3);
                panel__button.add(button4);
                panel__button.add(button5);
                panel__button.add(button6);
                panel__button.add(button7);
                panel__button.add(button8);
                panel__button.add(button9);
                panel__button.add(button10);
                panel__button.add(button11);
                panel__button.add(button12);
                panel__button.add(button13);
                panel__button.add(button14);
                panel__button.add(button15);
                panel__button.add(button16);
                panel__button.add(button17);
            } else if (user_name.equals("supply_staff")){
                panel__button.add(button2);
            } else if (user_name.equals("analysis_staff")){
                panel__button.add(button6);
                panel__button.add(button7);
                panel__button.add(button8);
                panel__button.add(button9);
                panel__button.add(button10);
                panel__button.add(button11);
                panel__button.add(button12);
                panel__button.add(button13);
                panel__button.add(button14);
                panel__button.add(button15);
                panel__button.add(button16);
            } else{
                panel__button.add(button3);
                if(user_name.equals("senior_salesman")){
                    panel__button.add(button4);
                    panel__button.add(button5);
                    panel__button.add(button17);
                }
            }
            panel__button.add(button_output);


        } //panel add buttons

            panel__textarea = new JPanel();
            textArea = new JTextArea();
        frame.getContentPane().add(panel__textarea, BorderLayout.CENTER);
        panel__textarea.add(textArea);
        String guide = "Welcome to operate this database, pleast select the number to do what you want:                                                                                                                                                  \n" +
                "1   = Exit and close this window\n" +
                "2   = StockIn()\n" +
                "3   = placeOrder()\n" +
                "4   = updateOrder()\n" +
                "5   = deleteOrder()\n" +
                "6   = getAllStaffCount()\n" +
                "7   = getContractCount()\n" +
                "8   = getOrderCount()\n" +
                "9   = getNeverSoldProductCount()\n" +
                "10 = getFavoriteProductModel()\n" +
                "11 = getAvgStockByCenter()\n" +
                "12 = getProductByNumber()\n" +
                "13 = getContractInfo()\n" +
                "14 = getOrderFromMultipleParameters()\n" +
                "15 = getSaleroomPerDay()\n" +
                "16 = getSaleroomPerMonth()\n" +
                "17 = changeOrderStatus()";
        textArea.setText(guide+"\n\n\n\n\n\n"); //add remark
        int length = textArea.getText().length();

            scrollbar = new JScrollPane(textArea);
        panel__textarea.add(scrollbar);

        // button设置
        {
            {
                button1.addActionListener(e -> {
                    System.exit(0);
                });
            } //button 1
            {
                button_output.addActionListener(e -> {
                    try {
                        String text = textArea.getText().substring(length-4);
                        System.out.println(text);

                        out_counter++;
                        File out_file = new File("out_file\\out_file_" + out_counter + ".txt");
                        if (!out_file.exists()) {
                            out_file.createNewFile();
                        }

                        PrintStream pri_str = new PrintStream(out_file);
                        pri_str.write(text.getBytes(StandardCharsets.UTF_8));
                        pri_str.close();
                    } catch (Exception eee) {
                        eee.printStackTrace();
                    }
                });

                button2.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入要插入的supply center, product model, supply staff num, date, purchase prise, quantity：");
                    text_input.setBounds(350, 400, 800, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String read_Line = textField.getText();

                        String[] allStr = read_Line.split("\"");
                        String[] input;
                        String count = null;

                        if (allStr.length == 1) {
                            input = allStr[0].split(",");
                        } else { // allStr.length == 3
                            String[] strr2 = allStr[2].split(",");
                            input = new String[strr2.length];
                            System.arraycopy(allStr, 1, input, 0, 1);
                            System.arraycopy(strr2, 1, input, 1, strr2.length-1);
                        }

                        try {
                            count = methods.stockIn(con, input[0], input[1], Integer.parseInt(input[2]), input[3], Integer.parseInt(input[4]), Integer.parseInt(input[5]));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        text_input.dispose();   //关闭用于输入的小窗口
                        textArea.setText(guide + "\n\n" + count);
                        textField.setText(null);
                    });
                });
            } //button 2
            {
                button3.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入详细contract num, enterprise, product_model, quantity, contract manager, contract date, estimated delivery date, lodgement date, salesman num, contract type：");
                    text_input.setBounds(350, 400, 800, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        String[] input = str.split(",");
                        
                        String count = null;

                        try {
                            count = methods.placeOrder(con, input[0], input[1], input[2], Integer.parseInt(input[3]), Integer.parseInt(input[4]), input[5], input[6], input[7], Integer.parseInt(input[8]), input[9]);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\n" + count);
                        textField.setText(null);
                    });
                });
            } //button 3
            {
                button4.addActionListener(e -> {
                textField = new TextField();
                text_input = new JFrame();  //  小窗口，用于输入
                button_confrim = new JButton("确定");
                button_close = new JButton("返回");
                panel__confirm_close = new JPanel();

                text_input.setTitle("请输入详细contract, product model, salesman, quantity, estimate delivery date, lodgement date：");
                text_input.setBounds(350, 400, 800, 300);
                text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                text_input.add(textField);
                text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                panel__confirm_close.add(button_confrim);
                panel__confirm_close.add(button_close);
                text_input.setVisible(true);

                button_close.addActionListener(e2 -> {
                    text_input.dispose();
                });
                button_confrim.addActionListener(e1 -> {
                    String str = textField.getText();
                    String[] input = str.split(",");
                    
                    String count = null;

                    try {
                        count = methods.updateOrder(con, input[0], input[1], Integer.parseInt(input[2]), Integer.parseInt(input[3]), input[4], input[5]);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    text_input.dispose();   //关闭用于输入的小窗口


                    textArea.setText(guide + "\n\n" + count);
                    textField.setText(null);
                });
            });
            } //button 4
            {
                button5.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入详细String contract_num, int salesman_num, int seq：");
                    text_input.setBounds(350, 400, 800, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        String[] input = str.split(",");
                        
                        String count = null;

                        try {
                            count = methods.deleteOrder(con, input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\n" + count);
                        textField.setText(null);
                    });
                });
            } //button 5
            {
                button6.addActionListener(e -> {
                    String count = null;
                    try {
                        count = methods.getAllStaffCount(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nStaff count is\n" + count);
                });


                button7.addActionListener(e -> {
                    int count = 0;
                    try {
                        count = methods.getContractCount(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nContract count is " + count);
                });

                button8.addActionListener(e -> {
                    int count = 0;
                    try {
                        count = methods.getOrderCount(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nOrder count is " + count);
                });

                button9.addActionListener(e -> {
                    int count = 0;
                    try {
                        count = methods.getNeverSoldProductCount(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nNever Sold Product Count is " + count);
                });

                button10.addActionListener(e -> {
                    String count = null;
                    try {
                        count = methods.getFavoriteProductModel(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nFavorite Product Model is\n" + count);
                });

                button11.addActionListener(e -> {
                    String count = null;
                    try {
                        count = methods.getAvgStockByCenter(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nAverage Stock By Center is\n" + count);
                });
            } //button 6 ~ 11
            {
                button12.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入要查询的product number：");
                    text_input.setBounds(450, 400, 600, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        
                        String count = null;

                        try {
                            count = methods.getProductByNumber(con, str);
                            ;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\nGet product by product number " + str + " :\n" + count);
                        textField.setText(null);
                    });

                });
            } //button 12
            {
                button13.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入要查询的contract number：");
                    text_input.setBounds(450, 400, 600, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        
                        String count = null;

                        try {
                            count = methods.getContractInfo(con, str);
                            ;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\nGet product by contract number " + str + " :\n" + count);
                        textField.setText(null);
                    });

                });
            } //button 13
            {
                button14.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入详细id, product model, quantity, contract date, estimated delivery date, lodgement date, salesman num：");
                    text_input.setBounds(350, 400, 800, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        String[] input = str.split(",");

                        String count = null;

                        try {
                            count = methods.getOrderFromMultipleParameters(con, input[0], input[1], input[2], input[3], input[4], input[5], input[6]);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\n" + count);
                        textField.setText(null);
                    });
                });
            }
            {
                button15.addActionListener(e -> {
                    String count = null;
                    try {
                        count = methods.getSaleroomPerDay(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nSaleroom Per Day:\n" + count);
                });
            } //button 15
            {
                button16.addActionListener(e -> {
                    String count = null;
                    try {
                        count = methods.getSaleroomPerMonth(con);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    textArea.setText(guide + "\n\nSaleroom Per Month:\n" + count);
                });

            } //button 16
            {
                button17.addActionListener(e -> {
                    textField = new TextField();
                    text_input = new JFrame();  //  小窗口，用于输入
                    button_confrim = new JButton("确定");
                    button_close = new JButton("返回");
                    panel__confirm_close = new JPanel();

                    text_input.setTitle("请输入详细year, month, day：");
                    text_input.setBounds(350, 400, 800, 300);
                    text_input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    text_input.add(textField);
                    text_input.add(panel__confirm_close, BorderLayout.SOUTH);
                    panel__confirm_close.add(button_confrim);
                    panel__confirm_close.add(button_close);
                    text_input.setVisible(true);

                    button_close.addActionListener(e2 -> {
                        text_input.dispose();
                    });
                    button_confrim.addActionListener(e1 -> {
                        String str = textField.getText();
                        String[] input = str.split(",");

                        int count = 0;

                        try {
                            count = methods.changeOrderStatus(con, input[0], input[1], input[2]);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        text_input.dispose();   //关闭用于输入的小窗口


                        textArea.setText(guide + "\n\n" + count + " rows have been updated.");
                        textField.setText(null);
                    });
                });
            }
        }
        
        // 显示
        frame.setVisible(true);
    }

}