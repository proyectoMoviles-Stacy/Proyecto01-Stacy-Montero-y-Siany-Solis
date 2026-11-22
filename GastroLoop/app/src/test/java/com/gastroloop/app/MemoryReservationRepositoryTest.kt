
package com.gastroloop.app

import com.gastroloop.app.data.memory.MemoryReservationRepository
import com.gastroloop.app.domain.model.Reservation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MemoryReservationRepositoryTest {

    private lateinit var repository: MemoryReservationRepository

    @Before
    fun setup() {
        repository = MemoryReservationRepository()
    }

    private fun sampleReservation() = Reservation(
        id = 1,
        foodOfferId = 1,
        quantity = 2,
        totalPrice = 4000.0,
        pickupTime = "12:30"
    )

    @Test
    fun addReservationSuccessfully() {
        val result = repository.addReservation(sampleReservation())

        assertTrue(result)
        assertEquals(1, repository.getAllReservations().size)
    }

    @Test
    fun findReservationById() {
        repository.addReservation(sampleReservation())

        val reservation = repository.getReservationById(1)

        assertNotNull(reservation)
        assertEquals(2, reservation?.quantity)
    }

    @Test
    fun updateReservationSuccessfully() {
        repository.addReservation(sampleReservation())

        val updated = sampleReservation().copy(
            quantity = 3,
            totalPrice = 6000.0
        )

        assertTrue(repository.updateReservation(updated))
        assertEquals(3, repository.getReservationById(1)?.quantity)
    }

    @Test
    fun deleteReservationSuccessfully() {
        repository.addReservation(sampleReservation())

        assertTrue(repository.deleteReservation(1))
        assertTrue(repository.getAllReservations().isEmpty())
    }

    @Test
    fun filterReservationsByStatus() {
        repository.addReservation(sampleReservation())

        val results = repository.getReservationsByStatus("PENDING")

        assertEquals(1, results.size)
    }
}
