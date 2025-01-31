package ru.pugovkinv.commissioningofventilationsystems.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.repository.PlaceOfWorkRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис работы с рабочими объектами
 */
@Service
@RequiredArgsConstructor
public class PlaceOfWorkService {
    /**
     * Репозиторий объектов
     */
    private final PlaceOfWorkRepository placeOfWorkRepository;
    /**
     * Поиск всех объектов
     * @return Лист объектов
     */
    public List<PlaceOfWork> findAll(){return placeOfWorkRepository.findAll();}
    /**
     * Поиск объекта по айди
     *
     * @param id айди
     * @return объект найденный по айди
     */
    public Optional<PlaceOfWork> findById(Long id){return placeOfWorkRepository.findById(id);}
    /**
     * Добаавление объекта в базу данных
     *
     * @param placeOfWork новый объект
     */
    public void save(PlaceOfWork placeOfWork){
        placeOfWorkRepository.save(placeOfWork);
    }
    /**
     * Удаление объекта по айди
     * @param id айди
     */
    public void deleteById(Long id){ placeOfWorkRepository.deleteById(id);}

    /**
     * Обновление объекта
     * @param placeOfWork обновленный объект
     */
    public void updatePlaceOfWork(PlaceOfWork placeOfWork){
        placeOfWorkRepository.save(placeOfWork);
    }
}
