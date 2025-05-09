package engine.controller.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        e: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }
}
