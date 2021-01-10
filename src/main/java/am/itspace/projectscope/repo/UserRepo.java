package am.itspace.projectscope.repo;

import am.itspace.projectscope.entity.ProjectEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.model.Type;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);

    Boolean existsByEmailAndPassword(String email, String pass);

    List<UserEntity> findAllByTypeEquals(Type type);

    @Query(
            value = "SELECT up.project_id FROM user_project up WHERE up.user_id=:userId",
            nativeQuery = true)
    List<Integer> findAllByUserIdQuery(Integer userId);


}
