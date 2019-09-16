package com.stavros.kotlinrestjpa.repository

import com.stavros.kotlinrestjpa.model.Poem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PoemRepo : JpaRepository<Poem , Long>