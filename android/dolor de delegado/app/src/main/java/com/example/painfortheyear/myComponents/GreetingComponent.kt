package com.example.painfortheyear.myComponents
import com.example.painfortheyear.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.painfortheyear.ui.theme.PainForTheYearTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val contentId="waveHandIcon"
    val waveHandContent = mapOf(
        contentId to InlineTextContent(
            Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            ),{
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background)
                , contentDescription = "Icono de mano saludando")
            })
        )


    Text(text = buildAnnotatedString {
        append("mi nombre es ")
        appendInlineContent(contentId, alternateText = "[wavehand]")
        append(" encantado")
         }
        ,modifier = modifier.border(width = 1.dp, color = Color.Red).fillMaxWidth().padding(2.dp)
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PainForTheYearTheme {
        Greeting("Android")
    }
}