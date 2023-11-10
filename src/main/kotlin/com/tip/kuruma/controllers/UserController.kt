package com.tip.kuruma.controllers

import com.tip.kuruma.dto.UserDTO
import com.tip.kuruma.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
    private val userService: UserService
){
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUsers()

        return ResponseEntity.ok(UserDTO.fromUsers(users))
    }

//    @PostMapping
//    fun createUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
//        val savedUser = userService.saveUser(userDTO.toUser())
//        return ResponseEntity.status(201).body(UserDTO.fromUser(savedUser))
//    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(UserDTO.fromUser(user))
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userService.updateUser(id, userDTO.toUser())
        return ResponseEntity.ok(UserDTO.fromUser(user))
    }

    @PatchMapping("/{id}")
    fun patchUser(@PathVariable id: Long, @RequestBody partialUserDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userService.patchUser(id, partialUserDTO.toUser())
        return ResponseEntity.ok(UserDTO.fromUser(user))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
