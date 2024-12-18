package com.dwan.ucp_2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiInsertDsn: AlamatNavigasi {
    override val route: String = "insert_dsn"
}

object DestinasiInsertMk: AlamatNavigasi {
    override val route: String = "insert_mk"
}

object DestinasiHome: AlamatNavigasi {
    override val route: String = "home"
}

object DestinasiDosen: AlamatNavigasi {
    override val route: String = "dosen"
}

object DestinasiMataKuliah: AlamatNavigasi {
    override val route: String = "mataKuliah"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route: String = "update"
    const val kodeMk = "mk"
    val routeWithArg = "$route/{$kodeMk}"
}

object DestinasiDetail: AlamatNavigasi {
    override val route: String = "detail"
    const val kodeMk = "mk"
    val routeWithArg = "$route/{$kodeMk}"
}