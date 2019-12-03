package io.ffem.iitk.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    fm: FragmentManager,
    val pageCount: Int
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return SelectWaterTypeFragment.newInstance("", "")
        } else if (position == 1) {
            return TreatmentTypeFragment.newInstance("", "")
        } else {
            return PlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}