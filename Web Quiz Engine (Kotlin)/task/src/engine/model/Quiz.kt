package engine.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Quiz(

    val title: String,

    val text: String,

    val options: List<String>,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val answer: List<Int>,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val authorEmail: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)
