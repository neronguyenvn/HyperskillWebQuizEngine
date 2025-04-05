package engine.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Quiz(
    val title: String,
    val text: String,
    val options: List<String>,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val answer: Int?,

    val id: Long? = null,
)
