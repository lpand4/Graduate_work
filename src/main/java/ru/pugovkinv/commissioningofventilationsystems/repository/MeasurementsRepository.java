package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;

public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
}
