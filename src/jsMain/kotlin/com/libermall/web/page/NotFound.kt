package com.libermall.web.page

import dev.fritz2.core.RenderContext
import com.libermall.web.component.Button
import com.libermall.web.component.Link
import com.libermall.web.model.ButtonKind

fun RenderContext.NotFound() =
    main("mx-3 lg:mx-6") {
        section("px-0 py-12 flex flex-col items-center text-center justify-center gap-8") {
            i("fa-duotone fa-triangle-exclamation text-yellow text-8xl") {}
            h1("text-5xl font-bold font-raleway") {
                +"Oops! Page Not Found"
            }
            p("text-lg") {
                +"Sorry, but the page you are looking for is not found. Please, make sure the URL is correct"
            }

            Link(setOf("")) {
                Button(ButtonKind.PRIMARY) {
                    +"Home Page"
                }
            }
        }
    }
