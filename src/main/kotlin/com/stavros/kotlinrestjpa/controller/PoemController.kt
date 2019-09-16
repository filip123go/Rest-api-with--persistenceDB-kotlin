package com.stavros.kotlinrestjpa.controller

import com.stavros.kotlinrestjpa.model.Poem
import com.stavros.kotlinrestjpa.repository.PoemRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class PoemController(private val poemRepo: PoemRepo) {

    @GetMapping("/poems")
    fun getAllArticles(): List<Poem> = poemRepo.findAll()

    @PostMapping("/poems")
    fun createNewArticle(@Valid @RequestBody poem: Poem): Poem =
            poemRepo.save(poem)

    @GetMapping("/poems/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Poem> {
        return poemRepo.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/poems/{id}")
    fun updateArticleById(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newPoem: Poem): ResponseEntity<Poem> {

        return poemRepo.findById(articleId).map { existingArticle ->
            val updatedPoem: Poem = existingArticle
                    .copy(title = newPoem.title, content = newPoem.content)
            ResponseEntity.ok().body(poemRepo.save(updatedPoem))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/poems/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {

        return poemRepo.findById(articleId).map { article  ->
            poemRepo.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }


}