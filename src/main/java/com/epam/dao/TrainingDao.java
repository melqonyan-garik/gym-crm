package com.epam.dao;

import com.epam.model.Training;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDao extends GenericDao<Training>{

    public static final String TRAINING = "Training";

    @Override
    public void save(String namespace, Integer id, Training entity) {
        super.save(namespace, id, entity);
    }

    @Override
    public Training findById(String namespace, Integer id) {
        return super.findById(namespace, id);
    }

    @Override
    public Map<Integer, Training> findAll(String namespace) {
        return super.findAll(namespace);
    }

    @Override
    public void update(String namespace, Integer id, Training updatedEntity) {
        super.update(namespace, id, updatedEntity);
    }

    @Override
    public void delete(String namespace, Integer id) {
        super.delete(namespace, id);
    }
}
