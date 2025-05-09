package engine.controller.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        e: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        val servletRequest = (request as? ServletWebRequest)?.request
        val uri = servletRequest?.requestURI

        return if (uri == "/actuator/shutdown") {
            ResponseEntity.ok().build() // Only return OK for this endpoint
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
