package org.hungrytessy.indycarsuperfan.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Overrides the default BottomNavigationView to host 6 items in the bottom menu
 * not in the constructor:
 * defStyleAttr: Int, defStyleRes: Int
 */
class IndyBottomNavigationView(context: Context, attrs: AttributeSet, ): BottomNavigationView(context, attrs) {
    override fun getMaxItemCount(): Int = 6
}
