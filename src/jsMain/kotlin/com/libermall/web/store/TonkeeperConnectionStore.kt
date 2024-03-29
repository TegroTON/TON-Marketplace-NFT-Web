package com.libermall.web.store

import Account
import SendTransactionRequest
import `T$4`
import TonConnect
import com.libermall.web.model.TransactionRequestModel
import dev.fritz2.core.SimpleHandler
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import money.tegro.market.web.jsObject
import org.kodein.di.instance

class TonkeeperConnectionStore : ConnectionStore<Account>() {
    private val popOverStore: PopOverStore by di.instance()
    private val tonConnect = js("new TonConnectSDK.TonConnect();").unsafeCast<TonConnect>()

    override val address: Flow<String?> = data.map { it?.address }

    override val isAvailable: Flow<Boolean> = data.map { true }

    override val isConnected: Flow<Boolean> =
        data.map { tonConnect.connected }

    override val connect: SimpleHandler<Unit> = handle { account ->
        account.also {
            if (isEmbedded() == false) {
                popOverStore.connectTonkeeper()
            } else {
                tonConnect.connect(jsObject {
                    jsBridgeKey = "tonkeeper"
                })
            }
            console.log(it)
        }
    }

    override val disconnect: SimpleHandler<Unit> = handle { _ ->
        tonConnect.disconnect().await()
        null
    }

    override val requestTransaction: SimpleHandler<TransactionRequestModel> = handle { wallet, request ->
        tonConnect.sendTransaction(object : SendTransactionRequest {
            override var validUntil: Number = Clock.System.now().epochSeconds + 120
            override var messages: Array<`T$4`> = arrayOf(
                object : `T$4` {
                    override var address: String = request.dest
                    override var amount: String = request.value.toString()
                    override var stateInit: String? = request.stateInit
                    override var payload: String? = request.payload ?: request.text
                }
            )
        }).await()
        wallet
    }

    fun connectLink() = tonConnect.connect(jsObject {
        universalLink = "https://app.tonkeeper.com/ton-connect"
        bridgeUrl = "https://bridge.tonapi.io/bridge"
    }).unsafeCast<String?>()

    suspend fun isEmbedded(): Boolean {
        val wallets = tonConnect.getWallets().await()
        console.log("wallets: ", wallets)

        val keeper = wallets.find { it.name.unsafeCast<String>().lowercase() == "tonkeeper" }
        console.log("keeper: ", keeper)
        return keeper?.embedded ?: false
    }

    init {
        tonConnect.restoreConnection()

        tonConnect.onStatusChange({
            console.log(it)
            this.update(tonConnect.account)
            popOverStore.close()
        })
    }
}
