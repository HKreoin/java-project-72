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
</div>
`
)