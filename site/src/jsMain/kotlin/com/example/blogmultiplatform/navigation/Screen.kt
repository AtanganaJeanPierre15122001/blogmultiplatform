package com.example.blogmultiplatform.navigation

sealed class Screen(val route: String) {
    data object AdminHome : Screen(route = "/admin/")
    data object AdminLogin : Screen(route = "/admin/login")
    data object AdminCreate : Screen(route = "/admin/create")
    data object AdminSuccess : Screen(route = "/admin/success")
    data object AdminMyPosts : Screen(route = "/admin/my-posts")
}