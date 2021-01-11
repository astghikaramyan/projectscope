package am.itspace.projectscope.service.serviceimpl;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.repo.ProjectRepo;
import am.itspace.projectscope.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Optional<ProjectEntity> getById(Integer id) {

        return projectRepo.findById(id);
    }

    @Override
    public Set<ProjectEntity> getAll() {
        Set<ProjectEntity> projectEntities = new HashSet<>();
        List<ProjectEntity> projectEntities1 = projectRepo.findAll();
        if (projectEntities1 != null) {
            projectEntities.addAll(projectEntities1);
            return projectEntities;
        }
        return null;
    }

    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        if (projectEntity != null)
            return projectRepo.save(projectEntity);

        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(id);
        if (projectEntity.isPresent()) {
            Set<UserEntity> userEntities = new HashSet<>();
            Set<LogEntity> logEntities = new HashSet<>();
            userEntities.addAll(projectEntity.get().getUserEntities());
            logEntities.addAll(projectEntity.get().getLogEntities());
            projectEntity.get().getLogEntities().clear();
            projectEntity.get().getUserEntities().clear();
            if(userEntities != null){
                for(UserEntity u : userEntities){
                    Set<ProjectEntity> set = new HashSet<>();
                    Set<ProjectEntity> set1 = u.getProjectEntities();
                    set1.remove(projectEntity.get());
                    set.addAll(set1);
                    u.getProjectEntities().clear();
                    u.getProjectEntities().addAll(set);
                }
            }
            projectRepo.deleteById(id);
        }
    }

    @Override
    public ProjectEntity updateProject(ProjectEntity projectEntity) {
        if (projectRepo.findById(projectEntity.getProjectId()).isPresent()) {
            return projectRepo.save(projectEntity);
        }
        return null;
    }

    @Override
    public void deleteAll(List<Integer> integerList) {
        if (!integerList.isEmpty()) {
            for (Integer i : integerList) {
                delete(i);
            }
        }
    }
}
