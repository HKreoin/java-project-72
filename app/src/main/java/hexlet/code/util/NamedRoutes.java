package hexlet.code.util;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlCheck(long id) {
        return urlCheck(String.valueOf(id));
    }

    public static String urlCheck(String id) {
        return "/urls/" + id + "/checks";
    }
}
