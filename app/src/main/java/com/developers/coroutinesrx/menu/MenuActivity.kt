package com.developers.coroutinesrx.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.data.menu.Menu
import com.developers.coroutinesrx.data.menu.MenuNames
import com.developers.coroutinesrx.databinding.ActivityMenuBinding
import com.developers.coroutinesrx.exception.ExceptionActivity
import com.developers.coroutinesrx.performance.PerformanceActivity
import com.developers.coroutinesrx.state.CoroutinesStateManagement
import com.developers.coroutinesrx.state.RxStateManagement
import com.developers.coroutinesrx.state.UIBindingActivity
import com.developers.coroutinesrx.utils.intentTo
import com.developers.coroutinesrx.zip.ZipCallsActivity

class MenuActivity : AppCompatActivity() {

    companion object {
        private const val MENU_KEY = "MENU"
    }

    private lateinit var menuViewModel: MenuViewModel

    private val menuList = listOf(
        Menu(MenuNames.PERFORMANCE_ANALYSIS.getMenuName()),
        Menu(MenuNames.EXCEPTION_HANDLING.getMenuName()),
        Menu(MenuNames.ZIPPING.getMenuName()),
        Menu(MenuNames.RX_STATE_MANAGEMENT.getMenuName()),
        Menu(MenuNames.COROUTINES_STATE_MANAGEMENT.getMenuName()),
        Menu(MenuNames.UI_BINDINGS.getMenuName())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding =
            DataBindingUtil.setContentView<ActivityMenuBinding>(this, R.layout.activity_menu)
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val menuListAdapter = MenuListAdapter {
            navigateToMenu(it)
        }
        viewBinding.menuRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )
        viewBinding.menuListAdapter = menuListAdapter
        menuListAdapter.submitList(menuList)
    }

    private fun navigateToMenu(menu: Menu) {
        when (menu.name) {
            MenuNames.PERFORMANCE_ANALYSIS.getMenuName() -> {
                intentTo(PerformanceActivity::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
            MenuNames.EXCEPTION_HANDLING.getMenuName() -> {
                intentTo(ExceptionActivity::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
            MenuNames.ZIPPING.getMenuName() -> {
                intentTo(ZipCallsActivity::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
            MenuNames.RX_STATE_MANAGEMENT.getMenuName() -> {
                intentTo(RxStateManagement::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
            MenuNames.COROUTINES_STATE_MANAGEMENT.getMenuName() -> {
                intentTo(CoroutinesStateManagement::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
            MenuNames.UI_BINDINGS.getMenuName() -> {
                intentTo(UIBindingActivity::class.java) {
                    putParcelable(MENU_KEY, menu)
                }
            }
        }
    }

}
