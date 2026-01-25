package town.amrita.timetable.ui.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import town.amrita.timetable.models.Timetable
import town.amrita.timetable.ui.components.ChipsPicker
import town.amrita.timetable.ui.components.TimetablePreview
import town.amrita.timetable.ui.components.TimetableScaffold

@Composable
fun ConfigPickerScreen(
  timetable: Timetable,
  onConfigSelected: suspend (Map<String, String>) -> Unit
) {
  var selectedConfig by remember {
    mutableStateOf(timetable.config.mapValues { (_, option) -> option.values.firstOrNull()?.id ?: "" })
  }

  TimetableScaffold(
    title = "Configure Timetable",
  ) { horizontalPadding ->
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      if (timetable.config.isEmpty()) {
        Column(Modifier.padding(horizontal = horizontalPadding)) {
          Text("No configuration needed for this timetable.", fontWeight = FontWeight.Medium)
        }
        Spacer(Modifier.weight(1f))
      } else {
        Column(
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          for ((key, option) in timetable.config) {
            ChipsPicker(
              options = option.values.map { it.id },
              displayOptions = option.values.map { it.label },
              selected = selectedConfig[key],
              label = option.label,
              allowNull = false,
              onSelectionChanged = { newValue ->
                selectedConfig = selectedConfig + (key to (newValue ?: ""))
              },
              horizontalPadding = horizontalPadding
            )
          }
        }

        TimetablePreview(
          modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
          timetable = timetable,
          config = selectedConfig,
          horizontalPadding = horizontalPadding
        )
      }

      Box(Modifier.padding(horizontal = horizontalPadding)) {
        UseTimetableButton(
          modifier = Modifier.fillMaxWidth(),
          enabled = selectedConfig.values.all { it.isNotEmpty() },
          onApplyTimetable = { onConfigSelected(selectedConfig) })
      }
    }
  }
}
