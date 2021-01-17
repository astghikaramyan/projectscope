package am.itspace.projectscope.service;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LogService {
    Optional<LogEntity> getById(Integer id);

    Set<LogEntity> getAll();

    LogEntity save(LogEntity logEntity);

    LogEntity updateLog(LogEntity logEntity);

    List<LogEntity> findAllByUserId(UserEntity userEntity);
}
