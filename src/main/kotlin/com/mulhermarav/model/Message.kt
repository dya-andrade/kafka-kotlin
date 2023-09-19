package com.mulhermarav.model

import jakarta.persistence.*

@Entity
@Table(name = "message")
class Message (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "message", nullable = false, length = 80)
    var message: String = ""
)