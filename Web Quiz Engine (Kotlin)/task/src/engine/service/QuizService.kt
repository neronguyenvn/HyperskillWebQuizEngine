package engine.service

import engine.model.Quiz
import engine.model.QuizCompletion
import engine.repository.QuizCompletionRepository
import engine.repository.QuizRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val quizCompletionRepository: QuizCompletionRepository
) {

    fun createQuiz(quiz: Quiz): Quiz {
        val createdQuiz = quizRepository.save(quiz)
        return createdQuiz
    }

    fun getQuizzes(page: Int): Page<Quiz> {
        val pageable = Pageable.ofSize(10).withPage(page)
        return quizRepository.findAll(pageable)
    }

    fun getQuizById(id: Long): Quiz? {
        return quizRepository.findByIdOrNull(id)
    }

    fun answerQuiz(
        answer: List<Int>,
        quiz: Quiz,
        answererEmail: String
    ): Boolean {
        val isCorrect = quiz.answer == answer

        if (isCorrect) {
            val quizCompletion = QuizCompletion(
                quizId = quiz.id!!,
                completedAt = LocalDateTime.now(),
                answererEmail = answererEmail,
            )
            quizCompletionRepository.save(quizCompletion)
        }

        return isCorrect
    }

    fun deleteQuiz(id: Long) {
        quizRepository.deleteById(id)
    }

    fun getQuizCompletionsByAnswererEmail(
        email: String,
        page: Int
    ): Page<QuizCompletion> {
        val pageable = Pageable.ofSize(10).withPage(page)
        return quizCompletionRepository.findByAnswererEmailOrderByCompletedAtDesc(
            email = email,
            pageable = pageable
        )
    }
}
