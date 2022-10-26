package example.jfall

import io.kotless.dsl.lang.http.Get
import kotlinx.html.*
import kotlinx.html.stream.createHTML

@Get("/")
fun triggerMail() : String =
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
      ?.let {
          Emailer.sendEmail("Take your umbrella?", it,
              "dummy@example.com", "Apps",
              "dummy@example.com")
      }
      ?.let {
          if(it) {"email sent"} else {"sending email failed"}
      }
      ?: "No chance of rain"
