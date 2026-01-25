package town.amrita.timetable.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsPicker(
  modifier: Modifier = Modifier,
  options: List<String>,
  displayOptions: List<String> = options,
  selected: String?,
  label: String,
  onSelectionChanged: (String?) -> Unit,
  allowNull: Boolean = true,
  horizontalPadding: Dp = 0.dp,
) {

  Column(modifier) {
    Text(
      label, 
      fontWeight = FontWeight.Medium,
      modifier = Modifier.padding(horizontal = horizontalPadding)
    )

    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(top = 4.dp)) {
      Spacer(Modifier.width(horizontalPadding))
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.zip(displayOptions).map { (option, displayOption) ->
          FilterChip(modifier = Modifier.height(32.dp), selected = selected == option,
            onClick = {
              if (allowNull && selected == option) onSelectionChanged(null)
              else onSelectionChanged(option)
            },
            label = {
            Text(displayOption)
            }
          )
        }
      }
      Spacer(Modifier.width(horizontalPadding))
    }
  }
}
