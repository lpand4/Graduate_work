package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
        List<Measurements> findAll(Point point);
}
