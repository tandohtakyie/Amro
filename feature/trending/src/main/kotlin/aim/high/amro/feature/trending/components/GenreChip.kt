package aim.high.amro.feature.trending.components

import aim.high.amro.core.designsystem.theme.AmroTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun GenreChip(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isActive) MaterialTheme.colorScheme.primary else Color.Transparent
    val contentColor =
        if (isActive) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    val borderColor =
        if (isActive) Color.Transparent else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = contentColor,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreChipActivePreview() {
    AmroTheme {
        GenreChip(
            label = "Action",
            isActive = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreChipInactivePreview() {
    AmroTheme {
        GenreChip(
            label = "Drama",
            isActive = false,
            onClick = {}
        )
    }
}
