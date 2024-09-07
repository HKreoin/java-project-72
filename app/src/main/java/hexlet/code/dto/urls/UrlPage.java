package hexlet.code.dto.urls;

import java.util.List;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> checks;

    public UrlPage(Url url, List<UrlCheck> checks) {
        this.url = url;
        this.checks = checks;
    }
}
