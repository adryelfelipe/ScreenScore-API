package ctw.screenscoreapi.Module.Avaliations.domain;

import java.util.List;
import java.util.Optional;

public interface AvaliationRepository {
    long create(AvaliationEntity avaliation);
    Optional<AvaliationEntity> findById(long id);
    long deleteById(long id);
    List<AvaliationEntity> findAll();
}
