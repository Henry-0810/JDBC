import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyMusic {
    private  String driverClass = "oracle.jdbc.driver.OracleDriver";
    private  Connection connection = null;
    private  String url = "jdbc:oracle:thin:@studentoracle.students.ittralee.ie:1521:orcl";
    private  String username = "T00229172"; // your username
    private  String password = "fe4_Tdeegee6"; //your password
    private String insertSql = "INSERT INTO MOVIES VALUES (3, 'Michael Collins','Neil Jordan', 'Irish civil war')";
    private String selectSql = "SELECT * FROM ALBUMS";

    public MyMusic(){
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
            Statement st = getConnection().createStatement();
// Execute the statement
            st.executeUpdate(insertSql);
            System.out.println("Movie persisted successfully!");
        } catch (java.sql.SQLException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        MyMusic manager = new MyMusic();
        manager.setConnection();
        //manager.persistMovie();
        manager.queryMovies();

    }

    private void queryMovies() {
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(selectSql);
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("TITLE") +
                        ", Band:" + rs.getString("BAND"));
            }
        } catch (java.sql.SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

