package engine.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

private typealias FrameworkUser = org.springframework.security.core.userdetails.User

@Entity(name = "users")
data class User(

    val email: String,

    val password: String,

    val authority: String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)

fun User.asUserDetails(): UserDetails = FrameworkUser(
    email, password,
    listOf(SimpleGrantedAuthority(authority))
)
