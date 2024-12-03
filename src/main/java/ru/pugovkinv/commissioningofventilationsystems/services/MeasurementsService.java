package ru.pugovkinv.commissioningofventilationsystems.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;
import ru.pugovkinv.commissioningofventilationsystems.repository.MeasurementsRepository;
import ru.pugovkinv.commissioningofventilationsystems.repository.PointRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementsService {
    /**
     * Репозиторий измерений в точке
     */
    private final MeasurementsRepository measurementsRepository;

    /**
     * Поиск всех измерений, которые принадлежат точке
     * @param point нужная точка
     * @return все измерения
     */
    public List<Measurements> findAll(Point point){return measurementsRepository.findAll(point);}

    /**
     * Поиск измерения по его айди
     * @param id нужное айди
     * @return искомое измерение
     */
    public Optional<Measurements> findById(Long id){return measurementsRepository.findById(id);}

    /**
     * Сохранение измерения
     * @param measurements новое измеренеие
     * @return измерение
     */
    public Measurements save(Measurements measurements){return measurementsRepository.save(measurements);}

    /**
     * Удаление измерения по айди
     * @param id нужное айди
     */
    public void deleteById(Long id){measurementsRepository.deleteById(id);}
}
