@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.page(
page = page,
navActive = @`
          <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${NamedRoutes.rootPath()}" class="nav-link px-2 text-white">Главная</a></li>
            <li><a href="${NamedRoutes.urlsPath()}" class="nav-link px-2 text-secondary">Сайты</a></li>
          </ul>
`,
content = @`
<div class="container-lg mt-5">
  <h1>Сайт: ${page.getUrl().getName()}</h1>
  <table class="table table-bordered table-hover mt-3">
    <tbody>
      <tr>
        <td>ID</td>
        <td>${page.getUrl().getId()}</td>
      </tr>
      <tr>
        <td>Имя</td>
        <td>${page.getUrl().getName()}</td>
      </tr>
      <tr>
        <td>Дата создания</td>
        <td>${page.getUrl().getCreatedAtString()}</td>
      </tr>
    </tbody>
  </table>

  <h2 class="mt-5">Проверки</h2>
  <form action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post">
    <input type="submit" class="btn btn-primary" value="Запустить проверку"/>
  </form>

  <table class="table table-bordered table-hover mt-3">
    <thead>
      <tr>
        <th scope="col">ID</th>
        <th scope="col">Код ответа</th>
        <th scope="col">title</th>
        <th scope="col">h1</th>
        <th scope="col">description</th>
        <th scope="col">Дата проверки</th>
      </tr>
    </thead>
    @if(page.getChecks() != null)
      @for(var urlCheck : page.getChecks())
      <tr>
        <td>${urlCheck.getId()}</td>
        <td>${urlCheck.getStatusCode()}</td>
        <td>${urlCheck.getTitle()}</td>
        <td>${urlCheck.getH1()}</td>
        <td>${urlCheck.getDescription()}</td>
        <td>${urlCheck.getCreatedAtString()}</td>
      </tr>
      @endfor
    @endif
  </table>
</div>
`
)