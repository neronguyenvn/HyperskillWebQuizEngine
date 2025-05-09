package engine.controller

import engine.controller.model.request.AnswerQuizRequest
import engine.controller.model.request.CreateQuizRequest
import engine.controller.model.request.asBusinessModel
import engine.controller.model.response.FeedbackResponse
import engine.controller.model.response.QuizCompletionResponse
import engine.controller.model.response.QuizResponse
import engine.model.asResponseModel
import engine.service.QuizService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quizzes")
class QuizController(private val service: QuizService) {

    @PostMapping
    fun createQuiz(
        @RequestBody @Valid request: CreateQuizRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<QuizResponse> {
        val quiz = request.asBusinessModel(user.username)
        val createdQuiz = service.createQuiz(quiz).asResponseModel()
        return ResponseEntity.ok(createdQuiz)
    }

    @GetMapping
    fun getQuizzes(page: Int): ResponseEntity<Page<QuizResponse>> {
        val quizzes = service.getQuizzes(page)
        return ResponseEntity.ok(quizzes.map { it.asResponseModel() })
    }

    @GetMapping("/{id}")
    fun getQuizById(@PathVariable id: Long): ResponseEntity<QuizResponse> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(quiz.asResponseModel())
    }

    @PostMapping("/{id}/solve")
    fun answer(
        @PathVariable id: Long,
        @RequestBody answer: AnswerQuizRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<FeedbackResponse> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        val isCorrect = service.answerQuiz(
            answer = answer.answer,
            quiz = quiz,
            answererEmail = user.username
        )

        val feedback =
            if (isCorrect) FeedbackResponse.trueAnswer
            else FeedbackResponse.wrongAnswer

        return ResponseEntity.ok(feedback)
    }

    @DeleteMapping("/{id}")
    fun deleteQuiz(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<HttpStatus> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        if (quiz.authorEmail != user.username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        service.deleteQuiz(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/completed")
    fun getQuizCompletions(
        page: Int,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Page<QuizCompletionResponse>> {
        val quizCompletions = service
            .getQuizCompletionsByAnswererEmail(
                email = user.username,
                page = page
            )
            .map { it.asResponseModel() }

        return ResponseEntity.ok(quizCompletions)
    }
}
