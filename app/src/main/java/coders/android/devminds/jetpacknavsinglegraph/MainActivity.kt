/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package coders.android.devminds.jetpacknavsinglegraph

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivity : AppCompatActivity() {

//    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.mainNavFragment)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            // no-op
        }
//
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (!navController.popBackStack(it.itemId, false)) {
                navController.navigate(it.itemId)
                true
            } else {
                false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navigateUp(findNavController(R.id.mainNavFragment), null)

    override fun onBackPressed() {
        if (!findNavController(R.id.mainNavFragment).popBackStack()) {
            super.onBackPressed()
        }
        val childFragmentManager = supportFragmentManager.findFragmentById(R.id.mainNavFragment)?.childFragmentManager
        val count = childFragmentManager?.backStackEntryCount ?: 0
        Log.d("dtm", "$count")
        for (index in count - 1 downTo 0)
        {
            val bck = childFragmentManager?.getBackStackEntryAt(index)
            Log.d("dtm", "${bck?.id} ---- ${bck?.breadCrumbShortTitle} ${bck?.breadCrumbShortTitleRes} ${bck?.breadCrumbTitle} ${bck?.breadCrumbTitleRes}")
        }
    }
}
