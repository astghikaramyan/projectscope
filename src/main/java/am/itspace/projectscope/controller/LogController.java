package am.itspace.projectscope.controller;

import am.itspace.projectscope.dto.LogDto;
import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.mapper.LogMapper;
import am.itspace.projectscope.service.LogService;
import am.itspace.projectscope.service.ProjectService;
import am.itspace.projectscope.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LogMapper logMapper;


    @PostMapping
    @Transactional
    public ResponseEntity<LogDto> addLog(@RequestBody LogDto logDto, @RequestParam Integer userId, @RequestParam Integer projectId) {
        Optional<UserEntity> userEntity = this.userService.getById(userId);
        Optional<ProjectEntity> projectEntity = projectService.getById(projectId);

        LogEntity logEntity = this.logMapper.toEntity(logDto);
        LocalDateTime localDateTime = LocalDateTime.now();
        logEntity.setStartDateTime(localDateTime);
        logEntity.setEndDateTime(localDateTime);
        logEntity.setHours(logEntity.getEndDateTime().getHour() - logEntity.getStartDateTime().getHour());


        if (userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();
            logEntity.setUserEntity(userEntity1);
        }
        if (projectEntity.isPresent()) {
            ProjectEntity projectEntity1 = projectEntity.get();
            logEntity.setProjectEntity(projectEntity1);
        }
        logEntity = logService.save(logEntity);

        if (userEntity.isPresent()) {
            Set<LogEntity> logEntitySet = userEntity.get().getLogEntities();
            Set<LogEntity> set = new HashSet<>();
            set.add(logEntity);
            if (!logEntitySet.isEmpty()) {
                set.addAll(logEntitySet);
                userEntity.get().getLogEntities().clear();
                userEntity.get().setLogEntities(set);
            } else {
                userEntity.get().setLogEntities(set);
            }
        }
        if (projectEntity.isPresent()) {
            Set<LogEntity> projectLogs = projectEntity.get().getLogEntities();
            Set<LogEntity> logEntities = new HashSet<>();
            logEntities.add(logEntity);
            if (!projectLogs.isEmpty()) {
                logEntities.addAll(projectLogs);
                projectEntity.get().getLogEntities().clear();
                projectEntity.get().setLogEntities(logEntities);
            } else {
                projectEntity.get().setLogEntities(logEntities);
            }
        }
        return ResponseEntity.ok(logMapper.toDto(logEntity));
    }

    @PatchMapping("/startLogTime/{logId}")
    public ResponseEntity<LogDto> updateStartLogTime(@PathVariable Integer logId) {
        Optional<LogEntity> logEntity1 = logService.getById(logId);
        if (logEntity1.isPresent()) {
            if (logEntity1.get().getStartDateTime().getDayOfMonth()
                    != LocalDateTime.now().getDayOfMonth()) {
                logEntity1.get().setStartDateTime(LocalDateTime.now());
                logEntity1.get().setEndDateTime(LocalDateTime.now());
                LogEntity logEntity = logService.updateLog(logEntity1.get());
                return ResponseEntity.ok(logMapper.toDto(logEntity));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/endLogTime/{logId}")
    public ResponseEntity<LogDto> updateEndLogTime(@PathVariable Integer logId) {
        Optional<LogEntity> logEntity1 = logService.getById(logId);
        if (logEntity1.isPresent()) {
            if (logEntity1.get().getStartDateTime().getDayOfMonth()
                    == logEntity1.get().getEndDateTime().getDayOfMonth()) {
                logEntity1.get().setEndDateTime(LocalDateTime.now());
                int hours = logEntity1.get().getEndDateTime().getHour() - logEntity1.get().getStartDateTime().getHour();
                logEntity1.get().setHours(logEntity1.get().getHours() + hours);
                LogEntity logEntity = logService.updateLog(logEntity1.get());
                return ResponseEntity.ok(logMapper.toDto(logEntity));
            }
        }
        return ResponseEntity.notFound().build();
    }
}
