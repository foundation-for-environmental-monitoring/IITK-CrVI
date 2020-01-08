package io.ffem.iitk.ui.main.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    init {
        // Add some sample items.
        addItem(DummyItem("1", "Iron Sulphate"))
        addItem(DummyItem("2", "Electrocoagulation"))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

//    private fun createDummyItem(position: Int): DummyItem {
//        return DummyItem(position.toString(), "Item $position", makeDetails(position))
//    }
//
    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String) {
        override fun toString(): String = content
    }
}
