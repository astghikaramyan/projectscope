package am.itspace.projectscope.controller;

import am.itspace.projectscope.dto.ProjectDto;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.exceptions.ConflictException;
import am.itspace.projectscope.mapper.ProjectMapper;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.service.ProjectService;
import am.itspace.projectscope.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional
    @PostMapping("/{usersIds}")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto,
                                                 @PathVariable String usersIds, @RequestParam String date, @RequestParam String deadline, @RequestParam Integer leaderId) {
        Optional<UserEntity> userEntityL = userService.getById(leaderId);
        if (!userEntityL.isPresent()) {
            throw new ConflictException("Only  users with leader role can add project. ");
        }
        ProjectEntity projectEntity = this.projectMapper.toEntity(projectDto);
        String[] d = date.split("/");
        String[] dl = deadline.split("/");
        projectEntity.setDate(LocalDate.of(Integer.valueOf(d[2]), Integer.valueOf(d[1]), Integer.valueOf(d[0])));
        projectEntity.setDeadline(LocalDate.of(Integer.valueOf(dl[2]), Integer.valueOf(dl[1]), Integer.valueOf(dl[0])));
        Set<UserEntity> userEntities = new HashSet<>();
        String[] userIds = usersIds.split(",");
        for (int i = 0; i < userIds.length; i++) {
            Optional<UserEntity> userEntity = this.userService.getById(Integer.valueOf(userIds[i]));
            if (userEntity.isPresent()) {
                userEntities.add(userEntity.get());
            }
        }
        projectEntity.setUserEntities(userEntities);
        projectEntity = this.projectService.save(projectEntity);


        if (userEntities != null) {
            for (UserEntity u : userEntities) {
                Set<ProjectEntity> projectEntities = new HashSet<>();
                projectEntities.add(projectEntity);
                if (!u.getProjectEntities().isEmpty()) {
                    projectEntities.addAll(u.getProjectEntities());
                    u.getProjectEntities().clear();
                    u.setProjectEntities(projectEntities);
                } else {
                    u.setProjectEntities(projectEntities);
                }
            }
        }
        return ResponseEntity.ok(projectMapper.toDto(projectEntity));
    }


    @Transactional
    @DeleteMapping("/{projectId}/type")
    public ResponseEntity<?> deleteProject(@PathVariable Integer projectId, @RequestParam Integer leaderId) {
        Optional<UserEntity> userEntity = userService.getById(leaderId);
        if (userEntity.isPresent() && userEntity.get().getType() == Type.LEADER) {
            projectService.delete(projectId);
            return ResponseEntity.ok().build();
        } else {
            throw new ConflictException("Only leader can delete project. ");
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<ProjectDto>> getAllByUserId(@PathVariable Integer userId) {
        List<ProjectEntity> projectEntities = this.userService.getUserProjects(userId);
        if (projectEntities != null) {
            return ResponseEntity.ok(this.projectMapper.toDtoList(new ArrayList<>(projectEntities)));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{projectIds}")
    public ResponseEntity<?> deleteAllByIds(@PathVariable String projectIds, @RequestParam Integer leaderId) {
        Optional<UserEntity> userEntity = userService.getById(leaderId);
        if (userEntity.isPresent() && userEntity.get().getType() == Type.LEADER) {
            String[] prIds = projectIds.split(",");
            List<Integer> list = new LinkedList<>();
            for(String e : prIds){
                list.add(Integer.valueOf(e));
            }
            projectService.deleteAll(list);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
