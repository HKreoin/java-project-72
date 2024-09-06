package hexlet.code.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;

public class RootController {
    public static void index(Context ctx) {
        String flashType = ctx.consumeSessionAttribute("flashType");
        String flashMessage = ctx.consumeSessionAttribute("flashMessage");
        var page =  flashMessage == null ? new BasePage() : new BasePage(flashType, flashMessage);
        ctx.render("index.jte", model("page", page));
    }
}
