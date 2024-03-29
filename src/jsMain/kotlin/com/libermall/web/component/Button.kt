package com.libermall.web.component

import dev.fritz2.core.HtmlTag
import dev.fritz2.core.RenderContext
import dev.fritz2.core.type
import com.libermall.web.model.ButtonKind
import org.w3c.dom.HTMLButtonElement

fun RenderContext.Button(
    kind: ButtonKind,
    classes: String? = null,
    content: HtmlTag<HTMLButtonElement>.() -> Unit
) =
    button("px-6 py-3 flex flex-nowrap items-center justify-center border rounded-lg text-sm uppercase font-medium tracking-widest") {
        when (kind) {
            ButtonKind.PRIMARY ->
                className("text-dark-900 bg-yellow hover:bg-yellow-hover focus:bg-yellow-hover border-yellow hover:border-yellow-hover focus:border-yellow-hover")

            ButtonKind.SECONDARY ->
                className("text-white bg-transparent hover:bg-yellow focus:bg-yellow border-yellow focus:border-yellow")

            ButtonKind.SOFT ->
                className("text-white bg-dark-700 hover:bg-gray-900 focus:bg-gray-900 border-dark-700 hover:border-gray-900 focus:border-gray-900")
        }

        classes?.let { className(it) }
        type("button")

        this.apply(content)
    }
