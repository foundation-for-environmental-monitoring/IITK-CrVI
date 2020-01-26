package io.ffem.iitk.ui.main.model

import io.ffem.iitk.ui.main.TreatmentType
import java.util.*

object Treatments {

    val ITEMS: MutableList<TreatmentItem> = ArrayList()

    private val ITEM_MAP: MutableMap<String, TreatmentItem> = HashMap()

    init {
        addItem(TreatmentItem("1", TreatmentType.IRON_SULPHATE, "Iron Sulphate"))
        addItem(TreatmentItem("2", TreatmentType.ELECTROCOAGULATION, "Electrocoagulation"))
    }

    private fun addItem(item: TreatmentItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

//        private fun createTreatmentItem(position: Int): TreatmentItem {
//        return TreatmentItem(position.toString(), "Item $position", makeDetails(position))
//    }

    data class TreatmentItem(val id: String, val treatmentType: TreatmentType, val name: String) {
        override fun toString(): String = name
    }
}
