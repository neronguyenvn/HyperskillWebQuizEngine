package engine.service

import engine.model.Quiz
import engine.repository.QuizRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizService(private val repository: QuizRepository) {

    fun createQuiz(quiz: Quiz): Quiz {
        val createdQuiz = repository.save(quiz)
        return createdQuiz
    }

    fun getAllQuizzes(): List<Quiz> {
        return repository.findAll()
    }

    fun getQuizById(id: Long): Quiz? {
        return repository.findByIdOrNull(id)
    }

    fun answerQuiz(answer: List<Int>, quiz: Quiz): Boolean {
        return quiz.answer == answer
    }
}
