package engine.controller

import engine.controller.model.AnswerQuizRequest
import engine.controller.model.CreateQuizRequest
import engine.controller.model.Feedback
import engine.controller.model.asBusinessModel
import engine.model.Quiz
import engine.service.QuizService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quizzes")
@Validated
class QuizController(private val service: QuizService) {

    @PostMapping
    fun createQuiz(
        @RequestBody @Valid request: CreateQuizRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Quiz> {
        val quiz = request.asBusinessModel(user.username)
        val createdQuiz = service.createQuiz(quiz)
        return ResponseEntity.ok(createdQuiz)
    }

    @GetMapping
    fun getAllQuizzes(): ResponseEntity<List<Quiz>> {
        val quizzes = service.getAllQuizzes()
        return ResponseEntity.ok(quizzes)
    }

    @GetMapping("/{id}")
    fun getQuizById(@PathVariable id: Long): ResponseEntity<Any> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(quiz)
    }

    @PostMapping("/{id}/solve")
    fun answer(
        @PathVariable id: Long,
        @RequestBody answer: AnswerQuizRequest
    ): ResponseEntity<Any> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        val isCorrect = service.answerQuiz(answer.answer, quiz)
        val feedback =
            if (isCorrect) Feedback.trueAnswer
            else Feedback.wrongAnswer

        return ResponseEntity.ok(feedback)
    }

    @DeleteMapping("/{id}")
    fun deleteQuiz(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Any> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        if (quiz.authorEmail != user.username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        service.deleteQuiz(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
