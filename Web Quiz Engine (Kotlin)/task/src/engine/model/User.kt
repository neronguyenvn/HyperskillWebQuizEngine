package engine.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "users")
data class User(

    val email: String,

    val password: String,

    val role: String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)

// TODO
fun User.asUserDetails(): UserDetails = org.springframework.security.core.userdetails.User(
    email, password, listOf(SimpleGrantedAuthority("ROLE_$role"))
)
