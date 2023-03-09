import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class methods {
    public static Scanner in = new Scanner(System.in);
    public static String user_name;

    public static void main(String[] args) throws SQLException {
        System.out.println("Please input user_name : ");
        user_name = in.next();
        System.out.println("Please input password : ");
        String password = in.next();
        openDB(password);

        print_instruction();

        boolean exit = false;
        while (!exit) {
//            switch (in.nextInt()) {
//                case 1:
//                    System.out.println("Thank you for using our program ! ");
//                    System.out.println("This program is written by Dong Yuhang and Zhang Weining.");
//                    exit = true;
//                    break;
//                case 2:
//                    stockIn(); // StockIn(con);
//                    break;
//                case 3:
//                    placeOrder();
//                    break;
//                case 4:
//                    updateOrder();
//                    break;
//                case 5:
//                    deleteOrder();
//                    break;
//                case 6:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getAllStaffCount();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 7:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        System.out.println(getContractCount());
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 8:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        System.out.println(getOrderCount());
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 9:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        System.out.println(getNeverSoldProductCount());
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 10:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getFavoriteProductModel();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 11:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getAvgStockByCenter();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 12:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getProductByNumber(in.next());  // input product_number
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 13:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getContractInfo(in.next());  // input contract_number
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 14:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getOrderFromMultipleParameters();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 15:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getSaleroomPerDay();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//                case 16:
//                    if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
//                        getSaleroomPerMonth();
//                    } else {
//                        System.out.println("Access denied, you do not have sufficient permissions !");
//                    }
//                    break;
//            }
            System.out.println();
        }
        closeDB();
    }

    public static void print_instruction() {
        System.out.print("Welcome to operate this database, please input number to do what you want:\n\n");
        System.out.printf(" %-7s| %-30s| %-10s\n", "number", "operation", "Remark");
        System.out.println("————————+———————————————————————————————+————————————————————————————————————————————————————");
        System.out.printf(" %-7s| %-30s| %-10s\n", 1, "exit()", "To terminate this program.");
        if (user_name.equals("super_user") || user_name.equals("supply_staff")) {
            System.out.printf(" %-7s| %-30s| %-10s\n", 2, "stockIn()", "Input: supply center, product model, staff number, String date, purchase_price and quantity; Input \"end\" to end");
        }
        if (user_name.equals("super_user") || user_name.equals("senior_salesman") || user_name.equals("salesman")) {
            System.out.printf(" %-7s| %-30s| %-10s\n", 3, "placeOrder()", "Input: contract number, enterprise name, product model, quantity, contract manager number, contract date, estimated delivery date, lodgement date, salesman number and contract type");
            if (!user_name.equals("salesman")) {
                System.out.printf(" %-7s| %-30s| %-10s\n", 4, "updateOrder()", "Input: contract number, product model, salesman number, quantity, estimate delivery date and lodgement date");
                System.out.printf(" %-7s| %-30s| %-10s\n", 5, "deleteOrder()", "Input: contract number, salesman number and sequence");
            }
        }
        if (user_name.equals("super_user") || user_name.equals("analysis_staff")) {
            System.out.printf(" %-7s| %-30s| %-10s\n", 6, "getAllStaffCount()", "To get the count of all staff");
            System.out.printf(" %-7s| %-30s| %-10s\n", 7, "getContractCount()", "To get the count of contract");
            System.out.printf(" %-7s| %-30s| %-10s\n", 8, "getOrderCount()", "To get the count of order");
            System.out.printf(" %-7s| %-30s| %-10s\n", 9, "getNeverSoldProductCount()", "To get the number of product models that are in stock but have never been ordered (sold)");
            System.out.printf(" %-7s| %-30s| %-10s\n", 10, "getFavoriteProductModel()", "Find the models with the highest sold quantity, and the number of sales");
            System.out.printf(" %-7s| %-30s| %-10s\n", 11, "getAvgStockByCenter()", "To get the average quantity of the remaining product models in each supply center");
            System.out.printf(" %-7s| %-30s| %-10s\n", 12, "getProductByNumber()", "Input: product number; To get the current inventory capacity of each product model in each supply center");
            System.out.printf(" %-7s| %-30s| %-10s\n", 13, "getContractInfo()", "Input: contract number; To get the content of the contract.");
            System.out.printf(" %-7s| %-30s| %-10s\n", 15, "getSaleroomPerDay()", "To get the total saleroom of every day");
            System.out.printf(" %-7s| %-30s| %-10s\n", 16, "getSaleroomPerMonth()", "To get the total saleroom of every month");

        }
        System.out.println();
    }

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


    // 2
    public static String stockIn(Connection con_in, String supply_center, String product_model, int supply_staff, String date, int purchase_price, int quantity) throws SQLException{
        con = con_in;
        PreparedStatement check;
        date = date.replace('/','-');
        if (con != null) {
            check = con.prepareStatement("select rscp.current_quantity, sc.id, p.id, rscp.average_price " +
                    " from (select * from supply_center where name = ?) sc\n" +
                    "         join rel_supply_center_staff rscs on sc.id = rscs.supply_center_id\n" +
                    "         join (select * from staff where number = ? and type = 'Supply Staff') s on s.id = rscs.staff_id\n" +
                    "         join rel_supply_center_product rscp on sc.id = rscp.supply_center_id\n" +
                    "         join (select * from product where model = ?) p on p.id = rscp.product_id;");
            check.setString(1, supply_center);
            check.setInt(2, supply_staff);
            check.setString(3, product_model);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                PreparedStatement seq_next = con.prepareStatement("select nextval('rel_task1_id_seq');");
                PreparedStatement seq_check = con.prepareStatement("select * from rel_task1 where id = lastval();");
                ResultSet rs_sc;

                do {
                    seq_next.execute();
                    rs_sc = seq_check.executeQuery();
                } while (rs_sc.next());

                PreparedStatement stmt0 = con.prepareStatement("insert into rel_task1 (supply_center, product_model, supply_staff, date, purchase_price, quantity)\n" +
                        "values (?, ?, ?, ?, ?, ?);");
                stmt0.setString(1, supply_center);
                stmt0.setString(2, product_model);
                stmt0.setInt(3, supply_staff);
                stmt0.setDate(4, java.sql.Date.valueOf(date));
                stmt0.setInt(5, purchase_price);
                stmt0.setInt(6, quantity);
                stmt0.execute();

                PreparedStatement upd_curr_qua = con.prepareStatement("update rel_supply_center_product " +
                        "set current_quantity = ?, average_price = ? " +
                        "where supply_center_id = ? and product_id = ?;");
                upd_curr_qua.setInt(1, rs.getInt(1) + quantity);
                upd_curr_qua.setInt(2,
                        (purchase_price * quantity + rs.getInt(1) * rs.getInt(4)) / (rs.getInt(1) + quantity));
                upd_curr_qua.setInt(3, rs.getInt(2));
                upd_curr_qua.setInt(4, rs.getInt(3));
                upd_curr_qua.execute();

                PreparedStatement verify = con.prepareStatement("select * from rel_task1\n" +
                        "where supply_center = ? and product_model = ? and supply_staff = ?\n" +
                        "  and date = ? and purchase_price = ? and quantity = ? and id = lastval();");
                verify.setString(1, supply_center);
                verify.setString(2, product_model);
                verify.setInt(3, supply_staff);
                verify.setDate(4, java.sql.Date.valueOf(date));
                verify.setInt(5, purchase_price);
                verify.setInt(6, quantity);
                rs = verify.executeQuery();

                if (rs.next()) {
                    con.commit();
                    return "Insert successfully !";
                } else {
                    return "Unknown wrong!!!";
                }
            } else {
                return "Input data invalid.";
            }
        }
        return "Unconnected";
    }

    // 3
    public static String placeOrder(Connection con_in, String contract_num, String enterprise, String product_model, int quantity, int contract_manager, String contract_date, String estimated_delivery_date, String lodgement_date, int salesman_num, String contract_type
    ) throws SQLException {
        con = con_in;
        if (con != null) {
            PreparedStatement check1;   //check enterprise & product_model & current_quantity > quantity in order
            check1 = con.prepareStatement("select (rscp.current_quantity - ?) as sub, sc.id, p.id, rscp.sold_quantity as sold" +
                    " from (select * from enterprise where name = ?) e" +
                    "         join rel_supply_center_enterprise rsce on e.id = rsce.enterprise_id" +
                    "         join supply_center sc on rsce.supply_center_id = sc.id" +
                    "         join (select * from rel_supply_center_product where current_quantity >= ?) rscp on sc.id = rscp.supply_center_id" +
                    "         join (select * from product where model = ?) p on p.id = rscp.product_id;");
            check1.setInt(1, quantity);
            check1.setString(2, enterprise);
            check1.setInt(3, quantity);
            check1.setString(4, product_model);
            ResultSet rs1 = check1.executeQuery();

            PreparedStatement check2;   //check salesman
            check2 = con.prepareStatement("select * from staff where number = ? and type = 'Salesman';");
            check2.setInt(1, salesman_num);
            ResultSet rs2 = check2.executeQuery();

            if (rs1.next() && rs2.next()) {
                PreparedStatement check3 = con.prepareStatement("select * from rel_task2_contract where contract_num = ?;");
                check3.setString(1, contract_num);
                ResultSet rs3 = check3.executeQuery();

                if (! rs3.next()) {
                    PreparedStatement stmt1 = con.prepareStatement("insert into rel_task2_contract " +
                            "(contract_num, enterprise_name, contract_manager, contract_type)" +
                            "values (?, ?, ?, ?);");
                    stmt1.setString(1, contract_num);
                    stmt1.setString(2, enterprise);
                    stmt1.setInt(3, contract_manager);
                    stmt1.setString(4, contract_type);
                    stmt1.execute();
                }

                PreparedStatement stmt2 = con.prepareStatement("insert into rel_task2_order " +
                        "(product_model, quantity, contract_date, estimated_delivery_date, lodgement_date, salesman_num)" +
                        "values (?, ?, ?, ?, ?, ?);");
                stmt2.setString(1, product_model);
                stmt2.setInt(2, quantity);
                stmt2.setDate(3, java.sql.Date.valueOf(contract_date.replace('/', '-')));
                stmt2.setDate(4, java.sql.Date.valueOf(estimated_delivery_date.replace('/', '-')));
                stmt2.setDate(5, java.sql.Date.valueOf(lodgement_date.replace('/', '-')));
                stmt2.setInt(6, salesman_num);
                stmt2.execute();

                ResultSet temp = con.prepareStatement("select lastval();").executeQuery(); temp.next();

                PreparedStatement stmt3 = con.prepareStatement("insert into rel_contract_order (contract_num, order_id) " +
                        "values (?, ?);");
                stmt3.setString(1, contract_num);
                stmt3.setInt(2, temp.getInt(1));
                stmt3.execute();

                PreparedStatement upd_quantity = con.prepareStatement("update rel_supply_center_product " +
                        "set current_quantity = ?, sold_quantity = ? " +
                        "where supply_center_id = ? and product_id = ?;");
                upd_quantity.setInt(1, rs1.getInt(1));
                upd_quantity.setInt(2, rs1.getInt(4) + quantity);
                upd_quantity.setInt(3, rs1.getInt(2));
                upd_quantity.setInt(4, rs1.getInt(3));
                upd_quantity.execute();

                con.commit();
                return "Place order successfully !";
            }
            return "Something wrong !";
        } else {
            return "Unconnected";
        }
    }

    //4
    public static String updateOrder(Connection con_in, String contract, String product_model, int salesman, int quantity, String estimate_delivery_date, String lodgement_date) throws SQLException {
        con = con_in;
        if (con != null) {
            PreparedStatement stmt1 = con.prepareStatement(
                    "select rto.id, rscs.supply_center_id, p.id, rto.quantity\n" +
                            "from rel_contract_order rco\n" +
                            "         join rel_task2_order rto on rco.order_id = rto.id\n" +
                            "         join (select * from staff where number = ?) s on rto.salesman_num = s.number\n" +
                            "         join rel_supply_center_staff rscs on s.id = rscs.staff_id\n" +
                            "         join product p on rto.product_model = p.model\n" +
                            "where rco.contract_num = ?\n" +
                            "  and rto.product_model = ?\n" +
                            "  and rto.salesman_num = ?;"
            );
            stmt1.setInt(1, salesman);
            stmt1.setString(2, contract);
            stmt1.setString(3, product_model);
            stmt1.setInt(4, salesman);
            ResultSet rs1 = stmt1.executeQuery();

            if (rs1.next()) {
                PreparedStatement stmt2 = con.prepareStatement(
                        "select current_quantity, sold_quantity\n" +
                                "from rel_supply_center_product rscp\n" +
                                "where supply_center_id = ? and product_id = ?;"
                );
                stmt2.setInt(1, rs1.getInt(2));
                stmt2.setInt(2, rs1.getInt(3));
                ResultSet rs2 = stmt2.executeQuery();
                rs2.next();

                int new_total_curr_qua = rs2.getInt(1) - quantity + rs1.getInt(4);
                int new_total_sold_qua = rs2.getInt(2) + quantity - rs1.getInt(4);

                if (new_total_curr_qua >= 0 && new_total_sold_qua >= 0) {
                    if (quantity == 0) {
                        PreparedStatement stmt_delete_rto = con.prepareStatement("delete from rel_task2_order where id = ?;");
                        PreparedStatement stmt_delete_rco = con.prepareStatement("delete from rel_contract_order where order_id = ?;");
                        stmt_delete_rco.setInt(1, rs1.getInt(1));
                        stmt_delete_rco.execute();
                        stmt_delete_rto.setInt(1, rs1.getInt(1));
                        stmt_delete_rto.execute();
                    } else { // not 0
                        PreparedStatement stmt3 = con.prepareStatement(
                                "update rel_task2_order\n" +
                                        "set estimated_delivery_date = ?, \n" +
                                        "    lodgement_date          = ? \n" +
                                        ",   quantity                = ?  \n" +
                                        "where id = ?;\n");
                        stmt3.setDate(1, java.sql.Date.valueOf(estimate_delivery_date));
                        stmt3.setDate(2, java.sql.Date.valueOf(lodgement_date));
                        stmt3.setInt(3, quantity);
                        stmt3.setInt(4, rs1.getInt(1));
                        stmt3.execute();
                    }
                    PreparedStatement stmt4 = con.prepareStatement("" +
                            "update rel_supply_center_product\n" +
                            "set current_quantity = ?,\n" +
                            "    sold_quantity    = ?\n" +
                            "where supply_center_id = ?\n" +
                            "  and product_id = ?;");
                    stmt4.setInt(1, new_total_curr_qua);
                    stmt4.setInt(2, new_total_sold_qua);
                    stmt4.setInt(3, rs1.getInt(2));
                    stmt4.setInt(4, rs1.getInt(3));
                    stmt4.execute();

                    PreparedStatement check2 = con.prepareStatement("select * from rel_task2_order\n" +
                            "where product_model = ? and quantity = ?\n" +
                            "  and estimated_delivery_date = ? and lodgement_date = ? and salesman_num = ?;");
                    check2.setString(1, product_model);
                    check2.setInt(2, quantity);
                    check2.setDate(3, java.sql.Date.valueOf(estimate_delivery_date));
                    check2.setDate(4, java.sql.Date.valueOf(lodgement_date));
                    check2.setInt(5, salesman);
                    ResultSet rs = check2.executeQuery();
                    if (rs.next()) {
                        con.commit();
                        return "Update successfully !";
                    } else {
                        return "Something wrong !";
                    }
                } else {
                    return "Invalid quantity !";
                }
            } else {
                return "Input invalid !";
            }
        } else {
            return "Unconnected";
        }
    }

    //5
    public static String deleteOrder(Connection con_in, String contract_num, int salesman_num, int seq) throws SQLException{
        con = con_in;
        if(con != null){
            PreparedStatement  stmt4_1 = con.prepareStatement("" +
                    "select *\n" +
                    "from (\n" +
                    "         select rto.id,\n" +
                    "                rscs.supply_center_id,\n" +
                    "                p.id,\n" +
                    "                rto.quantity,\n" +
                    "                row_number() over (order by estimated_delivery_date, product_model) as rn\n" +
                    "         from rel_task2_order rto\n" +
                    "                  join rel_contract_order rco on rto.id = rco.order_id\n" +
                    "                  join staff s on s.number = rto.salesman_num\n" +
                    "                  join rel_supply_center_staff rscs on s.id = rscs.staff_id\n" +
                    "                  join product p on rto.product_model = p.model\n" +
                    "         where rco.contract_num = ?\n" +
                    "           and rto.salesman_num = ?\n" +
                    "         order by estimated_delivery_date, product_model\n" +
                    "     ) tempA\n" +
                    "where rn = ?;");
            stmt4_1.setString(1, contract_num);
            stmt4_1.setInt(2, salesman_num);
            stmt4_1.setInt(3, seq);
            ResultSet rs1 = stmt4_1.executeQuery();


            if (rs1.next()) {
                PreparedStatement stmt3_2 = con.prepareStatement("" +
                        "select current_quantity, sold_quantity\n" +
                        "from rel_supply_center_product rscp\n" +
                        "where supply_center_id = ? and product_id = ?;");
                stmt3_2.setInt(1, rs1.getInt(2));
                stmt3_2.setInt(2, rs1.getInt(3));
                ResultSet rs2 = stmt3_2.executeQuery();   rs2.next();


                PreparedStatement count_row_co = con.prepareStatement("select count(*) from rel_contract_order;");
                PreparedStatement count_row_t2o = con.prepareStatement("select count(*) from rel_task2_order;");

                ResultSet rs_cr = count_row_co.executeQuery(); rs_cr.next();
                int before_delete_co = rs_cr.getInt(1);
                rs_cr = count_row_t2o.executeQuery();  rs_cr.next();
                int before_delete_t2o = rs_cr.getInt(1);


                PreparedStatement stmt_delete_rel_contract_order = con.prepareStatement
                        ("delete from rel_contract_order where order_id = ?;");
                PreparedStatement stmt_delete_rto = con.prepareStatement("delete from rel_task2_order where id = ?;");
                stmt_delete_rel_contract_order.setInt(1, rs1.getInt(1));
                stmt_delete_rel_contract_order.execute();
                stmt_delete_rto.setInt(1, rs1.getInt(1));
                stmt_delete_rto.execute();


                rs_cr = count_row_co.executeQuery();  rs_cr.next();
                int after_delete_co = rs_cr.getInt(1);
                rs_cr = count_row_co.executeQuery();  rs_cr.next();
                int after_delete_t2o = rs_cr.getInt(1);


                PreparedStatement stmt3_4 = con.prepareStatement("" +
                        "update rel_supply_center_product\n" +
                        "set current_quantity = ?,\n" +
                        "    sold_quantity    = ?\n" +
                        "where supply_center_id = ?\n" +
                        "  and product_id = ?;");
                stmt3_4.setInt(1, rs2.getInt(1) + rs1.getInt(4));
                stmt3_4.setInt(2, rs2.getInt(2) - rs1.getInt(4));
                stmt3_4.setInt(3, rs1.getInt(2));
                stmt3_4.setInt(4, rs1.getInt(3));
                stmt3_4.execute();


                if (before_delete_co - after_delete_co == 1 && before_delete_t2o - after_delete_t2o == 1) {
                    con.commit();
                    return "Delete successfully !";
                } else {
                    return "Something wrong in delete !\n" + "Before_co: "+before_delete_co+",  After_co: "+after_delete_co + "\n" + "Before_t2o: "+before_delete_t2o+",  After_t2o: "+after_delete_t2o;
                }
            } else {
                return "Invalid input !";
            }
        } else {
            return "Unconnected";
        }
    }

    // 6
    public static String getAllStaffCount(Connection con_in) throws SQLException {
        System.out.println("getAllStaffCount()");
        con = con_in;
        int rrreturn_cnt = 0;
        String rrreturn_type = "";
        Statement stmt0;
        StringBuffer count = new StringBuffer();
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("select type, count(*) as cnt from staff group by type;");

            while (rs.next()) {
                rrreturn_type = rs.getString(1);
                rrreturn_cnt = rs.getInt(2);
                count.append(rrreturn_cnt).append("\t:\t").append(rrreturn_type).append("\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    //7
    public static int getContractCount(Connection con_in) throws SQLException {
        con = con_in;
        System.out.println("getContractCount()");
        int rrreturn = 0;
        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("select count(*) as cnt from rel_task2_contract;");
            rs.next();
            rrreturn = rs.getInt("cnt");
            stmt0.close();
        }
        return rrreturn;
    }

    //8
    public static int getOrderCount(Connection con_in) throws SQLException {
        System.out.println("getOrderCount()");
        con = con_in;
        int rrreturn = 0;
        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("select count(*) as cnt from rel_task2_order;");
            rs.next();
            rrreturn = rs.getInt("cnt");
            stmt0.close();
        }
        return rrreturn;
    }

    //9
    public static int getNeverSoldProductCount(Connection con_in) throws SQLException {
        System.out.println("getNeverSoldProductCount()");
        con = con_in;
        int rrreturn = 0;
        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("select count(*)\n" +
                    "from (select product_id, sum(current_quantity) as scq, sum(sold_quantity) as ssq\n" +
                    "      from rel_supply_center_product\n" +
                    "      group by product_id\n" +
                    "     ) temp\n" +
                    "where scq > 0 and ssq = 0;");
            rs.next();
            rrreturn = rs.getInt(1);
            stmt0.close();
        }
        return rrreturn;
    }

    //10
    public static String getFavoriteProductModel(Connection con_in) throws SQLException {
        System.out.println("getFavoriteProductModel()");
        con = con_in;
        int rrreturn_cnt = 0;
        String rrreturn_type = "";
        Statement stmt0;
        StringBuffer count = new StringBuffer();
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("with temp as (\n" +
                    "    select product_id, sum(sold_quantity) as ssq\n" +
                    "    from rel_supply_center_product\n" +
                    "    group by product_id)\n" +
                    "\n" +
                    "select product.model, ssq from temp join product on temp.product_id = product.id\n" +
                    "where ssq = (select max(ssq) from temp);");
            while (rs.next()) {
                rrreturn_type = rs.getString(1);
                rrreturn_cnt = rs.getInt(2);
                count.append(rrreturn_cnt).append("\t:\t").append(rrreturn_type +"\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    //11
    public static String getAvgStockByCenter(Connection con_in) throws SQLException {
        System.out.println("getAvgStockByCenter()");
        con = con_in;
        double rrreturn_cnt = 0;
        String rrreturn_type = "";
        Statement stmt0;
        StringBuffer count = new StringBuffer();
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("select supply_center.name, cast( (cast(sum as decimal(18, 2)) / cnt) as decimal(18, 1))\n" +
                    "from (select supply_center_id, sum(current_quantity) as sum, count(*) as cnt\n" +
                    "      from (select * from rel_supply_center_product where current_quantity > 0) ss\n" +
                    "      group by supply_center_id) temp\n" +
                    "         join supply_center\n" +
                    "              on temp.supply_center_id = supply_center.id\n" +
                    "order by supply_center.name;");
            while (rs.next()) {
                rrreturn_type = rs.getString(1);
                rrreturn_cnt = rs.getDouble(2);
                count.append(rrreturn_cnt).append("\t:\t").append(rrreturn_type).append("\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    //12
    public static String getProductByNumber(Connection con_in, String p_num) throws SQLException {
        con = con_in;
        int rrreturn_cnt = 0;
        String rrreturn_name = "";
        String rrreturn_model = "";
        StringBuffer count = new StringBuffer();

        PreparedStatement stmt0;
        if (con != null) {
            stmt0 = con.prepareStatement("select supply_center.name, temp.model, temp.current_quantity\n" +
                    "from (select rs.supply_center_id, p.model, sum(rs.current_quantity) current_quantity\n" +
                    "      from rel_supply_center_product rs\n" +
                    "               join product p on p.id = rs.product_id\n" +
                    "      where p.number = ?\n" +
                    "      group by rs.supply_center_id, p.model) temp\n" +
                    "         join supply_center\n" +
                    "              on temp.supply_center_id = supply_center.id\n" +
                    "order by supply_center.name;");
            stmt0.setString(1, p_num);
            ResultSet rs = stmt0.executeQuery();
            count.append("       quantity        :         model        :                      supply center\n");
            count.append("---------------------------------------------------------------------------------------------\n");
            while(rs.next()){
                rrreturn_name = rs.getString(1);
                rrreturn_model = rs.getString(2);
                rrreturn_cnt = rs.getInt(3);
                count.append("             " + rrreturn_cnt + "\t: " + rrreturn_model + "\t: " + rrreturn_name + "\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    //13
    public static String getContractInfo(Connection con_in, String c_num) throws SQLException{
        con = con_in;
        String contract_number = "";
        String contract_manager_name = "";
        String enterprise_name = "";
        String supply_center = "";
        StringBuffer count = new StringBuffer();

        PreparedStatement stmt0;
        if (con != null) {
            stmt0 = con.prepareStatement("select rtc.contract_num, s.name, rtc.enterprise_name, sc.name\n" +
                    "from rel_task2_contract rtc\n" +
                    "         join enterprise e\n" +
                    "              on e.name = rtc.enterprise_name\n" +
                    "         join rel_supply_center_enterprise rsce\n" +
                    "              on e.id = rsce.enterprise_id\n" +
                    "         join supply_center sc\n" +
                    "              on sc.id = rsce.supply_center_id\n" +
                    "         join staff s\n" +
                    "              on s.number = rtc.contract_manager\n" +
                    "where contract_num = ?;\n");
            stmt0.setString(1, c_num);
            ResultSet rs = stmt0.executeQuery();
            count.append("\"contract number\"—\"contract manager name\"—\"enterprise name\"—\"supply center\n");
            count.append("------------------------------------------------------------------------------------------------------------\n");
            while (rs.next()) {
                contract_number = rs.getString(1);
                contract_manager_name = rs.getString(2);
                enterprise_name = rs.getString(3);
                supply_center = rs.getString(4);
                count.append("   " + contract_number + "\t|   " + contract_manager_name + "\t|   " +  enterprise_name + "\t|   " + supply_center + "\n");
            }
            System.out.println();

            String product_model = "";
            String salesman_name = "";
            int quantity = 0;
            int unit_price = 0;
            Date estimate_delivery_date = new Date(1970-1-1);
            Date lodgement_date = new Date(1970-1-1);

            stmt0 = con.prepareStatement("select rto_temp.product_model, s.name, rto_temp.quantity, p.unit_price, estimated_delivery_date, lodgement_date\n" +
                    "from (select rto.*\n" +
                    "      from rel_task2_contract rtc\n" +
                    "               join rel_contract_order rco on rtc.contract_num = rco.contract_num\n" +
                    "               join rel_task2_order rto on rto.id = rco.order_id\n" +
                    "      where rtc.contract_num = ?) rto_temp\n" +
                    "         join product p on rto_temp.product_model = p.model\n" +
                    "         join staff s on rto_temp.salesman_num = s.number;");
            stmt0.setString(1, c_num);
            rs = stmt0.executeQuery();
            count.append("\n\n\n \"salesman_name\"—\"quantity\"—\"unit_price\"—\"estimate_delivery_date\"—\"lodgement_date\"—\"product model\" \n");
            count.append("--------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            while(rs.next()){
                product_model = rs.getString(1);
                salesman_name = rs.getString(2);
                quantity = rs.getInt(3);
                unit_price = rs.getInt(4);
                estimate_delivery_date = rs.getDate(5);
                lodgement_date = rs.getDate(6);
                count.append(salesman_name + "\t|   " + quantity + "\t|   " + unit_price + "\t|   " + estimate_delivery_date + "\t|   " + lodgement_date + "\t|   " + product_model + "\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    // 14
    public static String getOrderFromMultipleParameters(Connection con_in, String s1, String s2, String s3, String s4, String s5, String s6, String s7) throws SQLException {
        con = con_in;
        StringBuffer count = new StringBuffer();

        if (con != null) {
            System.out.println("getOrderFromMultipleParameters()");
            System.out.println();
            int count_no_empty = 0;
            boolean s1_is_no_empty, s2_is_no_empty, s3_is_no_empty, s4_is_no_empty, s5_is_no_empty, s6_is_no_empty, s7_is_no_empty;
            System.out.println("Please input id : ");                            s1_is_no_empty = ( ! s1.equals(" "));      if (s1_is_no_empty){count_no_empty++;}
            System.out.println("Please input product_model : ");                 s2_is_no_empty = ( ! s2.equals(" "));      if (s2_is_no_empty){count_no_empty++;}
            System.out.println("Please input quantity : ");                      s3_is_no_empty = ( ! s3.equals(" "));      if (s3_is_no_empty){count_no_empty++;}
            System.out.println("Please input contract_date : ");                 s4_is_no_empty = ( ! s4.equals(" "));      if (s4_is_no_empty){count_no_empty++;}
            System.out.println("Please input estimated_delivery_date : ");       s5_is_no_empty = ( ! s5.equals(" "));      if (s5_is_no_empty){count_no_empty++;}
            System.out.println("Please input lodgement_date : ");                s6_is_no_empty = ( ! s6.equals(" "));      if (s6_is_no_empty){count_no_empty++;}
            System.out.println("Please input salesman_num : ");                  s7_is_no_empty = ( ! s7.equals(" "));      if (s7_is_no_empty){count_no_empty++;}

            String origin_stmt =
                    "select rt2c.*, rt2o.*\n" +
                    "from rel_contract_order rco\n" +
                    "         join rel_task2_order rt2o on rt2o.id = rco.order_id\n" +
                    "         join rel_task2_contract rt2c on rco.contract_num = rt2c.contract_num\n";
            String s1_stmt = "", s2_stmt = "", s3_stmt = "", s4_stmt = "", s5_stmt = "", s6_stmt = "", s7_stmt = "";

            PreparedStatement stmt0;
            if (count_no_empty > 0) {
                if (s1_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s1_stmt = " id = " + s1 + " and ";
                    } else {
                        s1_stmt = " id = " + s1;
                    }
                }
                if (s2_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s2_stmt = " product_model = '" + s2 + "' and ";
                    } else {
                        s2_stmt = " product_model = '" + s2 + "' ";
                    }
                }
                if (s3_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s3_stmt = " quantity = " + s3 + " and ";
                    } else {
                        s3_stmt = " quantity = " + s3;
                    }
                }
                if (s4_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s4_stmt = " contract_date = to_date('" + s4 + "', 'YYYY-MM-DD') and ";
                    } else {
                        s4_stmt = " contract_date = to_date('" + s4 + "', 'YYYY-MM-DD') ";
                    }
                }
                if (s5_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s5_stmt = " estimated_delivery_date = to_date('" + s5 + "', 'YYYY-MM-DD') and ";
                    } else {
                        s5_stmt = " estimated_delivery_date = to_date('" + s5 + "', 'YYYY-MM-DD') ";
                    }
                }
                if (s6_is_no_empty) {
                    if (--count_no_empty > 0) {
                        s6_stmt = " lodgement_date = to_date('" + s6 + "', 'YYYY-MM-DD') and ";
                    } else {
                        s6_stmt = " lodgement_date = to_date('" + s6 + "', 'YYYY-MM-DD') ";
                    }
                }
                if (s7_is_no_empty) {
                    s7_stmt = " salesman_num = " + s7;
                }
                origin_stmt = origin_stmt + " where " + s1_stmt + s2_stmt + s3_stmt + s4_stmt + s5_stmt + s6_stmt + s7_stmt;
            }
            stmt0 = con.prepareStatement(origin_stmt);
            ResultSet rs = stmt0.executeQuery();
            count.append("\"contract number\"  \"enterprise\" \"manager number\" \"contract type\" \"unit price\" \"quantity\"    \"contract date\"\"estimated delivery date\"\"lodgement date\" \"salesman num\" \"product model\"\n");
            while (rs.next()) {
                count.append( "    " +
                        rs.getString(1) +"          "+  rs.getString(2) +"\t"+  rs.getInt(3)    +"\t"+
                        rs.getString(4) +"\t"+  rs.getInt(5)    +"\t"+
                        rs.getInt(7)    +"\t"+  rs.getDate(8)   +"\t"+  rs.getDate(9)   +"\t"+
                        rs.getDate(10)  +"\t"+  rs.getInt(11)   +"\t"+  rs.getString(6) + "\n"
                );
            }
            stmt0.close();
        }
        return count.toString();
    }

    // 15
    public static String getSaleroomPerDay(Connection con_in) throws SQLException {
        con = con_in;

        System.out.println("getSaleroomPerDay()");
        Date rrreturn_date;
        int rrreturn_cnt;
        StringBuffer count = new StringBuffer();

        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("" +
                    "select lodgement_date, sum(saleroom) as saleroom_per_day\n" +
                    "from (select quantity * unit_price as saleroom, lodgement_date\n" +
                    "      from rel_task2_order rt2o\n" +
                    "               join product p on rt2o.product_model = p.model) temp\n" +
                    "group by lodgement_date\n" +
                    "order by lodgement_date;");
            while (rs.next()) {
                rrreturn_date = rs.getDate(1);
                rrreturn_cnt = rs.getInt(2);
                count.append("             " + rrreturn_date + "\t:\t" + rrreturn_cnt + "\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    // 16
    public static String getSaleroomPerMonth(Connection con_in) throws SQLException {
        con = con_in;

        System.out.println("getSaleroomPerMonth()");
        String rrreturn_date;
        int rrreturn_cnt;
        StringBuffer count = new StringBuffer();

        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            ResultSet rs = stmt0.executeQuery("" +
                    "select month, sum(saleroom) as saleroom_per_month\n" +
                    "from (select quantity * unit_price as saleroom, substr(to_char(lodgement_date, 'YYYY-MM-DD'), 1, 7) as month\n" +
                    "      from rel_task2_order rt2o\n" +
                    "               join product p on rt2o.product_model = p.model) temp\n" +
                    "group by month\n" +
                    "order by month;");
            while (rs.next()) {
                rrreturn_date = rs.getString(1);
                rrreturn_cnt = rs.getInt(2);
                count.append("             " + rrreturn_date + "\t:\t" + rrreturn_cnt + "\n");
            }
            stmt0.close();
        }
        return count.toString();
    }

    // 17
    public static int changeOrderStatus(Connection con_in, String year, String month,String day) throws SQLException {
        con = con_in;

        String this_month = month;      if (month.equals(" ")) { this_month = "01"; }
        String this_day = day;          if (day.equals(" "))   { this_day = "01"; }

        String this_date = year + "-" + this_month + "-" + this_day;

        PreparedStatement stmt0;
        if (con != null) {
            stmt0 = con.prepareStatement(
                    "update rel_task2_contract\n" +
                    "set contract_type = 'Not_Finished'\n" +
                    "where contract_num in (select distinct rco.contract_num\n" +
                    "                       from rel_task2_order rt2o\n" +
                    "                                join rel_contract_order rco on rt2o.id = rco.order_id\n" +
                    "                                join rel_task2_contract rt2c on rco.contract_num = rt2c.contract_num\n" +
                    "                       where contract_date > to_date( ?, 'YYYY-MM-DD') );");
            stmt0.setString(1, this_date);
            int count = stmt0.executeUpdate();
            con.commit();
            return count;
        }
        return 0;
    }
}