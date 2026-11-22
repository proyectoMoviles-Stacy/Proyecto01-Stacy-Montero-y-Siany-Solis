
package com.gastroloop.app.data.memory

import com.gastroloop.app.domain.model.Reservation
import com.gastroloop.app.domain.repository.ReservationRepository

class MemoryReservationRepository : ReservationRepository {

    private val reservations = mutableListOf<Reservation>()

    override fun getAllReservations(): List<Reservation> {
        return reservations.toList()
    }

    override fun getReservationById(id: Int): Reservation? {
        return reservations.find { it.id == id }
    }

    override fun addReservation(reservation: Reservation): Boolean {
        if (reservations.any { it.id == reservation.id }) {
            return false
        }

        reservations.add(reservation)
        return true
    }

    override fun updateReservation(reservation: Reservation): Boolean {
        val index = reservations.indexOfFirst {
            it.id == reservation.id
        }

        if (index == -1) {
            return false
        }

        reservations[index] = reservation
        return true
    }

    override fun deleteReservation(id: Int): Boolean {
        return reservations.removeAll { it.id == id }
    }

    override fun getReservationsByStatus(
        status: String
    ): List<Reservation> {
        return reservations.filter {
            it.status.equals(status, ignoreCase = true)
        }
    }
}
