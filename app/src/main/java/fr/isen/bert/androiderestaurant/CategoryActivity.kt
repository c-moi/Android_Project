package fr.isen.bert.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.RoundedCorner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.bert.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.json.JSONObject
import com.google.gson.Gson
import fr.isen.bert.androiderestaurant.model.DataResult
import fr.isen.bert.androiderestaurant.model.Items
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import fr.isen.bert.androiderestaurant.model.Ingredients

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra("category") ?: ""

        setContent {
            AndroidERestaurantTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Log.d("Header", "Title: $category")
                    CategoryContent(category)
                }
            }
        }
    }
}


@Composable
fun CategoryContent(category: String) {
    val context = LocalContext.current
    val menuItems = remember { mutableStateOf<List<Items>>(emptyList()) }

    fetchData(category, context) { dishes ->
        menuItems.value = dishes
    }

    Column {

        Header(category)

        menuItems.value.forEach {dish ->
            Click(dish, context)
        }
    }
}

private fun fetchData(category: String, context: Context, onDataLoaded: (List<Items>) -> Unit) {
    val url = "http://test.api.catering.bluecodegames.com/menu"
    val param = JSONObject().apply {
        put("id_shop", "1")
    }

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, url, param,
        { response ->
            val result = Gson().fromJson(response.toString(),
                DataResult::class.java)
            val dishesFromCategory = result.data.find {
                it.nameFr == category }?.items ?:emptyList()
            onDataLoaded(dishesFromCategory)
            Log.d("CategoryActivity", "result = $response")
        },
        { error ->
            Log.d("CategoryActivity", "error = $error")
        }
    )
    Volley.newRequestQueue(context).add(jsonObjectRequest)
}


@Composable
fun Click(plat: Items, context: Context) {
    val painter: Painter = rememberImagePainter(
        data = plat.images.firstOrNull(),
        builder = {
            crossfade(true)
            placeholder(R.drawable.placeholder)
            error(R.drawable.error)
        }
    )
    
    Column(modifier = Modifier.clickable {
        val intentN = Intent(context, DetailActivity::class.java)
        intentN.putExtra("Name", plat.nameFr)
        intentN.putExtra("Ingredients", buildIngredientsText(plat.ingredients))
        intentN.putExtra("ImageUrl", plat.images.firstOrNull())
        context.startActivity(intentN)
    }) {
        Image(
            painter = painter,
            contentDescription = plat.nameFr,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = plat.nameFr ?: "",
            style = TextStyle(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = (plat.prices.firstOrNull()?.price ?: "N/A") + " €",
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun buildIngredientsText(ingredients: List<Ingredients>): String {
    val stringBuilder = StringBuilder()
    for (ingredient in ingredients) {
        stringBuilder.append("${ingredient.nameFr}, ")
    }
    // Supprimer la virgule et l'espace en trop à la fin
    if (stringBuilder.isNotEmpty()) {
        stringBuilder.delete(stringBuilder.length - 2, stringBuilder.length)
    }
    return stringBuilder.toString()
}