package com.libermall.web.page

import dev.fritz2.core.RenderContext
import dev.fritz2.core.RootStore
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import kotlinx.coroutines.flow.filterNotNull
import com.libermall.web.resource.CollectionResource
import com.libermall.web.card.CollectionCard
import com.libermall.web.model.CollectionModel
import org.kodein.di.DI
import org.kodein.di.conf.global
import org.kodein.di.instance

fun RenderContext.Explore() {
    main("mx-3 lg:mx-6") {
        section("px-0 py-12") {
            div("container mx-auto") {
                h1("font-raleway text-4xl") {
                    +"Explore collections"
                }
            }

            div("grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4") {
                val collectionsStore = object : RootStore<List<CollectionModel>?>(null) {
                    private val httpClient: HttpClient by DI.global.instance()
                    val load = handle { _ ->
                        httpClient.get(CollectionResource.All(sort = CollectionResource.All.Sort.ALL))
                            .body<List<CollectionModel>>()
                    }

                    init {
                        load()
                    }
                }

                collectionsStore.data
                    .filterNotNull()
                    .renderEach(into = this) { CollectionCard(it) }
            }
        }
    }
}
