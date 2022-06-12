package com.Endless.database;

import com.Endless.ELCore;
import com.Endless.user.UserManager;
import com.Endless.user.UserRank;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class DatabaseManager {

    private final ConnectionPoolManager pool;

    ELCore plugin = (ELCore)ELCore.getPlugin(ELCore.class);

    public DatabaseManager(ELCore Plugin){
        this.plugin = plugin;
        this.pool = new ConnectionPoolManager((org.bukkit.plugin.Plugin)plugin);
    }

    public void onDisable() {
        this.pool.closePool();
    }
   /* ######################BASIC METHODS###################### */
   public boolean exists(String player) throws SQLException {
       Connection conn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
       try {
           conn = this.pool.getConnection();
           ps = conn.prepareStatement("SELECT 1 FROM `player_data` WHERE UUID='" + getUUID(player) + "'");
           rs = ps.executeQuery();
           if (rs.next())
               return true;
           return false;
       } finally {
           this.pool.close(conn, ps, rs);
       }
   }

    public void createPlayer(UUID uuid, Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO `player_data` (UUID,NAME,COINS,RANK,MESSAGING,LastLogin,Vanished) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, player.getName());
            ps.setString(3, "0");
            ps.setString(4, "MEMBER");
            ps.setString(5, "true");
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();
            ps.setNString(6, dateFormat.format(date));
            ps.setInt(7, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public String getUsername(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT NAME FROM `player_data` WHERE UUID = ? LIMIT 1");
            ps.setString(1, getUUID(player));
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("NAME");
        } catch (SQLException e) {
            return null;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return null;
    }

    public String getUUID(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT UUID FROM `player_data` WHERE NAME=?");
            ps.setString(1, player);
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("UUID");
        } catch (SQLException e) {
            return null;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return null;
    }


    public UserRank getRank(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT RANK FROM `player_data` WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                UserRank rank = UserRank.valueOf(rs.getString("RANK"));
                if (rank != null)
                    return rank;
                return UserRank.MEMBER;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return UserRank.MEMBER;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return UserRank.MEMBER;
    }

    public UserRank getRank(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT RANK FROM `player_data` WHERE UUID=?");
            ps.setString(1, getUUID(player));
            rs = ps.executeQuery();
            if (rs.next()) {
                UserRank rank = UserRank.valueOf(rs.getString("RANK"));
                if (rank != null)
                    return rank;
                return UserRank.MEMBER;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return UserRank.MEMBER;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return UserRank.MEMBER;
    }

    public String getLastLogin(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT LastLogin FROM `player_data` WHERE UUID = ? LIMIT 1");
            ps.setString(1, getUUID(player));
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("LastLogin");
        } catch (SQLException e) {
            return null;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return null;
    }

    public int getRowCount(String table) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM `player_data`");
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return 0;
    }

    public String getDBTotalSize(String table) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT \r\n    table_name AS `player_data`, \r\n    round(((data_length + index_length) / 1024 / 1024), 2) `Size in MB` \r\nFROM information_schema.TABLES \r\nWHERE table_schema = \"$DB_NAME\"\r\n    AND table_name = \"$TABLE_NAME\";");
            rs = ps.executeQuery();
            int columnsNumber = rs.getMetaData().getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rs.getMetaData().getColumnName(i));
                }
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return null;
    }

    public void updateCoins(String string, String Coins) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player_data` SET COINS=? WHERE UUID=?;");
            ps.setString(1, Coins);
            ps.setString(2, string.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public void updateLastLogin(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String statement = "UPDATE `player_data` SET LastLogin=? WHERE UUID=?;";
            conn = this.pool.getConnection();
            ps = conn.prepareStatement(statement);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public void updateUsername(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String statement = "UPDATE `player_data` SET NAME=? WHERE UUID = ?;";
            conn = this.pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public void addFilter(String word) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO wordfilter (word) VALUES (?)");
            ps.setString(1, word);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public void deleteFilter(String word) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("DELETE FROM `wordfilter` WHERE word=?");
            ps.setString(1, word);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM `wordfilter`");
            rs = ps.executeQuery();
            while (rs.next()) {
              words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return words;
    }


    public void setRank(String player, UserRank rank) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player_data` SET RANK=? WHERE UUID=?;");
            ps.setString(1, rank.toString());
            ps.setString(2, getUUID(player));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

    public int getVanished(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("SELECT Vanished FROM `player_data` WHERE UUID = ? LIMIT 1");
            ps.setString(1, getUUID(player));
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("Vanished");
        } catch (SQLException e) {
            return 0;
        } finally {
            this.pool.close(conn, ps, rs);
        }
        return 0;
    }

    public void setVanish(String player, int v) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = this.pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player_data` SET VANISHED=? WHERE UUID=?;");
            ps.setInt(1, v);
            ps.setString(2, getUUID(player));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.pool.close(conn, ps, null);
        }
    }

}
