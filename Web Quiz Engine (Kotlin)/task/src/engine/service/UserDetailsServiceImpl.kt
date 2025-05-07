package engine.service

import engine.model.asUserDetails
import engine.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val repository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return repository.findUserByEmail(username)?.asUserDetails()
    }
}
