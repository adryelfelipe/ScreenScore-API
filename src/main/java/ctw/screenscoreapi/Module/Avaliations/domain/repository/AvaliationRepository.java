package ctw.screenscoreapi.Module.Avaliations.domain.repository;

import ctw.screenscoreapi.Module.Avaliations.domain.entity.AvaliationEntity;

import java.util.List;
import java.util.Optional;

public interface AvaliationRepository {
    long create(AvaliationEntity avaliation);
    Optional<AvaliationEntity> findById(long id);
    long deleteById(long id);
    List<AvaliationEntity> findAll();
    void update(AvaliationEntity avaliation);
}
