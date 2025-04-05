package engine.controller

import engine.controller.model.Feedback
import engine.model.Quiz
import engine.service.QuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quizzes")
class QuizController(private val service: QuizService) {

    @PostMapping
    fun createQuiz(@RequestBody quiz: Quiz): ResponseEntity<Quiz> {
        val createdQuiz = service.createQuiz(quiz)
        return ResponseEntity.ok(createdQuiz)
    }

    @GetMapping
    fun getAllQuizzes(): ResponseEntity<List<Quiz>> {
        val quizzes = service.getAllQuizzes().map {
            it.copy(answer = null)
        }

        return ResponseEntity.ok(quizzes)
    }

    @GetMapping("/{id}")
    fun getQuizById(@PathVariable id: Long): ResponseEntity<Any> {
        val quiz = service.getQuizById(id)?.copy(answer = null) ?: run {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(quiz)
    }

    @PostMapping("/{id}/solve")
    fun answer(
        @PathVariable id: Long,
        @RequestParam answer: Int
    ): ResponseEntity<Any> {
        val quiz = service.getQuizById(id) ?: run {
            return ResponseEntity.notFound().build()
        }

        if (quiz.answer != answer) {
            val feedback = Feedback(
                success = false,
                feedback = "Wrong answer! Please, try again."
            )
            return ResponseEntity.ok(feedback)
        }

        val feedback = Feedback(
            success = true,
            feedback = "Congratulations, you're right!"
        )
        return ResponseEntity.ok(feedback)
    }
}
