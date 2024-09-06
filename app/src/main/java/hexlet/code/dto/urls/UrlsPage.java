package hexlet.code.dto.urls;

import java.util.List;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlsPage extends BasePage {
    private List<Url> urls;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }

    public UrlsPage(List<Url> urls, String flashType, String flashMessage) {
        this.urls = urls;
        setFlashType(flashType);
        setFlashType(flashMessage);
    }
}
