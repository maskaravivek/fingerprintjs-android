package com.fingerprint.kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fingerprint.kotlin.ui.theme.FingerPrintKotlinTheme
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.launch

// Add the line below
var fingerPrint: FingerPrint? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Add the line below
            fingerPrint = FingerPrint(context = LocalContext.current)

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
            route = "DashboardScreen?user={user}",
        ) { backStackEntry ->
            val user: JsonObject? = backStackEntry
                .arguments?.getString("user")?.let {
                    JsonParser().parse(it).asJsonObject
                }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                color = Color.White
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextComponent(heading = "Welcome ${user?.get("name")}")
                    Text(
                        text = "Your email address is: ${user?.get("email")}.\nYour Visitor ID is: ${
                            user?.get(
                                "visitorId"
                            )
                        }"
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        color = Color.White
    ) {
        Column() {
            Spacer(modifier = Modifier.height(10.dp))
            HeadingTextComponent(heading = "Login")
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                MyTextField(
                    labelVal = "Email",
                    Icons.Default.Email,
                    onValueChange = { updatedValue ->
                        email = updatedValue
                    })
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(
                    labelVal = "Password",
                    Icons.Default.Lock,
                    onValueChange = { updatedValue ->
                        password = updatedValue
                    })
                Spacer(modifier = Modifier.height(15.dp))
            }
            MyButton(
                labelVal = "Login",
                loading = loading,
                onPressed = {
                    loading = true
                    coroutineScope.launch {
                        val res = Network().loginUser(
                            email, password, fingerPrint?.visitorId,
                            fingerPrint?.requestId
                        )
                        loading = false
                        if (res != null) {
                            Log.d("", res.toString())
                            if (res.isJsonPrimitive) {
                                Toast.makeText(context, res.toString(), Toast.LENGTH_LONG).show()
                            } else {
                                navController.navigate("DashboardScreen?user=${res}")
                            }
                        }
                    }
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
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
                MyTextField(
                    labelVal = "Email",
                    icon = Icons.Default.Email,
                    onValueChange = { updatedValue ->
                        email = updatedValue
                    })
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(
                    labelVal = "Name",
                    icon = Icons.Default.Person,
                    onValueChange = { updatedValue ->
                        name = updatedValue
                    })
                Spacer(modifier = Modifier.height(15.dp))
                MyTextField(
                    labelVal = "Password",
                    icon = Icons.Default.Lock,
                    onValueChange = { updatedValue ->
                        password = updatedValue
                    })
            }
            MyButton(
                labelVal = "Continue",
                loading = loading,
                onPressed = {
                    loading = true
                    coroutineScope.launch {
                        val res = Network().signUpUser(
                            email,
                            password,
                            name,
                            fingerPrint?.visitorId,
                            fingerPrint?.requestId
                        )
                        loading = false
                        if (res != null) {
                            Log.d("", res.toString())
                            if (res.isJsonPrimitive) {
                                Toast.makeText(context, res.toString(), Toast.LENGTH_LONG).show()
                            } else {
                                navController.navigate("DashboardScreen?user=${res}")
                            }
                        }
                    }
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