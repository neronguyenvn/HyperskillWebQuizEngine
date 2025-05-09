package engine.controller.model.response

data class FeedbackResponse(
    val success: Boolean,
    val feedback: String
) {
    companion object {
        val wrongAnswer = FeedbackResponse(
            success = false,
            feedback = "Wrong answer! Please, try again."
        )
        val trueAnswer = FeedbackResponse(
            success = true,
            feedback = "Congratulations, you're right!"
        )
    }
}

