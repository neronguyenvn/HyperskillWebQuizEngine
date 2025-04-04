package engine.controller

import engine.controller.model.Feedback
import engine.model.Quiz
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz")
class QuizController {

    @GetMapping
    fun getQuiz(): ResponseEntity<Quiz> {
        val quiz = Quiz(
            title = "The Java Logo",
            text = "What is depicted on the Java logo?",
            options = listOf("Robot", "Tea leaf", "Cup of coffee", "Bug")
        )
        return ResponseEntity.ok(quiz)
    }

    @PostMapping
    fun answer(answer: Int): ResponseEntity<Feedback> {
        if (answer != 2) {
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
