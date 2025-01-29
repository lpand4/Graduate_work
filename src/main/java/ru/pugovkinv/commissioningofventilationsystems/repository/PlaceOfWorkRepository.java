package ru.pugovkinv.commissioningofventilationsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;

@Repository
public interface PlaceOfWorkRepository extends JpaRepository<PlaceOfWork, Long> {
}
