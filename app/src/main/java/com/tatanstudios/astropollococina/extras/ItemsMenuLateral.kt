package com.tatanstudios.astropollococina.extras

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RealEstateAgent
import androidx.compose.ui.graphics.vector.ImageVector
import com.tatanstudios.astropollococina.R

sealed class ItemsMenuLateral(
    val icon: ImageVector,
    val idString: Int,
    val id: Int
) {
    object ItemMenu1 : ItemsMenuLateral(
        Icons.Filled.ShoppingCart,
        R.string.nuevas_ordenes,
        1
    )

    object ItemMenu2 : ItemsMenuLateral(
        Icons.AutoMirrored.Filled.ListAlt,
        R.string.orden_en_preparacion,
        2
    )

    object ItemMenu3 : ItemsMenuLateral(
        Icons.Filled.Inventory,
        R.string.completadas_hoy,
        3
    )

    object ItemMenu4 : ItemsMenuLateral(
        Icons.Filled.Timeline,
        R.string.canceladas_hoy,
        4
    )


    object ItemMenu5 : ItemsMenuLateral(
        Icons.Filled.Checklist,
        R.string.categorias,
        5
    )

    object ItemMenu6 : ItemsMenuLateral(
        Icons.Filled.Notifications,
        R.string.notificacion,
        6
    )

    object ItemMenu7 : ItemsMenuLateral(
        Icons.AutoMirrored.Filled.ReceiptLong,
        R.string.historial,
        7
    )

    object ItemMenu8 : ItemsMenuLateral(
        Icons.AutoMirrored.Filled.Logout,
        R.string.cerrar_sesion,
        8
    )
}

// Lista de items del menú lateral
val itemsMenu = listOf(ItemsMenuLateral.ItemMenu1,
    ItemsMenuLateral.ItemMenu2,
    ItemsMenuLateral.ItemMenu3,
    ItemsMenuLateral.ItemMenu4,
    ItemsMenuLateral.ItemMenu5,
    ItemsMenuLateral.ItemMenu6,
    ItemsMenuLateral.ItemMenu7,
    ItemsMenuLateral.ItemMenu8,
)
