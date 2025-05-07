package engine.controller

import engine.controller.model.RegisterUserRequest
import engine.model.User
import engine.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class UserController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/api/register")
    // TODO
    fun registerUser(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<Any> {

        if (!Regex(".+@.+\\..+").matches(request.email)) {
            return ResponseEntity.badRequest().build()
        }

        if (userService.findUserByEmail(request.email) != null) {
            return ResponseEntity.badRequest().build()
        }

        if (request.password.length < 5) {
            return ResponseEntity.badRequest().build()
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = "USER"
        )

        userService.createUser(user)

        return ResponseEntity.ok().build()
    }
}
