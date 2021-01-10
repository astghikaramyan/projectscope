package am.itspace.projectscope.controller;

import am.itspace.projectscope.dto.UserDto;
import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.exceptions.BadRequestException;
import am.itspace.projectscope.exceptions.ConflictException;
import am.itspace.projectscope.mapper.UserMapper;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.responsemodel.Project;
import am.itspace.projectscope.service.UserService;
import am.itspace.projectscope.util.EncriptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = this.userMapper.toEntity(userDto);
        if (userService.existsByEmail(userEntity.getEmail())) {
            logger.warn("User with {} email already exists.", userDto.getEmail());
            throw new ConflictException("Email address already taken");
        }
        if (userService.existsByPassword(userEntity.getPassword())) {
            logger.warn("User with {} password already exists.", userDto.getPassword());
            throw new ConflictException("Password already in use ");
        }
        userEntity.setPassword(EncriptionUtil.encrypt(userDto.getPassword()));
        userEntity = this.userService.save(userEntity);
        if (userEntity != null) {
            logger.info("User was saved successfully. ");
            return ResponseEntity.ok(userMapper.toDto(userEntity));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestParam String email, @RequestParam String password) {
        Boolean exists = userService.existsUserByEmailAndPassword(email, password);
        if (!exists) {
            logger.warn("User with {} email and {} password does not exist.", email, password);
            throw new BadRequestException("User with such email and password doesn't exist");
        }
        UserEntity userEntity = userService.getUserByEmailAndPassword(email, password);
        logger.info("Fetching a user with {} email and {} password does not exist.", email, password);
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllByMemberType() {
        Set<UserEntity> userEntities = this.userService.getAllByType(Type.MEMBER);
        if (userEntities != null) {
            logger.info("Fetching members. ");
            return ResponseEntity.ok(this.userMapper.toDtoList(new ArrayList<>(userEntities)));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/type")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId, @RequestParam Integer leaderId) {
        Optional<UserEntity> userEntity = this.userService.getById(leaderId);
        if (userEntity.isPresent() && userEntity.get().getType() == Type.LEADER) {
            UserEntity userEntity1 = this.userMapper.toEntity(userDto);
            userEntity1.setUserId(userId);
            userEntity1 = userService.updateUser(userEntity1);
            logger.info("User with {} id successfully", userId);
            return ResponseEntity.ok(userMapper.toDto(userEntity1));
        } else {
            throw new ConflictException("User with leader type can change other user's details. ");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Project>> getProjectsInfo(@PathVariable Integer userId) {
        List<Project> projects = userService.getProjectsInfo(userId);
        if (projects != null) {
            return ResponseEntity.ok(projects);
        }
        return ResponseEntity.notFound().build();
    }

    //I should check this method correct work.
    @GetMapping("/{userId}/filteredByDate")
    public ResponseEntity<List<Project>> getProjectsFilteredByDate(@PathVariable Integer userId, @RequestParam String dateFrom, @RequestParam String dateTo) {
        String[] dateFromArray = dateFrom.split("/");
        String[] dateToArray = dateFrom.split("/");
        LocalDate dateF = LocalDate.of(Integer.valueOf(dateFromArray[2]), Integer.valueOf(dateFromArray[1]), Integer.valueOf(dateFromArray[0]));
        LocalDate dateT = LocalDate.of(Integer.valueOf(dateToArray[2]), Integer.valueOf(dateToArray[1]), Integer.valueOf(dateToArray[0]));
        List<Project> projects = userService.getProjectsFilteredInfo(userId, dateF, dateT);
        if (projects != null) {
            return ResponseEntity.ok(projects);
        }
        return ResponseEntity.notFound().build();
    }

    //I should check this method correct work.
    @GetMapping("/{userId}/filteredByName")
    public ResponseEntity<List<Project>> getProjectsInfo(@PathVariable Integer userId, @RequestParam String name) {
        List<Project> projects = userService.getProjectsFilteredInfoByName(userId, name);
        if (projects != null) {
            return ResponseEntity.ok(projects);
        }
        return ResponseEntity.notFound().build();
    }
}
