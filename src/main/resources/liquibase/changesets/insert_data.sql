insert into place_of_work (name_of_object, address_of_object)
values ('ЦОД РУДН', 'г. Mосква, ул.Миклухо-Маклая, д.10 к.2'),
       ('РУДН Медицинский', 'г. Mосква, ул.Миклухо-Маклая, д. 10'),
       ('РУДН Главный корпус', 'г. Mосква, ул.Миклухо-Маклая, д. 6')
       ON CONFLICT DO NOTHING;
