package fr.isen.bert.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import fr.isen.bert.androiderestaurant.model.Ingredients
import fr.isen.bert.androiderestaurant.ui.theme.AndroidERestaurantTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("Name")
        val ingredients = intent.getStringExtra("Ingredients")
        val imageUrl = intent.getStringExtra("ImageUrl")

        setContent {
            AndroidERestaurantTheme {
                Toast.makeText(LocalContext.current, name, Toast.LENGTH_SHORT).show()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        Header(stringResource(id = R.string.app_name))
                        Detail(name ?: "", ingredients ?: "", imageUrl ?: "")
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Detail(name: String, ingredients: String, imageUrl: String) {
    var painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true) // Animation de transition entre les images
            placeholder(R.drawable.placeholder) // Image à afficher pendant le chargement
            error(R.drawable.error) // Image à afficher en cas d'erreur de chargement
        }
    )

    Column(
        modifier = Modifier.padding(16.dp),

    ) {
        // Afficher image du plat
        Image(
            painter = painter,
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Afficher le titre du plat
        Text(
            text = name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Afficher la liste des ingrédients du plat
        Text(
            text = ingredients,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


fun ImageItem(imageUrl: String) {

}