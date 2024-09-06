package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        String flashType = ctx.consumeSessionAttribute("flashType");
        String flashMessage = ctx.consumeSessionAttribute("flashMessage");
        var page =  flashMessage == null ? new UrlsPage(urls) : new UrlsPage(urls, flashType, flashMessage);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {

        try {
            var uri = URI.create(ctx.formParam("url"));
            var url = uri.toURL();
            var urlStr = url.getProtocol() + "://" + url.getHost();
            if (!UrlRepository.existByName(urlStr)) {
                UrlRepository.save(new Url(urlStr));
                setSessionAttribute(ctx, "Success", "Страница успешно добавлена!");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                setSessionAttribute(ctx, "Error", "Страница уже существует");
                ctx.redirect(NamedRoutes.rootPath());
            }
        } catch (MalformedURLException | IllegalArgumentException e) {
            setSessionAttribute(ctx, "Error", "Некорректный URL!");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            Url url = UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("Страница с таким ID не существует"));
            var page = new UrlPage(url);
            ctx.render("urls/show.jte", model("page", page));
        } catch (SQLException e) {
            setSessionAttribute(ctx, "Error", "Ошибка БД");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    private static void setSessionAttribute(Context ctx, String flashType, String flashMessage) {
        ctx.sessionAttribute("flashType", flashType);
        ctx.sessionAttribute("flashMessage", flashMessage);
    }
}
