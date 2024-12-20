package com.dwan.ucp_2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiInsertDsn: AlamatNavigasi {
    override val route: String = "insert_dosen"
}

object DestinasiInsertMk: AlamatNavigasi {
    override val route: String = "insert_mata_kuliah"
}

object DestinasiHome: AlamatNavigasi {
    override val route: String = "home"
}

object DestinasiDosen: AlamatNavigasi {
    override val route: String = "dosen"
}

object DestinasiMataKuliah: AlamatNavigasi {
    override val route: String = "mata_kuliah"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route: String = "update_mata_kuliah"
    const val kodeMk = "kodeMk"
    val routeWithArg = "$route/{$kodeMk}"
}

object DestinasiDetail: AlamatNavigasi {
    override val route: String = "detail_mata_kuliah"
    const val kodeMk = "kodeMk"
    val routeWithArg = "$route/{$kodeMk}"
}