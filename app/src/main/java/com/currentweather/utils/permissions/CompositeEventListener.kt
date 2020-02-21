package com.currentweather.utils.permissions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Subscription interface
 * */
interface CompositeEventListener<TEvent> {
    /**
     * Subscribes listener
     * @param listener - listener to subscribe
     * */
    fun subscribe(listener: (TEvent) -> Unit): (TEvent) -> Unit

    /**
     * Unsubscribes listener
     * @param listener - listener to unsubscribe
     * */
    fun unSubscribe(listener: (TEvent) -> Unit): (TEvent) -> Unit

    /**
     * Unsubscribes all listeners
     * */
    fun unSubscribeAll()
}

/**
 * Extension function for [CompositeEventListener] witch return the first event which satisfies predicate
 * @param predicate - condition to select item
 * @return TEvent
 **/
@ExperimentalCoroutinesApi
suspend fun <TEvent> CompositeEventListener<TEvent>.awaitFirst(predicate: (TEvent) -> Boolean = { true }): TEvent {
    return suspendCancellableCoroutine { continuation ->
        lateinit var listener: (TEvent) -> Unit
        listener = { event: TEvent ->
            if (predicate(event)) {
                continuation.resume(event) {}
                unSubscribe(listener)
            }
        }
        subscribe(listener)
    }
}

/**
 * [CompositeEventListener] which allows to emit events
 * */
interface EmitableCompositeEventListener<TEvent> : CompositeEventListener<TEvent> {
    /**
     * Emits new event. Sends it to listeners
     * @param event - event to emit
     * */
    fun emit(event: TEvent)
}

/**
 * Simple realisation of Observer patter
 * */
class SimpleCompositeEventListener<TEvent> : CompositeEventListener<TEvent>, EmitableCompositeEventListener<TEvent> {
    /**
     * Thread-safe list of listeners
     * */
    private val listeners = CopyOnWriteArrayList<(TEvent) -> Unit>()

    /**
     * Notifies listeners about new event
     * @param event - event to notify about
     * */
    override fun emit(event: TEvent) {
        listeners.forEach { it(event) }
    }

    /**
     * Adds listener to list
     * @param listener - new listener
     * */
    override fun subscribe(listener: (TEvent) -> Unit): (TEvent) -> Unit {
        listeners.add(listener)
        return listener
    }

    /**
     * Removes listener from list
     * @param listener - listener to remove
     * */
    override fun unSubscribe(listener: (TEvent) -> Unit): (TEvent) -> Unit {
        listeners.remove(listener)
        return listener
    }

    /**
     * Removes all listeners from list
     * */
    override fun unSubscribeAll() {
        listeners.clear()
    }
}