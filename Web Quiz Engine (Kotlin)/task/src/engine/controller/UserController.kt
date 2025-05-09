package engine.controller

import engine.controller.model.request.RegisterUserRequest
import engine.model.User
import engine.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/api/register")
    fun registerUser(
        @RequestBody @Valid request: RegisterUserRequest
    ): ResponseEntity<Any> {

        if (userService.findUserByEmail(request.email) != null) {
            return ResponseEntity.badRequest().build()
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            authority = "ROLE_USER"
        )

        userService.createUser(user)

        return ResponseEntity.ok().build()
    }
}
