package aim.high.amro.core.common.error

import aim.high.amro.core.common.R
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.asUiErrorMessage(): Int {
    return when (this) {
        is UnknownHostException, is ConnectException, is IOException -> R.string.error_no_internet
        else -> R.string.error_unknown_generic
    }
}

fun Throwable.isConnectionIssue(): Boolean {
    return this is UnknownHostException || this is ConnectException || this is IOException
}
