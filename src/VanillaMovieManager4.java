import java.sql.*;

public class VanillaMovieManager4 {
    private  String driverClass = "oracle.jdbc.driver.OracleDriver";
    private  Connection connection = null;
    private  String url = "jdbc:oracle:thin:@studentoracle.students.ittralee.ie:1521:orcl";
    private  String username = "T00229172"; // your username
    private  String password = "fe4_Tdeegee6"; //your password
    private String insertSql = "INSERT INTO MOVIES VALUES (3, 'Michael Collins','Neil Jordan', 'Irish civil war')";
    private String selectSql = "SELECT * FROM MOVIES";

    private String insertSql2 = "insert into movies " + "(id, title, director, synopsis) " + "values " + "(?, ?, ?, ?)";

    public VanillaMovieManager4(){
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
        } catch (java.sql.SQLException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void main(String[] args){
        VanillaMovieManager4 manager = new VanillaMovieManager4();
        manager.setConnection();
        manager.persistMovie();
        manager.queryMovies();

    }

    private void queryMovies() {
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(selectSql);
            while (rs.next()) {
                System.out.println("Director: " + rs.getString("DIRECTOR") +
                        ", Title:" + rs.getString("TITLE"));
            }
        } catch (java.sql.SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

