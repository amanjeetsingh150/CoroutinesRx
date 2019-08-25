package com.developers.coroutinesrx.data.menu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(val name: String) : Parcelable

enum class MenuNames {
    PERFORMANCE_ANALYSIS {
        override fun getMenuName(): String {
            return "Performance Analysis"
        }
    },
    EXCEPTION_HANDLING {
        override fun getMenuName(): String {
            return "Exception Handling"
        }
    },
    ZIPPING {
        override fun getMenuName(): String {
            return "Zipping Calls"
        }
    },
    RX_STATE_MANAGEMENT {
        override fun getMenuName(): String {
            return "Reactive State Management"
        }
    },
    COROUTINES_STATE_MANAGEMENT {
        override fun getMenuName(): String {
            return "Coroutines State Management"
        }
    };


    abstract fun getMenuName(): String
}