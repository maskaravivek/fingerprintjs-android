package com.fingerprint.kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fingerprint.kotlin.ui.theme.FingerPrintKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FingerPrintKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "LoginScreen"
    ) {
        composable(
            route = "LoginScreen"
        ) {
            LoginScreen(
                navController
            )
        }
        composable(
            route = "SignupScreen"
        ) {
            SignupScreen(navController)
        }
        composable(
            route = "DashboardScreen"
        ) {
            Surface(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                color = Color.White
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextComponent(heading = "Welcome Buggy")
                }}
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        color = Color.White
    ) {
        Column() {
            Spacer(modifier = Modifier.height(10.dp))
            HeadingTextComponent(heading = "Login")
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                MyTextField(labelVal = "Email", Icons.Default.Email)
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(labelVal = "Password", Icons.Default.Lock)
                Spacer(modifier = Modifier.height(15.dp))
            }
            MyButton(labelVal = "Login", onPressed = {
                navController.navigate("DashboardScreen")
            })
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = { navController.navigate("SignupScreen") }
            ) {
                Text("Create an account?")
            }
        }
    }
}

@Composable
fun SignupScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        color = Color.White
    ) {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            HeadingTextComponent(heading = "Sign Up")
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                MyTextField(labelVal = "Email", icon = Icons.Default.Email)
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(labelVal = "Name", icon = Icons.Default.Person)
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(labelVal = "Mobile", icon = Icons.Default.Phone)
            }
            MyButton(labelVal = "Continue", onPressed = {
                navController.navigate("DashboardScreen")
            })
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = { navController.navigate("LoginScreen") }
            ) {
                Text("Already have an account?")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    FingerPrintKotlinTheme {
        LoginScreen(navController)
    }
}