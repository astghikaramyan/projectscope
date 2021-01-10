package am.itspace.projectscope.repo;

import am.itspace.projectscope.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository<LogEntity, Integer> {
}
