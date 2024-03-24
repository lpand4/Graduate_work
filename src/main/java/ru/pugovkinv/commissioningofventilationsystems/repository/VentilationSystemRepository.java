package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;

public interface VentilationSystemRepository extends JpaRepository<VentilationSystem, Long> {
}
