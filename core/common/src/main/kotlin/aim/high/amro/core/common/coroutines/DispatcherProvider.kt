package aim.high.amro.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val databaseRead: CoroutineDispatcher
    val databaseWrite: CoroutineDispatcher
}

class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val databaseRead: CoroutineDispatcher = Dispatchers.IO
    override val databaseWrite: CoroutineDispatcher = Dispatchers.IO
}
