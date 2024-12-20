package com.dwan.ucp_2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dwan.ucp_2.ui.view.DetailMataKuliahView
import com.dwan.ucp_2.ui.view.DosenView
import com.dwan.ucp_2.ui.view.HomeView
import com.dwan.ucp_2.ui.view.InsertDosenView
import com.dwan.ucp_2.ui.view.InsertMataKuliahView
import com.dwan.ucp_2.ui.view.MataKuliahView
import com.dwan.ucp_2.ui.view.UpdateMataKuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        composable(route = DestinasiHome.route) {
            HomeView(
                onNavigateDosen = {
                    navController.navigate(DestinasiDosen.route)
                },
                onNavigateMataKuliah = {
                    navController.navigate(DestinasiMataKuliah.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiDosen.route) {
            DosenView(
                onBack = {
                    navController.popBackStack()
                },
                onAddDosen = {
                    navController.navigate(DestinasiInsertDsn.route)
                },
                onNavigateDosen = {
                    navController.navigate(DestinasiDosen.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiMataKuliah.route) {
            MataKuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onAddMataKuliah = {
                    navController.navigate(DestinasiInsertMk.route)
                },
                onDetail = { kodeMk ->
                    navController.navigate("${DestinasiDetail.route}/$kodeMk")
                },
                onNavigateMataKuliah = {
                    navController.navigate(DestinasiMataKuliah.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertDsn.route) {
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateDosen = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertMk.route) {
            InsertMataKuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateMataKuliah = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.kodeMk) {
                    type = NavType.StringType
                }
            )
        ) {
            val kodeMk = it.arguments?.getString(DestinasiDetail.kodeMk)
            kodeMk?.let { kodeMkValue ->
                DetailMataKuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { kode ->
                        navController.navigate("${DestinasiUpdate.route}/$kode")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    kodeMk = kodeMkValue,
                    modifier = modifier
                )
            }
        }

        composable(
            route = DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.kodeMk) {
                    type = NavType.StringType
                }
            )
        ) {
            val kodeMk = it.arguments?.getString(DestinasiUpdate.kodeMk)
            kodeMk?.let { kode ->
                UpdateMataKuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigateMataKuliah = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }
    }
}
