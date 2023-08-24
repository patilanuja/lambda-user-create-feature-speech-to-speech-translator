package lol.lingua.usercreate.repository;

import lol.lingua.usercreate.entity.User;
import lol.lingua.usercreate.helper.LogHelper;
import software.amazon.awssdk.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RDSRepository {

    public User createUser(String uid, String name, String language) throws Exception {
        Optional<User> user = getUser(uid);
        if (user.isPresent() && StringUtils.isNotBlank(user.get().getName()) && StringUtils.isNotBlank(user.get().getLanguage()))
            return user.get();
        try (PreparedStatement ps = getConnection()
                .prepareStatement("insert into lingualol.user (uid, name, language) values (?, ?, ?) on conflict(uid) do update set name = EXCLUDED.name, language = EXCLUDED.language",
                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, uid);
            ps.setString(2, name);
            ps.setString(3, language);
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    User u = new User(generatedKeys.getLong(1), uid, name, language);
                    LogHelper.getLogger().log(u + " was created");
                    return u;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private Optional<User> getUser(String uid) throws Exception {
        try (PreparedStatement ps = getConnection()
                .prepareStatement("select id, uid, name, language from lingualol.user where uid = ?")) {
            ps.setString(1, uid);
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next())
                users.add(
                        new User(rs.getLong("id"), rs.getString("uid"),
                                rs.getString("name"), rs.getString("language"))
                );
            if (users.size() > 1)
                throw new Exception("More than 1 user with uid: " + uid);
            Optional<User> user = users.stream().findFirst();
            if (user.isPresent())
                LogHelper.getLogger().log(user.get() + " was found");
            else
                LogHelper.getLogger().log("User wasn't found for uid " + uid);
            return user;
        } catch (SQLException e) {
            throw e;
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://cpsc6720.c4rhdanwzmao.us-east-1.rds.amazonaws.com:5432/cpsc6720?currentSchema=lingualol";
        String username = "lingualol";
        String password = "qwertyuiop";

        return DriverManager.getConnection(url, username, password);
    }

}
