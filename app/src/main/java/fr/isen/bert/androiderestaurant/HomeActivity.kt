package fr.isen.bert.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.bert.androiderestaurant.ui.theme.AndroidERestaurantTheme
import fr.isen.bert.androiderestaurant.ui.theme.Mandarine40
import org.json.JSONObject


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        val entrees: String = stringResource(id = R.string.option_entree)
                        val plats: String = stringResource(id = R.string.option_plats)
                        val desserts: String = stringResource(id = R.string.option_desserts)

                        Header(stringResource(id = R.string.app_name))
                        Title()
                        Menu(entrees, plats, desserts)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Home activity is destroyed")
    }
}

@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Mandarine40)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun Title() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.LightGray, Color(0xFFFFFBFE)),
                    startY = 0f,
                    endY = 10f
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = stringResource(id = R.string.home_welcome),
                color = Mandarine40,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )

            Text(
                text = stringResource(id = R.string.home_welcome_app_name),
                color = Color(0xFF5E3206),
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 15.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo), // Ajoutez votre image
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(top = 16.dp) // Marge à droite pour l'image
        )
    }
}


@Composable
fun Menu(entrees: String, plats: String, desserts: String) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFFFFBFE))
            .padding(start = 60.dp, end = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.option_entree),
            color = Mandarine40,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .clickable {
                    //Toast.makeText(context, plats, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, CategoryActivity::class.java)
                    intent.putExtra("category", entrees)
                    context.startActivity(intent)
                }
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color(0xFF5E3206))
        )
        Text(
            text = stringResource(id = R.string.option_plats),
            color = Mandarine40,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .clickable {
                    //Toast.makeText(context, plats, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, CategoryActivity::class.java)
                    intent.putExtra("category", plats)
                    context.startActivity(intent)
                }
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color(0xFF5E3206))
        )
        Text(
            text = stringResource(id = R.string.option_desserts),
            color = Mandarine40,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .clickable {
                    //Toast.makeText(context, plats, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, CategoryActivity::class.java)
                    intent.putExtra("category", desserts)
                    context.startActivity(intent)
                }
        )
    }
}
