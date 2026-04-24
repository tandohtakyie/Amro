package aim.high.amro.core.model

import androidx.annotation.StringRes

enum class SortingCriteria(@StringRes val labelRes: Int) {
    POPULARITY(R.string.sorting_criteria_popularity),

    TITLE(R.string.sorting_criteria_title),

    RELEASE_DATE(R.string.sorting_criteria_release_date),

    VOTE_AVERAGE(R.string.sorting_criteria_rating)
}

enum class SortingDirection(@StringRes val labelRes: Int) {
    ASCENDING(R.string.sorting_direction_ascending),

    DESCENDING(R.string.sorting_direction_descending)
}

data class SortingSettings(
    val criteria: SortingCriteria = SortingCriteria.POPULARITY,
    val direction: SortingDirection = SortingDirection.DESCENDING
)
