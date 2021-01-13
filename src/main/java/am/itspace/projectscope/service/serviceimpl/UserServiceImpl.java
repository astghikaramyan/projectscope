package am.itspace.projectscope.service.serviceimpl;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.exceptions.ConflictException;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.repo.ProjectRepo;
import am.itspace.projectscope.repo.UserRepo;
import am.itspace.projectscope.responsemodel.Project;
import am.itspace.projectscope.service.UserService;
import am.itspace.projectscope.util.EncriptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Optional<UserEntity> getById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public Set<UserEntity> getAllByType(Type type) {
        Set<UserEntity> userEntities = new HashSet<>();
        List<UserEntity> userEntityList = userRepo.findAllByTypeEquals(Type.MEMBER);
        if (userEntityList != null) {
            userEntities.addAll(userEntityList);
            return userEntities;
        }
        return null;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity != null){
            if (existsByEmail(userEntity.getEmail())) {
                throw new ConflictException("Email address already taken");
            }
            if (existsByPassword(userEntity.getPassword())) {
                throw new ConflictException("Password already in use ");
            }
            return userRepo.save(userEntity);
        }
        return null;
    }

    @Override
    public Boolean existsByEmail(String email) {

        return userRepo.existsByEmail(email);
    }

    @Override
    public Boolean existsByPassword(String password) {
        password = EncriptionUtil.encrypt(password);
        return userRepo.existsByPassword(password);
    }

    @Override
    public Boolean existsUserByEmailAndPassword(String email, String password) {
        password = EncriptionUtil.encrypt(password);
        return userRepo.existsByEmailAndPassword(email, password);
    }

    @Override
    public UserEntity getUserByEmailAndPassword(String email, String password) {
        password = EncriptionUtil.encrypt(password);

        return userRepo.getUserByEmailAndPassword(email, password);

    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        if (userRepo.findById(userEntity.getUserId()).isPresent()) {
            return userRepo.save(userEntity);
        }
        return null;
    }

    @Override
    public List<ProjectEntity> getUserProjects(Integer id) {
        if (userRepo.findById(id).isPresent()) {
            List<Integer> prIds = userRepo.findAllByUserIdQuery(id);
            List<ProjectEntity> projectEntities = new LinkedList<>();
            for (Integer i : prIds) {
                Optional<ProjectEntity> projectEntity = projectRepo.findById(i);
                if (projectEntity.isPresent()) {
                    ProjectEntity projectEntity1 = projectEntity.get();
                    projectEntities.add(projectEntity1);
                }
            }
            return projectEntities;
        }
        return null;
    }

    @Override
    public List<Project> getProjectsByUserId(Integer userId) {
        List<ProjectEntity> projectEntities = getUserProjects(userId);
        if (projectEntities != null) {
            List<Project> projects = getProjects(projectEntities);
            return projects;
        }
        return null;
    }

    @Override
    public List<Project> getProjectsInfo(Integer userId) {
        List<ProjectEntity> list = getUserProjects(userId);
        if (!list.isEmpty()) {
            List<Project> p = getProjects(list);
            return p;
        }

        return null;
    }

    @Override
    public List<Project> getProjectsFilteredInfo(Integer userId, LocalDate dateFrom, LocalDate dateTo) {
        List<ProjectEntity> list = getUserProjects(userId);
        List<ProjectEntity> list2 = new LinkedList<>();
        if (!list.isEmpty()) {
            for (ProjectEntity p : list) {
                if (p.getDate().isAfter(dateFrom) && p.getDate().isBefore(dateTo)) {
                    list2.add(p);
                }
            }
            List<Project> projects = getProjects(list2);
            return projects;
        }
        return null;
    }

    @Override
    public List<Project> getProjectsFilteredInfoByName(Integer userId, String name) {
        List<ProjectEntity> list = getUserProjects(userId);
        List<ProjectEntity> list2 = new LinkedList<>();
        if (!list.isEmpty()) {
            for (ProjectEntity p : list) {
                if (p.getProjectName().equals(name)) {
                    list2.add(p);
                }
            }
            List<Project> projects = getProjects(list2);
            return projects;
        }
        return null;
    }

    private List<Project> getProjects(List<ProjectEntity> list) {
        List<Project> list1 = new LinkedList<>();
        for (ProjectEntity p : list) {
            Project project = new Project();
            project.setProjectId(p.getProjectId());
            project.setProjectName(p.getProjectName());
            project.setDate(p.getDate());
            project.setDeadline(p.getDeadline());
            StringJoiner stringJoiner = new StringJoiner(",");
            Set<UserEntity> userEntities = p.getUserEntities();
            for (UserEntity u : userEntities) {
                stringJoiner.add(u.getUserName());
            }
            project.setMembers(stringJoiner.toString());
            Set<LogEntity> logEntities = p.getLogEntities();
            int hours = 0;
            for (LogEntity l : logEntities) {
                hours += l.getHours();
            }
            project.setHours(hours);
            list1.add(project);
        }
        return list1;
    }


}
