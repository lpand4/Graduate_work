package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
