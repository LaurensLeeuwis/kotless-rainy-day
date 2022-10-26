package example.jfall

import io.kotless.dsl.lang.http.Get
import kotlinx.html.*
import kotlinx.html.stream.createHTML

@Get("/")
fun web() {
  getForecast().forecast.fivedayforecast[0]
      .takeIf { it.rainChance >= 20 }
      ?.let {
          createHTML().html {
              body {
                  h1 { +"Take your umbrella?" }
                  img {
                      src = it.iconurl
                  }
                  p {
                      +it.weatherdescription
                  }
              }
          }
      }
}
