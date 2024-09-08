package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            var createdAt = LocalDateTime.now();
            stmt.setTimestamp(2, Timestamp.valueOf(createdAt));

            stmt.executeUpdate();
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
                url.setCreatedAt(createdAt);
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }

    public static boolean existByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }


    public static Optional<Url> findByName(String urlName) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, urlName);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDateTime dateandtime = resultSet.getObject("created_at", LocalDateTime.class);
                long id = resultSet.getLong("id");
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(dateandtime);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }


    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var list = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                list.add(url);
            }
            return list;
        }
    }

}
