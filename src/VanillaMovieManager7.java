import java.sql.*;

public class VanillaMovieManager7 {
    private  String driverClass = "oracle.jdbc.driver.OracleDriver";
    private  Connection connection = null;
    private  String url = "jdbc:oracle:thin:@studentoracle.students.ittralee.ie:1521:orcl";
    private  String username = "T00229172"; // your username
    private  String password = "fe4_Tdeegee6"; //your password
    private String insertSql = "INSERT INTO MOVIES VALUES (3, 'Michael Collins','Neil Jordan', 'Irish civil war')";
    private String selectSql = "SELECT * FROM MOVIES";

    private String insertSql2 = "insert into movies " + "(id, title, director, synopsis) " + "values " + "(?, ?, ?, ?)";

    public VanillaMovieManager7(){
    }

    private void setConnection() {
        try {
            Class.forName(driverClass).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
        } catch (Exception ex) {
            System.err.println("Exception:"+ ex.getMessage());
            ex.printStackTrace();
        }

    }
    private Connection getConnection() {
        if (connection == null) {
            setConnection();
        }
        return connection;
    }

    private void persistMovie() {
        try {
            PreparedStatement pst1=getConnection().prepareStatement(insertSql2);
            PreparedStatement pst2=getConnection().prepareStatement(insertSql2);
            pst2.setInt(1,5);
            pst2.setString(2, "Marvels: Infinity War");
            pst2.setString(3, "Anthony Russo");
            pst2.setString(4, "Thanos equalizing the world");
            pst1.setInt(1, 6);
            pst1.setString(2, "Life of Pi");
            pst1.setString(3, "Ang Lee");
            pst1.setString(4, "Life on a safety boat");
// Execute the statement
            pst1.execute();
            pst2.execute();
            System.out.println("Movie persisted successfully!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void main(String[] args){
        VanillaMovieManager7 manager = new VanillaMovieManager7();
        manager.setConnection();
        //manager.persistMovie();
        //manager.getNumberRows2();
        //manager.printMetaData();
        manager.useSavepoint();

    }

    private void queryMovies() {
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(selectSql);
            while (rs.next()) {
                System.out.println("Director: " + rs.getString("DIRECTOR") +
                        ", Title:" + rs.getString("TITLE"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void getNumberRows() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectSql);

            // YOUR CODE UNDER HERE
            int rows = 0;
            while(rs.next()){
                rows++;
            }

            System.out.println("Row Count: " + rows);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void getNumberRows2() {
        int count=0;
        try {
            Statement st = connection.createStatement();
// YOUR CODE UNDER HERE – hint use executeUpdate and count *
            count = st.executeUpdate("SELECT COUNT(*) FROM MOVIES");

                    System.out.println("Row Count: " + count);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public  void printMetaData() {
        try {
            Statement stmt = getConnection().createStatement();

            ResultSet rs = stmt.executeQuery(selectSql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            System.out.println("Column Count:" + columnCount);

            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Index:" + i +
                        ", Name:" + rsmd.getColumnName(i) +
                        ", Label:" + rsmd.getColumnLabel(i) +
                        ", Type Name:" + rsmd.getColumnTypeName(i) +
                        ", Class Name:" + rsmd.getColumnClassName(i));
            }
            rs.close();
        }
        catch(SQLException e){
            System.out.println("error");
        }
    }

    public  void useSavepoint(){
        String SQL = "update movies " +"set synopsis = ? "+"where id=?";
        PreparedStatement pstmt = null;
        try{
            getConnection().setAutoCommit(false);

            pstmt = getConnection().prepareStatement(SQL);

            pstmt.setString(1, "a good movie"); // value for synopsis

            pstmt.setInt(2, 1); // value for movie id 1

            pstmt.execute();



            // Set a save point

            Savepoint sp1 = getConnection().setSavepoint();

            //Change synopsis to ‘a terrible movie’and execute SQL again

            pstmt.setString(1, "an bad movie");

            pstmt.execute();

            // Set a save point
            Savepoint sp2 = getConnection().setSavepoint();

            // Roll back the transaction to the save point sp1,
            // so that the synopsis for movie id 1 will remain set
            // to ‘a bad movie’ and not ‘a terrible movie’

            getConnection().rollback(sp1);

            // Commit the transaction
            getConnection().commit();
            pstmt.close();
            getConnection().close();
        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());

        }
    }



}

