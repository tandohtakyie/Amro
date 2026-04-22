package aim.high.amro.core.model

enum class SortingCriteria(val label: String) {
    POPULARITY("Popularity"),

    TITLE("Title"),

    RELEASE_DATE("Release Date")
}

enum class SortingDirection {
    ASCENDING,

    DESCENDING
}

data class SortingSettings(
    val criteria: SortingCriteria = SortingCriteria.POPULARITY,
    val direction: SortingDirection = SortingDirection.DESCENDING
)
