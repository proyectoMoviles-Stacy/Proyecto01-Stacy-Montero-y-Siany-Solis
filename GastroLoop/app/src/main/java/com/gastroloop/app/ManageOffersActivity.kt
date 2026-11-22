package com.gastroloop.app

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gastroloop.app.data.memory.MemoryDataRepository
import com.gastroloop.app.domain.model.FoodOffer

class ManageOffersActivity : Activity() {

    private val repository = MemoryDataRepository()

    private lateinit var offerNameInput: EditText
    private lateinit var offerDescriptionInput: EditText
    private lateinit var originalPriceInput: EditText
    private lateinit var discountPercentageInput: EditText
    private lateinit var quantityInput: EditText
    private lateinit var pickupStartInput: EditText
    private lateinit var pickupEndInput: EditText
    private lateinit var offersContainer: LinearLayout
    private lateinit var addOfferButton: Button

    private var selectedOfferId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_offers)

        offerNameInput = findViewById(R.id.offerNameInput)
        offerDescriptionInput = findViewById(R.id.offerDescriptionInput)
        originalPriceInput = findViewById(R.id.originalPriceInput)
        discountPercentageInput = findViewById(R.id.discountPercentageInput)
        quantityInput = findViewById(R.id.quantityInput)
        pickupStartInput = findViewById(R.id.pickupStartInput)
        pickupEndInput = findViewById(R.id.pickupEndInput)
        offersContainer = findViewById(R.id.offersContainer)
        addOfferButton = findViewById(R.id.addOfferButton)

        addOfferButton.setOnClickListener {
            if (selectedOfferId == null) {
                addOffer()
            } else {
                updateOffer()
            }
        }
        loadOffers()
    }

    private fun addOffer() {

        val name = offerNameInput.text.toString().trim()
        val description = offerDescriptionInput.text.toString().trim()
        val originalPrice = originalPriceInput.text.toString().toDoubleOrNull()
        val discountPercentage = discountPercentageInput.text.toString().toDoubleOrNull()
        val quantity = quantityInput.text.toString().toIntOrNull()
        val pickupStart = pickupStartInput.text.toString().trim()
        val pickupEnd = pickupEndInput.text.toString().trim()

        if (name.isEmpty() || description.isEmpty() ||
            originalPrice == null || discountPercentage == null ||
            quantity == null || pickupStart.isEmpty() || pickupEnd.isEmpty()
        ) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (originalPrice <= 0 || discountPercentage < 0 ||
            discountPercentage >= 100 || quantity <= 0
        ) {
            Toast.makeText(this, "Please enter valid prices, discount and quantity", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate the discounted price automatically
        val discountedPrice = originalPrice * (1 - discountPercentage / 100)

        val newId = (repository.getFoodOffers().maxOfOrNull { it.id } ?: 0) + 1

        val newOffer = FoodOffer(
            id = newId,
            restaurantId = 1,
            categoryId = 1,
            name = name,
            description = description,
            originalPrice = originalPrice,
            discountedPrice = discountedPrice,
            quantityAvailable = quantity,
            imageUrl = "",
            pickupStartTime = pickupStart,
            pickupEndTime = pickupEnd
        )

        repository.addFoodOffer(newOffer)

        Toast.makeText(this, "Offer added successfully", Toast.LENGTH_SHORT).show()

        clearFields()
        loadOffers()
    }
    private fun updateOffer() {
        val offerId = selectedOfferId ?: return

        val name = offerNameInput.text.toString().trim()
        val description = offerDescriptionInput.text.toString().trim()
        val originalPrice = originalPriceInput.text.toString().toDoubleOrNull()
        val discountPercentage = discountPercentageInput.text.toString().toDoubleOrNull()
        val quantity = quantityInput.text.toString().toIntOrNull()
        val pickupStart = pickupStartInput.text.toString().trim()
        val pickupEnd = pickupEndInput.text.toString().trim()

        if (name.isEmpty() || description.isEmpty() ||
            originalPrice == null || discountPercentage == null ||
            quantity == null || pickupStart.isEmpty() || pickupEnd.isEmpty()
        ) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (originalPrice <= 0 || discountPercentage < 0 ||
            discountPercentage >= 100 || quantity <= 0
        ) {
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            return
        }

        val oldOffer = repository.getFoodOfferById(offerId) ?: return

        val updatedOffer = oldOffer.copy(
            name = name,
            description = description,
            originalPrice = originalPrice,
            discountedPrice = originalPrice * (1 - discountPercentage / 100),
            quantityAvailable = quantity,
            pickupStartTime = pickupStart,
            pickupEndTime = pickupEnd
        )

        repository.updateFoodOffer(updatedOffer)

        Toast.makeText(this, "Offer updated successfully", Toast.LENGTH_SHORT).show()

        selectedOfferId = null
        addOfferButton.text = "Add offer"
        clearFields()
        loadOffers()
    }

    private fun loadOffers() {

        offersContainer.removeAllViews()

        val offers = repository.getFoodOffers()

        for (offer in offers) {

            val offerText = TextView(this)

            val discount = ((offer.originalPrice - offer.discountedPrice) /
                    offer.originalPrice) * 100

            offerText.text = """
                ${offer.name}
                ${offer.description}
                Original price: ₡${"%.0f".format(offer.originalPrice)}
                Discount: ${"%.0f".format(discount)}%
                Discounted price: ₡${"%.0f".format(offer.discountedPrice)}
                Available quantity: ${offer.quantityAvailable}
                Pickup: ${offer.pickupStartTime} - ${offer.pickupEndTime}
            """.trimIndent()

            offerText.textSize = 15f
            offerText.setPadding(16, 16, 16, 16)
            val editButton = Button(this)
            editButton.text = "Edit"

            editButton.setOnClickListener {
                selectedOfferId = offer.id

                offerNameInput.setText(offer.name)
                offerDescriptionInput.setText(offer.description)
                originalPriceInput.setText(offer.originalPrice.toString())
                discountPercentageInput.setText(
                    (((offer.originalPrice - offer.discountedPrice) / offer.originalPrice) * 100).toString()
                )
                quantityInput.setText(offer.quantityAvailable.toString())
                pickupStartInput.setText(offer.pickupStartTime)
                pickupEndInput.setText(offer.pickupEndTime)

                addOfferButton.text = "Update offer"
            }
            offersContainer.addView(offerText)
            offersContainer.addView(editButton)
            val deleteButton = Button(this)
            deleteButton.text = "Delete"

            deleteButton.setOnClickListener {
                android.app.AlertDialog.Builder(this)
                    .setTitle("Delete offer")
                    .setMessage("Are you sure you want to delete ${offer.name}?")
                    .setPositiveButton("Yes") { _, _ ->
                        repository.deleteFoodOffer(offer.id)

                        if (selectedOfferId == offer.id) {
                            selectedOfferId = null
                            addOfferButton.text = "Add offer"
                            clearFields()
                        }

                        Toast.makeText(this, "Offer deleted successfully", Toast.LENGTH_SHORT).show()
                        loadOffers()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }

            offersContainer.addView(deleteButton)
        }
    }

    private fun clearFields() {
        offerNameInput.text.clear()
        offerDescriptionInput.text.clear()
        originalPriceInput.text.clear()
        discountPercentageInput.text.clear()
        quantityInput.text.clear()
        pickupStartInput.text.clear()
        pickupEndInput.text.clear()
    }
}
