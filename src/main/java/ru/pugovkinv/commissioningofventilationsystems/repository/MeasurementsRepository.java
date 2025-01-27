package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {

        //@Modifying
        //@Query("SELECT * FROM measurements WHERE measurement.pointId = points.pointId")
        List<Measurements> findAllByPointId(Point pointId);
}
