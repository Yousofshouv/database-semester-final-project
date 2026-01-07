 import java.sql.*;

public class Department implements Searchable {
    private String deptName;
    private Connection conn;

    public Department(String name) {
        this.deptName = name;
        this.conn = DatabaseConnection.getConnection();
    }

    public boolean addStudent(String name, String id) {
        if (studentExists(id)) return false;
        
        String sql = "INSERT INTO students (id, name, department) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, deptName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean studentExists(String id) {
        String sql = "SELECT COUNT(*) FROM students WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public String getAllStudents() {
        StringBuilder sb = new StringBuilder("--- " + deptName + " Enrollment ---\n");
        String sql = "SELECT * FROM students WHERE department = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deptName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getString("id"))
                  .append(" | Name: ").append(rs.getString("name")).append("\n");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return sb.toString();
    }

    @Override
    public String searchByName(String name) {
        return performSearch("SELECT * FROM students WHERE name LIKE ? AND department = ?", name);
    }

    @Override
    public String searchById(String id) {
        return performSearch("SELECT * FROM students WHERE id = ? AND department = ?", id);
    }

    private String performSearch(String sql, String crit) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, crit);
            pstmt.setString(2, deptName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Found: " + rs.getString("name") + " (" + rs.getString("id") + ")";
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "Not found in " + deptName;
    }
}