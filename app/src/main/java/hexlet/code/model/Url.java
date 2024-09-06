package hexlet.code.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Url {
    private long id;
    private String name;
    private LocalDateTime createdAt;

    public Url(String name) {
        this.name = name;
    }

    public final String getCreatedAtString() {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm-ss");
        return createdAt.format(formatter);
    }
}
