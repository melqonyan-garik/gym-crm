package com.epam.dao;

import com.epam.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDao extends GenericDao<Trainer> {

    public static final String TRAINERS = "Trainers";

    @Override
    public void save(String namespace, Integer id, Trainer entity) {
        super.save(namespace, id, entity);
    }

    @Override
    public Trainer findById(String namespace, Integer id) {
        return super.findById(namespace, id);
    }

    @Override
    public Map<Integer, Trainer> findAll(String namespace) {
        return super.findAll(namespace);
    }

    @Override
    public void update(String namespace, Integer id, Trainer updatedEntity) {
        super.update(namespace, id, updatedEntity);
    }

    @Override
    public void delete(String namespace, Integer id) {
        super.delete(namespace, id);
    }
}
