package com.tts.usersapi.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value="users", description="Operations to view/create/update/delete users")
//slide 22 has code for this
@RequestMapping("/v2")
public class UserControllerV2  //put a copy of this file in ECommerceDUMMY project
{
    @Autowired
    UserRepository repository;
    
 // If user passes in query parameter for state, we find the user by state...
    // If user passes in no value, we find all users
    @ApiOperation(value = "Get all users, filtered by state", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users"),
            @ApiResponse(code = 400, message = "Bad request, state parameter missing")
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=true) String state)
    {
        List<User> users;
        if(state == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        users = repository.findByState(state);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get a single user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") Long id)
    {
        Optional<User> user = repository.findById(id);
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Create a user", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created user"),
            @ApiResponse(code = 400, message = "Bad request formatting or user exists")
    })
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult)
    {
        if(repository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName()).size() != 0) {
            bindingResult.rejectValue("id", "error.id", "User id aleady exists");
        }
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Update a user", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 400, message = "Bad request formatting"),
            @ApiResponse(code = 404, message = "User id not found")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value="id") Long id, @RequestBody @Valid User user, BindingResult bindingResult)
    {
        if(repository.findById(user.getId()) == null) {
            bindingResult.rejectValue("id", "error.id", "User id not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiOperation(value = "Delete a user", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully"),
            @ApiResponse(code = 404, message = "User id not found")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value="id") Long id, @RequestBody @Valid User user, BindingResult bindingResult)
    {
        if(repository.findById(user.getId()) == null) {
            bindingResult.rejectValue("id", "error.id", "User id not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
