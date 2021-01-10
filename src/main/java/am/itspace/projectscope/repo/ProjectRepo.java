package am.itspace.projectscope.repo;

import am.itspace.projectscope.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepo extends JpaRepository<ProjectEntity, Integer> {

}
