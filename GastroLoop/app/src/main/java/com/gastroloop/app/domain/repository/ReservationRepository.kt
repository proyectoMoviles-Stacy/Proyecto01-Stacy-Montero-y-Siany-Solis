
package com.gastroloop.app.domain.repository

import com.gastroloop.app.domain.model.Reservation

interface ReservationRepository {

    fun getAllReservations(): List<Reservation>

    fun getReservationById(id: Int): Reservation?

    fun addReservation(reservation: Reservation): Boolean

    fun updateReservation(reservation: Reservation): Boolean

    fun deleteReservation(id: Int): Boolean

    fun getReservationsByStatus(status: String): List<Reservation>
}
