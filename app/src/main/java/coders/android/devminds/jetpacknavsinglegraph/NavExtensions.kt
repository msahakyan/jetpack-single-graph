package coders.android.devminds.jetpacknavsinglegraph

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference
import java.util.*

/**
 * Sets up a [BottomNavigationView] for use with a [NavController]. This will call
 * [onNavDestinationSelected] when a menu item is selected. The
 * selected item in the BottomNavigationView will automatically be updated when the destination
 * changes.
 *
 * @param navController The NavController that supplies the primary menu.
 * Navigation actions on this NavController will be reflected in the
 * selected item in the BottomNavigationView.
 */
fun BottomNavigationView.setupWithNavController(
    navController: NavController
) {
    this.setOnNavigationItemSelectedListener { item: MenuItem ->
        if (!navController.popBackStack(item.itemId, false)) {
            onNavDestinationSelected(item, navController)
        }
        false
    }
    val weakReference = WeakReference(this)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination, arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }

                val items = listOf(R.id.titleScreen, R.id.leaderboard, R.id.register)
                if (destination.id in items) {
//                    if (!list.contains(destination.id))
//                        list.add(destination.id)
//                    else {
//                        val index: Int = list.indexOf(destination.id)
//                        list.retainAll(list.subList(0, index))
//                    }
                    if(!map.contains(destination.id)) {
                        map[destination.id] = false
                    } else {
                        for((key, value) in map) {
                            if (key == destination.id) {
                                map[destination.id] = true
                            }
                        }
//                        val index: Int = map.get(destination.id)
//                        list.retainAll(list.subList(0, index))
                    }
                }

                val menu = view.menu
                var h = 0
                val size = menu.size()
                while (h < size) {
                    val item = menu.getItem(h)

                    Log.d("dtm", "menuItem id -- ${item.itemId}")

//                    if (matchDestinationAdvanced(navController, destination, item.itemId)) {
//                    if (/*matchDestination(destination, item.itemId)*/  list.last() == item.itemId) {
                    if (map.lastKey() == item.itemId) {
                        item.isChecked = true
                        map.remove(map.lastKey())
                    }
                    h++
                }
            }
        })
}

/**
 * Determines whether the given `destId` matches the NavDestination. This handles
 * both the default case (the destination's id matches the given id) and the nested case where
 * the given id is a parent/grandparent/etc of the destination.
 */
internal fun matchDestination(
    destination: NavDestination,
    @IdRes destId: Int
): Boolean {
    var currentDestination: NavDestination? = destination
    while (currentDestination!!.id != destId && currentDestination.parent != null) {
        currentDestination = currentDestination.parent
    }
    return currentDestination.id == destId
}

internal fun matchDestinationAdvanced(
    navController: NavController,
    destination: NavDestination,
    @IdRes destId: Int
): Boolean {
    var currentDestination: NavDestination? = destination
    while (currentDestination!!.id != destId) {
//        val backStack = NavController::class.java.getDeclaredField("mBackStack").apply { isAccessible = true }
//        currentDestination = backStack.get(navController)
    }
    return currentDestination.id == destId
}


//
//fun BottomNavigationView.matchFinalDestination(destination: NavDestination) {
//    var h = 0
//    val size = menu.size()
//    while (h < size) {
//        val item = menu.getItem(h)
//        if (matchDestination(destination, item.itemId)) {
//            item.isChecked = true
//        }
//        h++
//    }
//}

val list = mutableListOf<Int>()

val map = TreeMap<Int, Boolean>()