package com.libermall.web.modal

import com.libermall.web.component.Button
import com.libermall.web.model.ButtonKind
import com.libermall.web.model.PopOver
import com.libermall.web.store.PopOverStore
import com.libermall.web.store.TonWalletConnectionStore
import com.libermall.web.store.TonkeeperConnectionStore
import dev.fritz2.core.*
import kotlinx.coroutines.flow.map
import org.kodein.di.DI
import org.kodein.di.conf.global
import org.kodein.di.instance

fun RenderContext.ConnectModal() =
    div("top-0 left-0 z-40 w-full h-full bg-dark-900/[.6]") {
        val popOverStore: PopOverStore by DI.global.instance()
        popOverStore.data.map { if (it == PopOver.CONNECT) "fixed" else "hidden" }.let(::className)

        div("mx-auto flex items-center relative w-auto max-w-lg min-h-screen") {
            div("bg-dark-700 rounded-3xl p-10 relative flex flex-col w-full h-full min-h-full gap-4") {
                div {
                    h5("text-2xl font-raleway font-bold mb-2") {
                        +"Connect Wallet"
                    }

                    p("text-gray-500 text-lg") {
                        +"Choose how you want to connect. More options will be added in the future."
                    }

                    button("absolute top-6 right-8 opacity-50") {
                        type("button")
                        clicks handledBy popOverStore.close

                        i("fa-solid fa-xmark text-2xl") {}
                    }
                }

                div("flex flex-col gap-4") {
                    val tonwalletConnectionStore: TonWalletConnectionStore by DI.global.instance()

                    Button(ButtonKind.SOFT, "flex items-center gap-4") {
                        disabled(tonwalletConnectionStore.isAvailable.map { !it })
                        clicks handledBy tonwalletConnectionStore.connect

                        img("w-10 h-10") {
                            alt("Ton Wallet")
                            src("./assets/img/ton-wallet.png")
                        }

                        span("text-lg flex-grow") {
                            +"Ton Wallet"
                        }

                        i {
                            className(tonwalletConnectionStore.isAvailable.map { if (it) "fa-solid fa-angle-right" else "fa-solid fa-xmark" })
                        }
                    }

                    val tonkeeperConnectionStore: TonkeeperConnectionStore by DI.global.instance()
                    Button(ButtonKind.SOFT, "flex items-center gap-4") {
                        disabled(tonkeeperConnectionStore.isAvailable.map { !it })
                        clicks handledBy tonkeeperConnectionStore.connect

                        img("w-10 h-10") {
                            alt("Tonkeeper")
                            src("./assets/img/tonkeeper.svg")
                        }

                        span("text-lg flex-grow") {
                            +"Tonkeeper"
                        }

                        i {
                            className(tonkeeperConnectionStore.isAvailable.map { if (it) "fa-solid fa-angle-right" else "fa-solid fa-xmark" })
                        }
                    }
                }
            }
        }
    }
