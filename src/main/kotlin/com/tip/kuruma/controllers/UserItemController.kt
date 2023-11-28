package com.tip.kuruma.controllers

import com.tip.kuruma.dto.UserItemDTO
import com.tip.kuruma.services.UserItemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user_items")
@CrossOrigin
class UserItemController @Autowired constructor(
    private val userItemSerivce: UserItemService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserItemService::class.java)
    }

    @GetMapping
    fun getAllUserItems(): ResponseEntity<List<UserItemDTO>> {
        LOGGER.info("Calling to GET /user_items")
        val user_items = userItemSerivce.getAllUserItems()
        return ResponseEntity.ok(UserItemDTO.fromUserItems(user_items))
    }

    @GetMapping("/user/{userId}")
    fun getAllUserItemsByUserId(@PathVariable userId: Long): ResponseEntity<List<UserItemDTO>> {
        val user_items = userItemSerivce.getAllUserItemsByUserId(userId)

        return ResponseEntity.ok(UserItemDTO.fromUserItems(user_items))
    }

    @PostMapping
    fun createUserItem(@RequestBody userItemDTO: UserItemDTO): ResponseEntity<UserItemDTO> {
        LOGGER.info("Calling to POST /user_items with request $userItemDTO")
        val savedUserItem = userItemSerivce.saveUserItem(userItemDTO.toUserItem())
        return ResponseEntity.status(HttpStatus.CREATED).body(UserItemDTO.fromUserItem(savedUserItem))
    }

    @GetMapping("/{id}")
    fun getUserItemByID(@PathVariable id: Long): ResponseEntity<UserItemDTO> {
        LOGGER.info("Calling to GET /user_items/$id")
        val userItem = userItemSerivce.getUserItemById(id)
        return ResponseEntity.ok(userItem.let { UserItemDTO.fromUserItem(it) })
    }

    @PutMapping("/{id}")
    fun updateUserItem(@PathVariable id: Long,@RequestBody userItemDTO: UserItemDTO): ResponseEntity<UserItemDTO> {
        LOGGER.info("Calling to PUT /user_items/$id with request $userItemDTO")
        val userItem = userItemSerivce.updateUserItem(id, userItemDTO.toUserItem())
        return ResponseEntity.ok(UserItemDTO.fromUserItem(userItem))
    }

    @DeleteMapping("/{id}")
    fun deleteUserItem(@PathVariable id: Long): ResponseEntity<Unit> {
        LOGGER.info("Calling to DELETE /user_items/$id")
        userItemSerivce.deleteUserItem(id)
        return ResponseEntity.noContent().build()
    }
}