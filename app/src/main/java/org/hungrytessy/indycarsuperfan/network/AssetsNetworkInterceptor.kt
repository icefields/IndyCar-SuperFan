package org.hungrytessy.indycarsuperfan.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

import org.hungrytessy.indycarsuperfan.extensions.readStringAsset
import java.lang.ref.WeakReference

class AssetsNetworkInterceptor(context: Context): Interceptor {
    private var weakContext: WeakReference<Context> = WeakReference(context)

    override fun intercept(chain: Interceptor.Chain): Response = makeOkResult(chain.request())

    /**
     * Generate an error result.
     *
     * ```
     * HTTP/1.1 500 Bad server day
     * Content-type: application/json
     *
     * {"cause": "not sure"}
     * ```
     */
    private fun makeErrorResult(request: Request): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Bad server day")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                Gson().toJson(mapOf("cause" to "not sure"))))
            .build()
    }

    /**
     * Generate a success response.
     * ```
     * HTTP/1.1 200 OK
     * Content-type: application/json
     * ```
     */
    private fun makeOkResult(request: Request): Response {
        val bodyStr = weakContext.get()?.let { ctx ->
            when(IndyPath.getPath(request.url.pathSegments[0])) {
                IndyPath.DRIVERS -> ctx.readStringAsset(IndyPath.DRIVERS.value)
                IndyPath.SEASONS -> ctx.readStringAsset(IndyPath.SEASONS.value)
                IndyPath.VENUES -> ctx.readStringAsset(IndyPath.VENUES.value)
            }
        } ?: run { "" }

        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(ResponseBody.create("application/json".toMediaType(), bodyStr))
            .build()
    }
}