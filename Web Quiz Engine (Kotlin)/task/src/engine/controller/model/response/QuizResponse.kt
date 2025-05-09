package engine.controller.model.response

data class QuizResponse(
    val title: String,
    val text: String,
    val options: List<String>,
    val id: Long? = null,
)
