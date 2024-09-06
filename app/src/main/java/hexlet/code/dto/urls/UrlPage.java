package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlPage extends BasePage {
    private Url url;

    public UrlPage(Url url) {
        this.url = url;
    }
}
