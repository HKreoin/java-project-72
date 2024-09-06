@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.page(
  page = page,
  navActive = @`
          <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${NamedRoutes.rootPath()}" class="nav-link px-2 text-white">Главная</a></li>
            <li><a href="${NamedRoutes.urlsPath()}" class="nav-link px-2 text-secondary">Сайты</a></li>
          </ul>
`,
    content = @`
        <div class="container-lg mt 5">
          <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
              <thead>
                <tr>
                  <th class="col-1">ID</th>
                  <th>Имя</th>
                </tr>
              </thead>
              <tbody>
                @if(page.getUrls().isEmpty())
                  <p>Url list is empty</p>
                @else
                  @for(var url : page.getUrls())
                    <tr>
                      <td>
                        <p>${url.getId()}</p>
                      </td>
                      <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">
                          ${url.getName()}
                        </a>
                      </td>
                    </tr>
                  @endfor
                @endif
              </tbody>
            </table>
        </div>
      `
)