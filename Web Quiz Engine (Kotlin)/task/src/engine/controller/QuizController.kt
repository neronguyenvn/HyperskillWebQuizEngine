package engine.controller

import engine.controller.model.AnswerQuizRequest
import engine.controller.model.CreateQuizRequest
import engine.controller.model.Feedback
import engine.controller.model.asBusinessModel
import engine.model.Quiz
import engine.service.QuizService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quizzes")
@Validated
class QuizController(private val service: QuizService) {

    @PostMapping
    fun createQuiz(@RequestBody quiz: CreateQuizRequest): ResponseEntity<Quiz> {
        val createdQuiz = service.createQuiz(quiz.asBusinessModel())
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
}
