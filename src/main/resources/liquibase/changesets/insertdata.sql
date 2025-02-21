insert into place_of_work (name_of_object, address_of_object)
values ('ЦОД РУДН', 'г. Mосква, ул.Миклухо-Маклая, д.10 к.2'),
       ('РУДН Медицинский', 'г. Mосква, ул.Миклухо-Маклая, д. 10'),
       ('РУДН Главный корпус', 'г. Mосква, ул.Миклухо-Маклая, д. 6')
ON CONFLICT DO NOTHING;

insert into ventilation_system(object_id, name_of_system, full_air_volume)
values (1,'В1-коридоры', 2300),
       (1,'П1-туалеты', 600),
       (2,'В1-зал', 1600),
       (2,'П1-зал', 1600),
       (2,'В2-туалеты', 600),
       (3,'В1', 600),
       (3,'П1', 600)
ON CONFLICT DO NOTHING;

insert into points( ventilation_system_id, name_of_point, type_measuring, type_of_hole, cross_sectional_area, air_volume, air_flow_rate)
values ( 1, 'Вводная', 'INSIDE_THE_DUCT', 'RECTANGULAR', 0.24, 2300, 2.66),
       ( 2, 'Вводная', 'INSIDE_THE_DUCT', 'CIRCULAR', 0.0491, 600, 3.39),
       ( 3, 'Вводная', 'INSIDE_THE_DUCT', 'RECTANGULAR', 0.16, 1600, 2.78),
       ( 4, 'Вводная', 'INSIDE_THE_DUCT', 'RECTANGULAR', 0.16, 1600, 2.78),
       ( 5, 'Вводная', 'INSIDE_THE_DUCT', 'RECTANGULAR', 0.09, 600, 1.85),
       ( 6, 'Вводная', 'INSIDE_THE_DUCT', 'CIRCULAR', 0.0314, 600, 5.31),
       ( 7, 'Вводная', 'INSIDE_THE_DUCT', 'CIRCULAR', 0.0314, 600, 5.31)
ON CONFLICT DO NOTHING;