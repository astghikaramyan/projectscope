package am.itspace.projectscope.service;

import am.itspace.projectscope.entity.ProjectEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectService {
    Optional<ProjectEntity> getById(Integer id);

    Set<ProjectEntity> getAll();

    ProjectEntity save(ProjectEntity projectEntity);

    void delete(Integer id);

    ProjectEntity updateProject(ProjectEntity projectEntity);

    void deleteAll(List<Integer> integerList);
}
