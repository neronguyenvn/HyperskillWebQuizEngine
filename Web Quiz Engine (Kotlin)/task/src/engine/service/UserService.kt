package engine.service

import engine.model.User
import engine.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    fun createUser(user: User) {
        repository.save(user)
    }

    fun findUserByEmail(email: String): User? {
        return repository.findUserByEmail(email)
    }
}
