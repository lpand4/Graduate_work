package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;

public interface PlaceOfWorkRepository extends JpaRepository<PlaceOfWork, Long> {
}
