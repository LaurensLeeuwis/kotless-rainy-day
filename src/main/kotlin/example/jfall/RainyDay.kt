package example.jfall

import io.kotless.dsl.lang.event.Scheduled
import io.kotless.dsl.lang.http.Get
import kotlinx.html.*
import kotlinx.html.stream.createHTML

@Get("/")
// https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/ScheduledEvents.html#CronExpressions
// The ? (question mark) wildcard specifies one or another. In the Day-of-month
// field you could enter 7 and if you didn't care what day of the week the 7th was,
// you could enter ? in the Day-of-week field.
@Scheduled(cron = "55 21 * * ? *")
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
