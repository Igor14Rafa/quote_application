package com.example.stockmanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.stockmanager.model.Host;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface HostRepository extends CrudRepository<Host, String> {

}
