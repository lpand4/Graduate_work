package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;

import java.util.List;

@Repository
public interface VentilationSystemRepository extends JpaRepository<VentilationSystem, Long> {
    List<VentilationSystem> findAll(PlaceOfWork placeOfWork);
}
