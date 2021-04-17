package com.tts.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The user id")
    Long id;
    
    @Length(max = 20, message = "Your first name cannot be longer than 20 characters")
    @ApiModelProperty(notes = "The first name of the user.")
    String firstName;
    
    @Length(min = 2, message = "Your last name must have at least 2 characters")
    @ApiModelProperty(notes = "The last name of the user.")
    String lastName;
    
    @Length(min = 2, message = "Your state must have at least 4 characters")
    @Length(max = 20, message = "Your state cannot have more than 20 characters")
    @ApiModelProperty(notes = "The location of the user.")
    String state; //of residence
    
}