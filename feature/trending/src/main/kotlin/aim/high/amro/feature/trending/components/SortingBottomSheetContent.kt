package aim.high.amro.feature.trending.components

import aim.high.amro.core.model.SortingCriteria
import aim.high.amro.core.model.SortingDirection
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun SortingBottomSheetContent(
    currentCriteria: SortingCriteria,
    currentDirection: SortingDirection,
    onCriteriaSelect: (SortingCriteria) -> Unit,
    onDirectionSelect: (SortingDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp, start = 24.dp, end = 24.dp, top = 8.dp)
    ) {
        Text(
            text = "Sort & Filter",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Order By",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        SortingCriteria.entries.forEach { criteria ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCriteriaSelect(criteria) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (currentCriteria == criteria),
                    onClick = { onCriteriaSelect(criteria) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = criteria.label)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Direction",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SortingDirection.entries.forEachIndexed { index, direction ->
                SegmentedButton(
                    selected = currentDirection == direction,
                    onClick = { onDirectionSelect(direction) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = SortingDirection.entries.size
                    ),
                    label = { Text(direction.name.lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
