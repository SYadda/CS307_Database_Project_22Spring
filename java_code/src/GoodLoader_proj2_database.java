import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class GoodLoader_proj2_database {
    private static final int            BATCH_SIZE = 500;
    private static Connection           con = null;
    private static PreparedStatement stmt1 = null, stmt2_1 = null, stmt2_2 = null, stmt3_1 = null, stmt3_2 = null, stmt4_1 = null, stmt4_2 = null;
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
            stmt1   = con.prepareStatement("insert into supply_center(id, name) " +
                    "values (?, ?);");
            stmt2_1 = con.prepareStatement("insert into enterprise(id, name, country, city, industry) " +
                    "values (?, ?, ?, ?, ?);");
            stmt2_2 = con.prepareStatement("insert into rel_supply_center_enterprise(supply_center_id, enterprise_id) " +
                    "values ((select s.id from supply_center s where s.name = ?), (select ?));");
            stmt3_1 = con.prepareStatement("insert into staff(id, name, age, gender, number, mobile_number, type) " +
                    "values (?, ?, ?, ?, ?, ?, ?);");
            stmt3_2 = con.prepareStatement("insert into rel_supply_center_staff(supply_center_id, staff_id) " +
                    "values ((select s.id from supply_center s where s.name = ?), (select ?));");
            stmt4_1 = con.prepareStatement("insert into product(id, number, model, name, unit_price) " +
                    "values (?, ?, ?, ?, ?);");
            stmt4_2 = con.prepareStatement("insert into rel_supply_center_product(supply_center_id, product_id, average_price, current_quantity, sold_quantity)" +
                    " values (?, ?, 0, 0, 0);");
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
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BufferedReader infile;
        try {
            openDB();
            // Empty all table
            if (con != null) {
                Statement stmt0 = con.createStatement();
                stmt0.execute("truncate table supply_center, enterprise, product, staff, rel_supply_center_enterprise, rel_supply_center_product, rel_supply_center_staff, rel_task1, rel_task2_contract, rel_task2_order, rel_contract_order cascade;");
                stmt0.close();
            }
            // Empty all table


            // 1. load center.csv
            infile = new BufferedReader(new FileReader("center.csv"));      infile.readLine();
            int cnt_BATCH = 0, cnt_all = 0;
            String read_Line;

            while ((read_Line = infile.readLine()) != null) {
                String[] allStr = read_Line.split("\"");

                String[] this_Line;
                if (allStr.length == 1) {
                    this_Line = allStr[0].split(",");
                } else { // allStr.length == 2
                    this_Line = new String[] {allStr[0].split("")[0], allStr[1]};
                }

                stmt1.setInt(1, Integer.parseInt(this_Line[0]));
                stmt1.setString(2, this_Line[1]);
                stmt1.addBatch();
                if (++cnt_BATCH % BATCH_SIZE == 0) {
                    stmt1.executeBatch();
                    stmt1.clearBatch();
                }
            }
            if (cnt_BATCH % BATCH_SIZE != 0) {
                stmt1.executeBatch();
            }
            stmt1.close();
            cnt_all += cnt_BATCH;
            // 1. load center.csv


            // 2. load enterprise.csv
            infile = new BufferedReader(new FileReader("enterprise.csv"));      infile.readLine();
            cnt_BATCH = 0;

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

                stmt2_1.setInt(1, Integer.parseInt(this_Line[0]));
                stmt2_1.setString(2, this_Line[1]);
                stmt2_1.setString(3, this_Line[2]);
                stmt2_1.setString(4, this_Line[3]);
                stmt2_1.setString(5, this_Line[5]);
                stmt2_1.addBatch();

                stmt2_2.setString(1, this_Line[4]);
                stmt2_2.setInt(2, Integer.parseInt(this_Line[0]));
                stmt2_2.addBatch();

                if (++cnt_BATCH % BATCH_SIZE == 0) {
                    stmt2_1.executeBatch();
                    stmt2_2.executeBatch();
                    stmt2_1.clearBatch();
                    stmt2_2.clearBatch();
                }
            }
            if (cnt_BATCH % BATCH_SIZE != 0) {
                stmt2_1.executeBatch();
                stmt2_2.executeBatch();
            }
            stmt2_1.close();
            stmt2_2.close();
            cnt_all += cnt_BATCH * 2;
            // 2. load enterprise.csv


            // 3. load staff.csv
            infile = new BufferedReader(new FileReader("staff.csv"));      infile.readLine();
            cnt_BATCH = 0;

            while ((read_Line = infile.readLine()) != null) {
                String[] allStr = read_Line.split("\"");
                String[] this_Line;
                if (allStr.length == 1) {
                    this_Line = allStr[0].split(",");
                } else { // allStr.length == 3
                    String[] strr0 = allStr[0].split(",");
                    String[] strr2 = allStr[2].split(",");
                    this_Line = new String[strr0.length + 1 + strr2.length];
                    System.arraycopy(strr0,  0, this_Line,    0,                          strr0.length);
                    System.arraycopy(allStr, 1, this_Line,            strr0.length,        1);
                    System.arraycopy(strr2,  1, this_Line,    strr0.length + 1,    strr2.length - 1);
                }
                stmt3_1.setInt(1, Integer.parseInt(this_Line[0]));
                stmt3_1.setString(2, this_Line[1]);
                stmt3_1.setInt(3, Integer.parseInt(this_Line[2]));
                stmt3_1.setString(4, this_Line[3]);
                stmt3_1.setInt(5, Integer.parseInt(this_Line[4]));
                stmt3_1.setString(6, this_Line[6]);
                stmt3_1.setString(7, this_Line[7]);
                stmt3_1.addBatch();

                stmt3_2.setString(1, this_Line[5]);
                stmt3_2.setInt(2, Integer.parseInt(this_Line[0]));
                stmt3_2.addBatch();

                if (++cnt_BATCH % BATCH_SIZE == 0) {
                    stmt3_1.executeBatch();
                    stmt3_2.executeBatch();
                    stmt3_1.clearBatch();
                    stmt3_2.clearBatch();
                }
            }
            if (cnt_BATCH % BATCH_SIZE != 0) {
                stmt3_1.executeBatch();
                stmt3_2.executeBatch();
            }
            stmt3_1.close();
            stmt3_2.close();
            cnt_all += cnt_BATCH * 2;
            // 3. load staff.csv


            // 4. load model.csv
            infile = new BufferedReader(new FileReader("model.csv"));      infile.readLine();
            cnt_BATCH = 0;

            while ((read_Line = infile.readLine()) != null) {
                String[] this_Line = read_Line.split(",");

                stmt4_1.setInt(1, Integer.parseInt(this_Line[0]));
                stmt4_1.setString(2, this_Line[1]);
                stmt4_1.setString(3, this_Line[2]);
                stmt4_1.setString(4, this_Line[3]);
                stmt4_1.setInt(5, Integer.parseInt(this_Line[4]));
                stmt4_1.addBatch();

                stmt4_2.setInt(2, Integer.parseInt(this_Line[0]));
                for (int sc_id = 1; sc_id <= 8; sc_id++) {
                    stmt4_2.setInt(1, sc_id);
                    stmt4_2.addBatch();
                }

                if (++cnt_BATCH % BATCH_SIZE == 0) {
                    stmt4_1.executeBatch();
                    stmt4_2.executeBatch();
                    stmt4_1.clearBatch();
                    stmt4_2.clearBatch();
                }
            }
            if (cnt_BATCH % BATCH_SIZE != 0) {
                stmt4_1.executeBatch();
                stmt4_2.executeBatch();
            }
            stmt4_1.close();
            stmt4_2.close();
            cnt_all += cnt_BATCH * 9;
            // 4. load model.csv

            con.commit();
            closeDB();

            System.out.println(cnt_all + " records successfully loaded");
            System.out.println("Loading speed : " + (cnt_all * 1000L)/(System.currentTimeMillis() - start) + " records/s");

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmt1.close();
            } catch (Exception e2) {}
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmt1.close();
            } catch (Exception e2) {}
            closeDB();
            System.exit(1);
        }
    }
}