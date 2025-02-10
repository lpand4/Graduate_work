package ru.pugovkinv.commissioningofventilationsystems.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;
import ru.pugovkinv.commissioningofventilationsystems.repository.PointRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    /**
     * Репозиторий точек измерения
     */
    private final PointRepository pointRepository;

    /**
     * Поиск всех точек измерения, которые принадлежат вентиляционной системе
     * @param ventilationSystem необходимая вентиляционная система
     * @return все точки измерения
     */
    public List<Point> findAll(VentilationSystem ventilationSystem){return pointRepository.findAllByVentilationSystem(ventilationSystem);}

    /**
     * Поиск точки измерения по айди
     * @param id необходимое айди
     * @return нужная точка измерения
     */
    public Optional<Point> findById(Long id){return pointRepository.findById(id);}

    /**
     * Сохранение точки измерения
     * @param point новая точка измерения
     * @return точка измерения
     */
    public Point save(Point point){return pointRepository.save(point);}

    /**
     * Удаление точки измерения по айди
     * @param id нужное айди
     */
    public void deleteById(Long id){pointRepository.deleteById(id);}

    /**
     * Обновление точки измерения
     * @param point обновленная точка измерения
     */
    public void updatePoint(Point point){
        pointRepository.save(point);
    }


}
