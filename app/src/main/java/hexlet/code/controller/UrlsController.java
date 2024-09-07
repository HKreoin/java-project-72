package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        String flashType = ctx.consumeSessionAttribute("flashType");
        String flashMessage = ctx.consumeSessionAttribute("flashMessage");
        var urlChecks = UrlCheckRepository.findLastUrlChecks();
        var page = new UrlsPage(urls, urlChecks,  flashType, flashMessage);
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
            var urlChecks = UrlCheckRepository.findUrlChecks(id);
            var page = new UrlPage(url, urlChecks);
            ctx.render("urls/show.jte", model("page", page));
        } catch (SQLException e) {
            setSessionAttribute(ctx, "Error", "Ошибка БД");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void checkUrl(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(urlId);
        var urlString = url.get().getName();
        int statusCode = 404;
        try {
            HttpResponse<String> response = Unirest.get(urlString).asString();
            statusCode = response.getStatus();
            Document document = Jsoup.parse(response.getBody());
            String title = document.title();
            var h1Temp = document.selectFirst("h1");
            var h1 = h1Temp == null ? "" : h1Temp.text();
            var metaDescription = document.selectFirst("meta[name=description]");
            var description = metaDescription == null ? "" : metaDescription.attr("content");
            var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
            setSessionAttribute(ctx, "Succes", "Страница успешно проверена");
            UrlCheckRepository.save(urlCheck);
        } catch (Exception e) {
            var urlCheck = new UrlCheck(statusCode, "Invalid URL", "", e.getMessage(), urlId);
            UrlCheckRepository.save(urlCheck);
            setSessionAttribute(ctx, "Error", "Некорректный URL");
        } finally {
            Unirest.shutDown();
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }

    private static void setSessionAttribute(Context ctx, String flashType, String flashMessage) {
        ctx.sessionAttribute("flashType", flashType);
        ctx.sessionAttribute("flashMessage", flashMessage);
    }
}
