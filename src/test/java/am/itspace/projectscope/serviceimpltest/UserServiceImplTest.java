package am.itspace.projectscope.serviceimpltest;

import static org.mockito.BDDMockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.exceptions.ConflictException;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.repo.ProjectRepo;
import am.itspace.projectscope.repo.UserRepo;
import am.itspace.projectscope.service.serviceimpl.UserServiceImpl;
import org.junit.Assume;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProjectRepo projectRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void when_save_user_with_unique_email_and_password_should_be_done_successfully() {
        UserEntity el = new UserEntity();
        el.setEmail("a7@mail.ru");
        el.setPassword("a7");
        el.setUserName("a7");
        el.setType(Type.MEMBER);
        given(userRepo.existsByEmail(el.getEmail())).willReturn(false);
        given(userRepo.existsByEmail(el.getEmail())).willReturn(false);
        Mockito.when(userRepo.save(any(UserEntity.class))).thenReturn(el);
        UserEntity userEntity1 = userService.save(el);
        assertEquals(userEntity1, el);
    }

    @Test
    public void when_save_user_with_existing_email_it_throw_exception() {
        Assertions.assertThrows(ConflictException.class, () -> {
            UserEntity el = new UserEntity();
            el.setEmail("a7@mail.ru");
            el.setPassword("a7");
            el.setUserName("a7");
            el.setType(Type.MEMBER);
            given(userRepo.existsByEmail(el.getEmail())).willReturn(true);
            userService.save(el);
        });
        verify(userRepo, never()).save(any(UserEntity.class));
    }

//    @Test
//    public void when_save_user_with_existing_password_it_throw_exception() {
//        Assertions.assertThrows(ConflictException.class, () -> {
//            UserEntity el = new UserEntity();
//            el.setEmail("a7@mail.ru");
//            el.setPassword("a7");
//            el.setUserName("a7");
//            el.setType(Type.MEMBER);
//            given(userRepo.existsByPassword(el.getPassword())).willReturn(true);
//            userService.save(el);
//        });
//        verify(userRepo, never()).save(any(UserEntity.class));
//    }

    @Test
    public void when_save_user_it_should_be_done_successfully() {
        UserEntity el = new UserEntity();
        el.setEmail("a7@mail.ru");
        el.setPassword("a7");
        el.setUserName("a7");
        el.setType(Type.MEMBER);
        given(userRepo.save(el)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        UserEntity userEntity1 = userService.save(el);
        Assume.assumeNotNull(userEntity1);
        verify(userRepo).save(any(UserEntity.class));
    }



//    @Test
//    public void update_user_test() {
//        UserEntity el = new UserEntity();
//        el.setEmail("a7@mail.ru");
//        el.setPassword("a7");
//        el.setUserName("a7");
//        el.setType(Type.MEMBER);
//        el.setUserId(1);
//        given(userRepo.save(el)).willReturn(el);
//        UserEntity userEntity1 = userService.updateUser(el);
//        Assume.assumeNotNull(userEntity1);
//        verify(userRepo).save(any(UserEntity.class));
//    }


    @Test
    public void get_by_id_test() {
        UserEntity el = new UserEntity();
        el.setEmail("a7@mail.ru");
        el.setPassword("a7");
        el.setUserName("a7");
        el.setType(Type.MEMBER);
        el.setUserId(1);
        given(userRepo.findById(el.getUserId())).willReturn(Optional.of(el));
        final Optional<UserEntity> expected = userService.getById(1);
        Assume.assumeNotNull(expected);
    }


    @Test
    public void exist_by_email_test() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("ashs@mail.ru");
        given(userRepo.existsByEmail(userEntity.getEmail())).willReturn(true);
        Boolean expected = userService.existsByEmail(userEntity.getEmail());
        assertEquals(expected, true);
    }

//    @Test
//    public void exist_by_password_test() {
//        String password = "Ahd@12";
//        given(userRepo.existsByPassword(password)).willReturn(true);
//        Boolean expected = userService.existsByPassword(password);
//        assertEquals(expected, true);
//    }

//    @Test
//    public void get_user_by_email_and_password_test() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail("asas@mail.ru");
//        userEntity.setPassword("@Ajdfsj12");
//        given(userRepo.getUserByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword())).willReturn(userEntity);
//        UserEntity excepted = userService.getUserByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword());
//        assertEquals(excepted.getEmail(), userEntity.getEmail());
//        assertEquals(excepted.getPassword(), userEntity.getPassword());
//
//    }

//    @Test
//    public void exist_by_email_and_password_test() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail("asas@mail.ru");
//        userEntity.setPassword("@Ajdfsj12");
//        given(userRepo.existsByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword())).willReturn(true);
//        Boolean excepted = userService.existsUserByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword());
//        assertEquals(excepted, true);
//    }


}
