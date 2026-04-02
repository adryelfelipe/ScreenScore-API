package ctw.screenscoreapi.Module.Avaliations.infra.repository.adapter;

import ctw.screenscoreapi.Module.Avaliations.domain.AvaliationEntity;
import ctw.screenscoreapi.Module.Avaliations.domain.AvaliationRepository;
import ctw.screenscoreapi.Module.Avaliations.infra.repository.dao.AvaliationSpringJdbcDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AvaliationRepositoryAdapter implements AvaliationRepository {
    private AvaliationSpringJdbcDao avaliationSpringJdbcDao;

    public AvaliationRepositoryAdapter(AvaliationSpringJdbcDao avaliationSpringJdbcDao) {
        this.avaliationSpringJdbcDao = avaliationSpringJdbcDao;
    }

    @Override
    public long create(AvaliationEntity avaliation) {
        return avaliationSpringJdbcDao.create(avaliation);
    }

    @Override
    public Optional<AvaliationEntity> findById(long id) {
        return avaliationSpringJdbcDao.findById(id);
    }

    @Override
    public long deleteById(long id) {
        return avaliationSpringJdbcDao.deleteById(id);
    }

    @Override
    public List<AvaliationEntity> findAll() {
        return avaliationSpringJdbcDao.findAll();
    }
}
