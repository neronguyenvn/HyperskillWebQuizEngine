package engine.model

data class Quiz(
    val title: String,
    val text: String,
    val options: List<String>,
)
