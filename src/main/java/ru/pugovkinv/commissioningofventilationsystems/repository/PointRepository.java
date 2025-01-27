package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByVentilationSystem(VentilationSystem ventilationSystem);

}
