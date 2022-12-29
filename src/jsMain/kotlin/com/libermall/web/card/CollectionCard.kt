package com.libermall.web.card

import com.libermall.web.model.CollectionModel
import dev.fritz2.core.RenderContext
import dev.fritz2.core.alt
import dev.fritz2.core.src
import com.libermall.web.component.Link

fun RenderContext.CollectionCard(collection: CollectionModel) =
    Link(setOf("collection", collection.address), "relative p-4 bg-dark-700 rounded-lg flex flex-col gap-4") {
        picture {
            img("w-full h-52 rounded-lg object-cover") {
                src(collection.coverImage.original ?: "assets/img/user-1.svg")
                alt(collection.name)
            }
        }

        picture {
            img("-mt-14 ml-4 rounded-full w-20 h-20") {
                alt(collection.name)
                src(collection.image.original ?: "./assets/img/user-1.svg")
            }
        }

        h4("font-raleway text-lg") {
            +collection.name
        }

        span("w-full text-gray-500") {
            +"${collection.numberOfItems} items"
        }
    }
