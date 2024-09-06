@import gg.jte.Content
@import hexlet.code.dto.BasePage

@param Content content
@param Content navActive
@param BasePage page = null

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <title>Анализатор страниц</title>
  </head>
  <body>
    <header class="p-3 bg-dark text-white">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
            <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
          </a>

          <div class="navActive">
            ${navActive}
          </div>

        </div>
      </div>
    </header>

        <main class="flex-grow-1">
            @if(page != null && page.getFlashMessage() != null)
                <div class="rounded-0 m-0 alert alert-dismissible fade show alert-${page.getFlashType()}" role="alert">
                    <p class="m-0">${page.getFlashMessage()}</p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @endif
            <section>
                ${content}
            </section>
        </main>

    <div class="container">
      <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
        <div class="col-md-4 d-flex align-items-center">
          <a href="/" class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
            <svg class="bi" width="30" height="24"><use xlink:href="#bootstrap"></use></svg>
          </a>
          <span class="text-muted">© 2024 Company, Inc</span>
        </div>
        <ul class="nav col-md-4 justify-content-end">
          <li class="nav-item"><a href="https://github.com/HKreoin" class="nav-link px-2 text-muted">My github</a></li>
        </ul>
      </footer>
    </div>

  </body>
</html>