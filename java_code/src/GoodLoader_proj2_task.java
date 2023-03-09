import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class GoodLoader_proj2_task{
    private static final int            BATCH_SIZE = 500;
    private static Connection           con = null;
    private static PreparedStatement
            next_val = null, last_val = null, upd_curr_qua = null, upd_curr_qua2 = null, upd_sold_qua = null,
            stmt1_check = null, stmt1 = null,
            stmt2_check1 = null, stmt2_check2 = null, stmt2_check3 = null,  stmt2_1 = null, stmt2_2 = null, stmt2_3 = null,
            stmt_delete_rto = null, stmt_delete_rco = null,
            stmt3_1 = null, stmt3_2 = null, stmt3_3 = null, stmt3_4 = null,  stmt4_1 = null;
    // private static URL               propertyURL = GoodLoader_V5.class.getResource("/loader.cnf");

    private static void openDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        Properties prop = new Properties();  //Properties prop = new Properties(default_prop);
        prop.put("user", "postgres");   prop.put("password", "11271049as");
        String host = "localhost";      String database = "postgres";      String url = "jdbc:postgresql://" + host + "/" + database;

        try {
            con = DriverManager.getConnection(url, prop);  //System.out.println("Successfully connected to the database " + prop.getProperty("database") + " as " + prop.getProperty("user"));
            con.setAutoCommit(false);

            next_val = con.prepareStatement("select nextval('rel_task1_id_seq');");
            last_val = con.prepareStatement("select lastval();");
            upd_curr_qua = con.prepareStatement("update rel_supply_center_product " +
                    "set current_quantity = ?, average_price = ? " +
                    "where supply_center_id = ? and product_id = ?;");      // task 1
            upd_curr_qua2 = con.prepareStatement("update rel_supply_center_product " +
                    "set current_quantity = ? " +
                    "where supply_center_id = ? and product_id = ?;");      // task 2
            upd_sold_qua = con.prepareStatement("update rel_supply_center_product " +
                    "set sold_quantity = ? " +
                    "where supply_center_id = ? and product_id = ?;");


            stmt1_check = con.prepareStatement("select rscp.current_quantity, sc.id, p.id, rscp.average_price " +
                    " from (select * from supply_center where name = ?) sc" +
                    "         join rel_supply_center_staff rscs on sc.id = rscs.supply_center_id" +
                    "         join (select * from staff where number = ? and type = 'Supply Staff') s on s.id = rscs.staff_id" +
                    "         join rel_supply_center_product rscp on sc.id = rscp.supply_center_id" +
                    "         join (select * from product where model = ?) p on p.id = rscp.product_id;");
            stmt1   = con.prepareStatement("insert into rel_task1 (id, supply_center, product_model, supply_staff, date, purchase_price, quantity)" +
                    "values (?, ?, ?, ?, ?, ?, ?);");


            stmt2_check1 = con.prepareStatement("select rscp.current_quantity as old_qua, sc.id, p.id, rscp.sold_quantity as old_total_sold, p.unit_price " +
                    " from (select * from enterprise where name = ?) e" +
                    "         join rel_supply_center_enterprise rsce on e.id = rsce.enterprise_id" +
                    "         join supply_center sc on rsce.supply_center_id = sc.id" +
                    "         join (select * from rel_supply_center_product where current_quantity >= ?) rscp on sc.id = rscp.supply_center_id" +
                    "         join (select * from product where model = ?) p on p.id = rscp.product_id;");
            stmt2_check2 = con.prepareStatement("select * from staff where number = ? and type = 'Salesman';");
            stmt2_check3 = con.prepareStatement("select * from rel_task2_contract where contract_num = ?;");
            stmt2_1 = con.prepareStatement("insert into rel_task2_contract (contract_num, enterprise_name, contract_manager, contract_type)" +
                    "values (?, ?, ?, ?);");
            stmt2_2 = con.prepareStatement("insert into rel_task2_order (product_model, quantity, contract_date, estimated_delivery_date, lodgement_date, salesman_num)" +
                    "values (?, ?, ?, ?, ?, ?);");
            stmt2_3 = con.prepareStatement("insert into rel_contract_order (contract_num, order_id) " +
                    "values (?, ?);");


            stmt3_1 = con.prepareStatement("" +
                    "select rto.id, rscs.supply_center_id, p.id, rto.quantity\n" +
                    "from rel_contract_order rco\n" +
                    "         join rel_task2_order rto on rco.order_id = rto.id\n" +
                    "         join (select * from staff where number = ?) s on rto.salesman_num = s.number\n" +
                    "         join rel_supply_center_staff rscs on s.id = rscs.staff_id\n" +
                    "         join product p on rto.product_model = p.model\n" +
                    "where rco.contract_num = ?\n" +
                    "  and rto.product_model = ?\n" +
                    "  and rto.salesman_num = ?;");
            stmt3_2 = con.prepareStatement("" +
                    "select current_quantity, sold_quantity\n" +
                    "from rel_supply_center_product rscp\n" +
                    "where supply_center_id = ? and product_id = ?;");
            stmt3_3 = con.prepareStatement("" +
                    "update rel_task2_order\n" +
                    "set estimated_delivery_date = ?, \n" +
                    "    lodgement_date          = ? \n" +
                    ",   quantity                = ?  \n" +
                    "where id = ?;\n");
            stmt3_4 = con.prepareStatement("" +
                    "update rel_supply_center_product\n" +
                    "set current_quantity = ?,\n" +
                    "    sold_quantity    = ?\n" +
                    "where supply_center_id = ?\n" +
                    "  and product_id = ?;");
            stmt_delete_rto = con.prepareStatement("delete from rel_task2_order where id = ?;");
            stmt_delete_rco = con.prepareStatement("delete from rel_contract_order where order_id = ?;");


            stmt4_1 = con.prepareStatement("" +
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

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt1 != null)  { stmt1.close(); }
                con.close();
                con = null;
            } catch (Exception ignored) {}
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BufferedReader infile;
        try {
            openDB();
            // Empty all test table
            if (con != null) {
                Statement stmt0 = con.createStatement();
                stmt0.execute("truncate table rel_task1, rel_task2_contract, rel_task2_order, rel_contract_order cascade;");
                stmt0.close();
            }
            // Empty all test table

            // task1
            infile = new BufferedReader(new FileReader("in_stoke_test.csv"));      infile.readLine();
            int cnt_all = 0;
            String read_Line;

            while ((read_Line = infile.readLine()) != null) {
                String[] allStr = read_Line.split("\"");
                String[] this_Line;
                if (allStr.length == 1) {
                    this_Line = allStr[0].split(",");
                } else { // allStr.length == 3
                    String[] strr0 = allStr[0].split(",");
                    String[] strr2 = allStr[2].split(",");
                    this_Line = new String[strr0.length + 1 + strr2.length];
                    System.arraycopy(strr0,  0, this_Line,    0,                        strr0.length);
                    System.arraycopy(allStr, 1, this_Line,            strr0.length,      1);
                    System.arraycopy(strr2,  1, this_Line,    strr0.length + 1,  strr2.length - 1);
                }

                int staff_num = Integer.parseInt(this_Line[3]);
                int this_prise = Integer.parseInt(this_Line[5]);
                int this_quantity = Integer.parseInt(this_Line[6]);

                stmt1_check.setString(1, this_Line[1]);
                stmt1_check.setInt(2, staff_num);
                stmt1_check.setString(3, this_Line[2]);
                ResultSet rs = stmt1_check.executeQuery();
                if (rs.next()) {
                    stmt1.setInt(1, Integer.parseInt(this_Line[0]));
                    stmt1.setString(2,this_Line[1]);
                    stmt1.setString(3,this_Line[2]);
                    stmt1.setInt(4, staff_num);

                    String[] temp = this_Line[4].split("/");
                    this_Line[4] = temp[0] + "-" + temp[1] + "-" + temp[2];

                    stmt1.setDate(5, Date.valueOf(this_Line[4]));
                    stmt1.setInt(6, this_prise);
                    stmt1.setInt(7, this_quantity);
                    stmt1.execute();

                    next_val.execute();

                    upd_curr_qua.setInt(1, rs.getInt(1) + this_quantity);
                    upd_curr_qua.setInt(2,
                            ( this_prise * this_quantity + rs.getInt(1) * rs.getInt(4) ) / (rs.getInt(1) + this_quantity)
                    );
                    upd_curr_qua.setInt(3, rs.getInt(2));
                    upd_curr_qua.setInt(4, rs.getInt(3));
                    upd_curr_qua.execute();
                }
                cnt_all++;
            }
            stmt1_check.close();
            stmt1.close();
            // task1


            // task2
            infile = new BufferedReader(new FileReader("task2_test_data_final_public.tsv"));      infile.readLine();

            while ((read_Line = infile.readLine()) != null) {
                String[] this_Line = read_Line.split("\\t");
                int this_quantity = Integer.parseInt(this_Line[3]);

                stmt2_check1.setString(1, this_Line[1]);
                stmt2_check1.setInt(2, Integer.parseInt(this_Line[3]));
                stmt2_check1.setString(3, this_Line[2]);
                ResultSet rs1 = stmt2_check1.executeQuery();

                stmt2_check2.setInt(1, Integer.parseInt(this_Line[8]));
                ResultSet rs2 = stmt2_check2.executeQuery();

                if (rs1.next() && rs2.next()) {
                    stmt2_check3.setString(1, this_Line[0]);
                    ResultSet rs3 = stmt2_check3.executeQuery();

                    if (! rs3.next()) {
                        stmt2_1.setString(1, this_Line[0]);
                        stmt2_1.setString(2, this_Line[1]);
                        stmt2_1.setInt(3, Integer.parseInt(this_Line[4]));
                        stmt2_1.setString(4, this_Line[9]);
                        stmt2_1.execute();
                    }

                    stmt2_2.setString(1, this_Line[2]);
                    stmt2_2.setInt(2, this_quantity);
                    stmt2_2.setDate(3, Date.valueOf(this_Line[5]));
                    stmt2_2.setDate(4, Date.valueOf(this_Line[6]));
                    stmt2_2.setDate(5, Date.valueOf(this_Line[7]));
                    stmt2_2.setInt(6, Integer.parseInt(this_Line[8]));
                    stmt2_2.execute();

                    ResultSet temp = last_val.executeQuery();  temp.next();

                    stmt2_3.setString(1, this_Line[0]);
                    stmt2_3.setInt(2, temp.getInt(1));
                    stmt2_3.execute();

                    upd_curr_qua2.setInt(1, rs1.getInt(1) - this_quantity);
                    upd_curr_qua2.setInt(2, rs1.getInt(2));
                    upd_curr_qua2.setInt(3, rs1.getInt(3));
                    upd_curr_qua2.execute();

                    upd_sold_qua.setInt(1, rs1.getInt(4) + this_quantity);
                    upd_sold_qua.setInt(2, rs1.getInt(2));
                    upd_sold_qua.setInt(3, rs1.getInt(3));
                    upd_sold_qua.execute();
                }
                cnt_all++;
            }
            stmt2_check1.close();
            stmt2_check2.close();
            stmt2_check3.close();
            stmt2_1.close();
            stmt2_2.close();
            stmt2_3.close();
            // task2


            // task3
            infile = new BufferedReader(new FileReader("update_final_test.tsv"));      infile.readLine();

            while ((read_Line = infile.readLine()) != null) {
                String[] this_Line = read_Line.split("\\t");

                // 0:   contract_num	1:  product_model	        2:  salesman_num
                // 3:   quantity	    4:  estimate_delivery_date	5:  lodgement_date

                stmt3_1.setInt(1, Integer.parseInt(this_Line[2]));
                stmt3_1.setString(2,this_Line[0]);
                stmt3_1.setString(3,this_Line[1]);
                stmt3_1.setInt(4, Integer.parseInt(this_Line[2]));
                ResultSet rs1 = stmt3_1.executeQuery();

                if (rs1.next()) {
                    stmt3_2.setInt(1, rs1.getInt(2));
                    stmt3_2.setInt(2, rs1.getInt(3));
                    ResultSet rs2 = stmt3_2.executeQuery();   rs2.next();

                    int new_this_sold = Integer.parseInt(this_Line[3]);

                    int new_total_curr_qua = rs2.getInt(1) - new_this_sold + rs1.getInt(4);
                    int new_total_sold_qua = rs2.getInt(2) + new_this_sold - rs1.getInt(4);

                    if (new_total_curr_qua >= 0 && new_total_sold_qua >= 0) {
                        if (new_this_sold == 0) {
                            stmt_delete_rco.setInt(1, rs1.getInt(1));
                            stmt_delete_rco.execute();
                            stmt_delete_rto.setInt(1, rs1.getInt(1));
                            stmt_delete_rto.execute();
                        } else { // not 0
                            stmt3_3.setDate(1, Date.valueOf(this_Line[4]));
                            stmt3_3.setDate(2, Date.valueOf(this_Line[5]));
                            stmt3_3.setInt(3, new_this_sold);
                            stmt3_3.setInt(4, rs1.getInt(1));
                            stmt3_3.execute();
                        }
                        stmt3_4.setInt(1,new_total_curr_qua);
                        stmt3_4.setInt(2,new_total_sold_qua);
                        stmt3_4.setInt(3, rs1.getInt(2));
                        stmt3_4.setInt(4, rs1.getInt(3));
                        stmt3_4.execute();
                    }
                }
                cnt_all++;
            }
            stmt3_1.close();
            stmt3_3.close();
            // task 3

            // task 4
            infile = new BufferedReader(new FileReader("delete_final.tsv"));      infile.readLine();

            while ((read_Line = infile.readLine()) != null) {
                String[] this_Line = read_Line.split("\\s+");

                // 0:   contract_num	1:  salesman_num    2:  seq

                stmt4_1.setString(1,this_Line[0]);
                stmt4_1.setInt(2, Integer.parseInt(this_Line[1]));
                stmt4_1.setInt(3, Integer.parseInt(this_Line[2]));
                ResultSet rs1 = stmt4_1.executeQuery();

                if (rs1.next()) {
                    stmt3_2.setInt(1, rs1.getInt(2));
                    stmt3_2.setInt(2, rs1.getInt(3));
                    ResultSet rs2 = stmt3_2.executeQuery();   rs2.next();

                    stmt_delete_rco.setInt(1, rs1.getInt(1));
                    stmt_delete_rco.execute();
                    stmt_delete_rto.setInt(1, rs1.getInt(1));
                    stmt_delete_rto.execute();

                    stmt3_4.setInt(1,rs2.getInt(1) + rs1.getInt(4));
                    stmt3_4.setInt(2,rs2.getInt(2) - rs1.getInt(4));
                    stmt3_4.setInt(3, rs1.getInt(2));
                    stmt3_4.setInt(4, rs1.getInt(3));
                    stmt3_4.execute();
                }
                cnt_all++;
            }
            stmt4_1.close();
            stmt3_2.close();
            stmt3_4.close();
            stmt_delete_rco.close();
            stmt_delete_rto.close();
            // task 4

            con.commit();
            closeDB();

            System.out.println(cnt_all + " lines successfully executed");
            System.out.println("Execute speed : " + (cnt_all * 1000L)/(System.currentTimeMillis() - start) + " lines/s");

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmt1.close();
            } catch (Exception ignored) {}
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmt1.close();
            } catch (Exception ignored) {}
            closeDB();
            System.exit(1);
        }
    }
}