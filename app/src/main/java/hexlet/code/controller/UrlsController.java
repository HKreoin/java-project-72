package hexlet.code.controller;

import io.javalin.http.Context;

public class UrlsController {

    public static void index(Context ctx) {
        ctx.render("index.jte");
    }

}
