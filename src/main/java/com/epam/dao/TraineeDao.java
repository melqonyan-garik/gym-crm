package com.epam.dao;

import com.epam.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDao extends GenericDao<Trainee> {
    public static final String TRAINEES = "Trainees";

    @Override
    public void save(String namespace, Integer id, Trainee entity) {
        super.save(TRAINEES, id, entity);
    }

    @Override
    public Trainee findById(String namespace, Integer id) {
        return super.findById(TRAINEES, id);
    }

    @Override
    public Map<Integer, Trainee> findAll(String namespace) {
        return super.findAll(TRAINEES);
    }

    @Override
    public void update(String namespace, Integer id, Trainee updatedEntity) {
        super.update(TRAINEES, id, updatedEntity);
    }

    @Override
    public void delete(String namespace, Integer id) {
        super.delete(TRAINEES, id);
    }
}

