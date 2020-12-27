package br.avcaliani.dmesh.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: Long = 0,
    val username: String = "",
    val description: String = "",
    @JsonIgnore
    var password: String = ""
)