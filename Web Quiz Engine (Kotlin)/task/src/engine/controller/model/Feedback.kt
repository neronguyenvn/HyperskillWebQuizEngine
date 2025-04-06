package engine.controller.model

data class Feedback(
    val success: Boolean,
    val feedback: String
) {
    companion object {
        val wrongAnswer = Feedback(
            success = false,
            feedback = "Wrong answer! Please, try again."
        )
        val trueAnswer = Feedback(
            success = true,
            feedback = "Congratulations, you're right!"
        )
    }
}

