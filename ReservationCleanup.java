import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.*;

public class ReservationCleanup {
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private static final long CLEANUP_INTERVAL = 2 * 60 * 1000; //every 2 minutes

  public static void main(String[] args) {
    // Schedule a task to run periodically
    scheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", " ")) {
          @SuppressWarnings("unused")
		long currentTime = System.currentTimeMillis();

          PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reserve WHERE timestampdiff(day, date_created, now()) > 3");

          // Execute the query and process the results
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            int itemId = rs.getInt("itemID");
            int userId = rs.getInt("userID");

            // Delete the reservation from the reserve table
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM reserve WHERE itemID = ? AND userID = ?");
            deleteStmt.setInt(1, itemId);
            deleteStmt.setInt(2, userId);
            deleteStmt.executeUpdate();

            // Update the item table to mark the item as not reserved and available
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE item SET reserved = 'No', available = 'Yes' WHERE itemID = ?");
            updateStmt.setInt(1, itemId);
            updateStmt.executeUpdate();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }, 0, CLEANUP_INTERVAL, TimeUnit.MILLISECONDS);
  }
}
