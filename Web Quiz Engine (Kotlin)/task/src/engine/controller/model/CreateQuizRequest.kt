package engine.controller.model

import engine.model.Quiz
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateQuizRequest(

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val text: String,

    @field:Size(min = 2)
    val options: List<String>,

    val answer: List<Int> = emptyList(),
)

fun CreateQuizRequest.asBusinessModel(authorEmail: String) = Quiz(
    title = title,
    text = text,
    options = options,
    answer = answer,
    authorEmail = authorEmail
)
