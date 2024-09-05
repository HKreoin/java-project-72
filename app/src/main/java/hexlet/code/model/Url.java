package hexlet.code.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Url {
    private long id;
    private String name;
    private LocalDateTime createdAt;
}
