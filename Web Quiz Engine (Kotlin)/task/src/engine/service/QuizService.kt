package engine.service

import engine.model.Quiz
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class QuizService {

    private val quizzes = mutableListOf<Quiz>()
    private val currentId = AtomicLong(0)

    fun createQuiz(quiz: Quiz): Quiz {
        val createdQuiz = quiz.copy(id = currentId.getAndIncrement())
        quizzes.add(createdQuiz)
        return createdQuiz
    }

    fun getAllQuizzes(): List<Quiz> {
        return quizzes
    }

    fun getQuizById(id: Long): Quiz? {
        return quizzes.find { it.id == id }
    }
}
