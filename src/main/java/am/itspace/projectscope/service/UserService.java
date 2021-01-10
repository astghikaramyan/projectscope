package am.itspace.projectscope.service;

import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.responsemodel.Project;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<UserEntity> getById(Integer id);

    Set<UserEntity> getAllByType(Type type);

    UserEntity save(UserEntity userEntity);

    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);

    Boolean existsUserByEmailAndPassword(String email, String password);

    UserEntity getUserByEmailAndPassword(String email, String password);

    UserEntity updateUser(UserEntity userEntity);

    List<ProjectEntity> getUserProjects(Integer id);

    List<Project> getProjectsInfo(Integer userId);

    List<Project> getProjectsFilteredInfo(Integer userId, LocalDate date, LocalDate deadline);

    List<Project> getProjectsFilteredInfoByName(Integer userId, String name);

    List<Project> getProjectsByUserId(Integer userId);
}
