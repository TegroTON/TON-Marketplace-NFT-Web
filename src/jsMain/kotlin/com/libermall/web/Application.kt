package money.tegro.market.web

import com.ionspin.kotlin.bignum.serialization.kotlinx.humanReadableSerializerModule
import com.libermall.web.fragment.Footer
import com.libermall.web.fragment.Header
import com.libermall.web.modal.ConnectModal
import com.libermall.web.modal.ConnectTonkeeperModal
import com.libermall.web.page.*
import com.libermall.web.route.AppRouter
import com.libermall.web.store.GlobalConnectionStore
import com.libermall.web.store.PopOverStore
import com.libermall.web.store.TonWalletConnectionStore
import com.libermall.web.store.TonkeeperConnectionStore
import dev.fritz2.core.render
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.conf.global
import org.kodein.di.instance

fun main() {
    kotlinext.js.require("tailwindcss/tailwind.css")

    DI.global.addConfig {
        bindSingleton {
            Json {
                serializersModule = humanReadableSerializerModule
            }
        }

        bindSingleton {
            HttpClient {
                install(ContentNegotiation) {
                    json(instance())
                }
                install(Resources)

                defaultRequest {
                    url("https://api.libermall.com/v1/")
                }
            }
        }

        bindSingleton { AppRouter() }
        bindSingleton { GlobalConnectionStore() }
        bindSingleton { TonWalletConnectionStore() }
        bindSingleton { TonkeeperConnectionStore() }
        bindSingleton { PopOverStore() }
    }

    render {
        Header()

        val appRouter: AppRouter by DI.global.instance()
        appRouter.data.render { route ->
            when (route.first()) {
                "" -> Index()
                "collection" -> Collection(route.elementAt(1))
                "item" -> Item(route.elementAt(1))
                "profile" -> Profile(route.elementAt(1))
                "explore" -> Explore()
                else -> NotFound()
            }
        }

        Footer()

        ConnectModal()
        ConnectTonkeeperModal()
    }
}
