package com.mulhermarav.repository

import com.mulhermarav.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, Long?> {
}