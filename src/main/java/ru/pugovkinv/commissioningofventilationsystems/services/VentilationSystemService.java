package ru.pugovkinv.commissioningofventilationsystems.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;
import ru.pugovkinv.commissioningofventilationsystems.repository.VentilationSystemRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис работы с вентиляционными системами
 */
@Service
@RequiredArgsConstructor
public class VentilationSystemService {
    /**
     * Репозиторий вентиляционных систем
     */
    private final VentilationSystemRepository ventilationSystemRepository;
    /**
     * Поиск всех вентиляционных систем
     * @return Все вентиляционные системы
     */
    public List<VentilationSystem> findAll(PlaceOfWork placeOfWork){return ventilationSystemRepository.findAllByPlaceOfWork(placeOfWork);}
    /**
     * Поиск вентиляционный системы по айди
     * @param id айди
     * @return нужная вентиляционная система
     */
    public Optional<VentilationSystem> findById(Long id){return ventilationSystemRepository.findById(id);}
    /**
     * Сохранение вентиляционной системы
     * @param ventilationSystem новая вентиляционная система
     * @return новая вентиляционная система
     */
    public VentilationSystem save(VentilationSystem ventilationSystem){return ventilationSystemRepository.save(ventilationSystem);}
    /**
     * Удаление вентиляционной системы по айди
     * @param id айди
     */
    public void deleteById(Long id){ventilationSystemRepository.deleteById(id);}
}
