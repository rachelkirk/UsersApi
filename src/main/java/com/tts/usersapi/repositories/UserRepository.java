package com.tts.usersapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.usersapi.models.User;


@Repository
    public interface UserRepository extends CrudRepository<User, Long>
    {
        public List<User> findByState(String state);
        
        @Override
        public List<User> findAll();
        //if you do this spring will guarantee it returns a list. try to avoid cast whenever possible.
    }

