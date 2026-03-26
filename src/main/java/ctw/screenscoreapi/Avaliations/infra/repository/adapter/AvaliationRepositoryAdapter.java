package ctw.screenscoreapi.Avaliations.infra.repository.adapter;

import ctw.screenscoreapi.Avaliations.domain.AvaliationEntity;
import ctw.screenscoreapi.Avaliations.domain.AvaliationRepository;
import ctw.screenscoreapi.Avaliations.infra.repository.dao.AvaliationSpringJdbcDao;
import org.springframework.stereotype.Repository;

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
}
