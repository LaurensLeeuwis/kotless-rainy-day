package example.jfall

import io.kotless.dsl.lang.http.Get

@Get("/")
fun web() = "Hello JFall!"
