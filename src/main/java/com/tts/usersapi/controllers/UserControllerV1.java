package com.tts.usersapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.usersapi.models.User;
import com.tts.usersapi.repositories.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value="users", description="Operations to view/create/update/delete users")
//slide 22 has code for this
@RequestMapping("/v1")
public class UserControllerV1
{
    @Autowired
    UserRepository repository;
    
    @ApiOperation(value = "Get all users", response = User.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users"),
            })
    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(value="state", required=false) String state)
    {
        if (state !=null)
        {
            return repository.findByState(state);
        }
        return repository.findAll();
    }
    
    @ApiOperation(value = "Get a single user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "User wasn't found")
            })
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value="id")Long id)
    {
        return repository.findById(id);
    }
   
    @ApiOperation(value = "Create a user", response = Void.class)
    //in postman, use raw and JSON. building request to endpoint Postman. passing in user information
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created user"),
            @ApiResponse(code = 400, message = "Bad request formatting or user already exisits")
            })
    @PostMapping("/users")
    public void createUser(@RequestBody User user)
    {
        repository.save(user);
    }
    
    @ApiOperation(value = "Update a user", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 400, message = "Bad request formmatting"),
            @ApiResponse(code = 404, message = "User id not found")
            })
    @PutMapping("/users/{id}")
    public void createUser(@PathVariable(value="id")Long id, @RequestBody User user)
    {
        repository.save(user);
    }
    
    @ApiOperation(value = "Delete a user", response = Void.class)
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully deleted user"),
    @ApiResponse(code = 404, message = "User id not found")
    })
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value="id") Long id)
    {
        repository.deleteById(id);
    }
}
