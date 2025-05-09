package engine.model

import engine.controller.model.response.QuizCompletionResponse
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class QuizCompletion(

    val quizId: Long,

    val completedAt: LocalDateTime,

    val answererEmail: String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val completionId: Long? = null,
)

fun QuizCompletion.asResponseModel() = QuizCompletionResponse(
    id = quizId,
    completedAt = completedAt.toString()
)
