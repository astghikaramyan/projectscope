package am.itspace.projectscope.repo;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends JpaRepository<LogEntity, Integer> {
    List<LogEntity> findAllByUserEntity(UserEntity userEntity);
}
