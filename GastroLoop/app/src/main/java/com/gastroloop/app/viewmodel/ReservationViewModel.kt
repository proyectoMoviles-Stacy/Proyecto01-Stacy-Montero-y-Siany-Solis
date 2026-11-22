
package com.gastroloop.app.viewmodel

import androidx.lifecycle.ViewModel
import com.gastroloop.app.data.memory.MemoryReservationRepository
import com.gastroloop.app.domain.model.Reservation
import com.gastroloop.app.domain.repository.ReservationRepository

class ReservationViewModel(
    private val repository: ReservationRepository =
        MemoryReservationRepository()
) : ViewModel() {

    var reservations: List<Reservation> = emptyList()
        private set

    init {
        loadReservations()
    }

    fun loadReservations() {
        reservations = repository.getAllReservations()
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
        reservations = repository.getReservationsByStatus(status)
    }
}
