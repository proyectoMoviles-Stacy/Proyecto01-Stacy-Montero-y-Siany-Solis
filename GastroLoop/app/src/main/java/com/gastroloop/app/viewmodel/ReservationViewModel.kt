
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.data.memory.MemoryReservationRepository
import com.gastroloop.app.domain.model.Reservation
import com.gastroloop.app.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReservationViewModel(
    private val repository: ReservationRepository =
        MemoryReservationRepository()
) : ViewModel() {

    private val _reservations =
        MutableStateFlow<List<Reservation>>(emptyList())

    val reservations: StateFlow<List<Reservation>> = _reservations

    init {
        loadReservations()
    }

    fun loadReservations() {
        _reservations.value = repository.getAllReservations()
    }

    fun addReservation(reservation: Reservation): Boolean {
        val added = repository.addReservation(reservation)

        if (added) {
            loadReservations()
        }

        return added
    }

    fun updateReservation(reservation: Reservation): Boolean {
        val updated = repository.updateReservation(reservation)

        if (updated) {
            loadReservations()
        }

        return updated
    }

    fun deleteReservation(id: Int): Boolean {
        val deleted = repository.deleteReservation(id)

        if (deleted) {
            loadReservations()
        }

        return deleted
    }

    fun filterByStatus(status: String) {
        _reservations.value =
            repository.getReservationsByStatus(status)
    }
}
