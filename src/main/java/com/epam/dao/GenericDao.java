/*
package com.epam.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
TODO unusable class we can remove it
public class GenericDao<T> {

    private final Map<String, Map<Integer, T>> storage = new HashMap<>();

    public void save(String namespace, Integer id, T entity) {
        storage.computeIfAbsent(namespace, k -> new HashMap<>()).put(id, entity);
        log.info("Saved entity with ID {} in namespace {}.", id, namespace);

    }

    public T findById(String namespace, Integer id) {
        T entity = storage.getOrDefault(namespace, new HashMap<>()).get(id);
        if (entity != null) {
            log.info("Found entity with ID {} in namespace {}.", id, namespace);
        } else {
            log.info("Entity with ID {} not found in namespace {}.", id, namespace);
        }
        return entity;
    }

    public Map<Integer, T> findAll(String namespace) {
        Map<Integer, T> entities = storage.getOrDefault(namespace, new HashMap<>());
        log.info("Retrieved all entities in namespace {}.", namespace);

        return entities;
    }

    public void update(String namespace, Integer id, T updatedEntity) {

        Map<Integer, T> entityMap = storage.get(namespace);
        if (entityMap != null && entityMap.containsKey(id)) {
            entityMap.put(id, updatedEntity);
            log.info("Updated entity with ID {} in namespace {}.", id, namespace);
        } else {
            log.warn("Entity with ID {} not found in namespace {}. Update operation ignored.", id, namespace);
        }

    }

    public void delete(String namespace, Integer id) {
        Map<Integer, T> entityMap = storage.get(namespace);
        if (entityMap != null) {
            entityMap.remove(id);
            log.info("Deleted entity with ID {} in namespace {}.", id, namespace);
        } else {
            log.warn("Entity with ID {} not found in namespace {}. Deletion operation ignored.", id, namespace);
        }
    }
    public void clearStorage() {
        storage.clear();
        log.info("Data storage has been reset.");
    }
}

*/
