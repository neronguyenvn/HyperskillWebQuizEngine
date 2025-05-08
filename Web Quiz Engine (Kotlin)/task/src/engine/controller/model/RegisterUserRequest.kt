package engine.controller.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class RegisterUserRequest(

    @field:Email(regexp = ".+@.+\\..+")
    val email: String,

    @field:Size(min = 5)
    val password: String
)
