package am.itspace.projectscope.service.serviceimpl;

import am.itspace.projectscope.entity.LogEntity;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.repo.LogRepo;
import am.itspace.projectscope.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepo logRepo;

    @Override
    public Optional<LogEntity> getById(Integer id) {
        return logRepo.findById(id);
    }

    @Override
    public Set<LogEntity> getAll() {
        Set<LogEntity> logEntities = new HashSet<>();
        List<LogEntity> logEntityList = logRepo.findAll();
        if (logEntityList != null) {
            logEntities.addAll(logEntityList);
            return logEntities;
        }
        return null;
    }

    @Override
    public LogEntity save(LogEntity logEntity) {
        if (logEntity != null)
            return logRepo.save(logEntity);
        return null;
    }

    @Override
    public LogEntity updateLog(LogEntity logEntity) {
        if (logRepo.findById(logEntity.getLogId()).isPresent())
            return logRepo.save(logEntity);
        return null;
    }

    @Override
    public List<LogEntity> findAllByUserId(UserEntity userEntity) {
        if(userEntity != null){
            return this.logRepo.findAllByUserEntity(userEntity);
        }
        return null;
    }
}
