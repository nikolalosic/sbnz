insert into jhi_authority(name) values ('ROLE_ADMIN');
insert into jhi_authority(name) values ('ROLE_DOCTOR');

INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (1, 'system', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'System', 'System', 'system@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);
INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (2, 'anonymoususer', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'Anonymous', 'User', 'anonymous@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);
INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (3, 'admin', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'Administrator', 'Administrator', 'admin@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);


-- doctors
INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (4, 'doctor', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'Doctor', 'Doctor', 'doctor@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);
INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (5, 'doctor2', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'Doctor 2', 'Doctor 2', 'doctor2@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);
INSERT INTO jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (6, 'doctor3', '$2a$10$mxKOOSofNwe9rvWikXsVcOh6GuYZZroTQGs9iWOzoDby.omNBTrjG', 'Doctor 3', 'Doctor 3', 'doctor3@localhost', '', true, 'en', null, null, 'system', '2018-09-22 17:56:31.670660', null, 'system', null);

INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (3, 'ROLE_ADMIN');

INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (4, 'ROLE_DOCTOR');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (5, 'ROLE_DOCTOR');
INSERT INTO jhi_user_authority(user_id, authority_name) VALUES (6, 'ROLE_DOCTOR');



insert into patient(id, first_name, last_name) values(1, 'Nikola', 'Nikolic');
insert into patient(id, first_name, last_name) values(2, 'Marko', 'Markovic');
insert into patient(id, first_name, last_name) values(3, 'Petar', 'Petrovic');
insert into patient(id, first_name, last_name) values(4, 'Stefan', 'Stefanovic');
insert into patient(id, first_name, last_name) values(5, 'Djordje', 'Djordjevic');


insert into user(id, email, password, name, surname, address, role_id) values (3, 'vanja@gmail.com', '$2a$04$MIVN0WlWN9ZJiqBwcBlpi.D09HmcgFJbIaMO.WkqZYwZBVJxGSfwG', 'Vanja', 'Zoric', 'Adresa3', 3);
insert into user(id, email, password, name, surname, address, role_id) values (4, 'krga@gmail.com', '$2a$04$MIVN0WlWN9ZJiqBwcBlpi.D09HmcgFJbIaMO.WkqZYwZBVJxGSfwG', 'Vesna', 'Krga', 'Adresa4', 3);
insert into user(id, email, password, name, surname, address, role_id) values (5, 'danica@gmail.com', '$2a$04$MIVN0WlWN9ZJiqBwcBlpi.D09HmcgFJbIaMO.WkqZYwZBVJxGSfwG', 'Danica', 'Stojanic', 'Adresa5', 3);
insert into user(id, email, password, name, surname, address, role_id) values (8, 'zoran@gmail.com', '$2a$04$MIVN0WlWN9ZJiqBwcBlpi.D09HmcgFJbIaMO.WkqZYwZBVJxGSfwG', 'Zoran', 'Zaric', 'Adresa8', 3);
insert into user(id, email, password, name, surname, address, role_id) values (9, 'vesna@gmail.com', '$2a$04$MIVN0WlWN9ZJiqBwcBlpi.D09HmcgFJbIaMO.WkqZYwZBVJxGSfwG', 'Vesna', 'Zaric', 'Adresa9', 3);

insert into ingredient(id, name) values (1, 'Sastojak1');
insert into ingredient(id, name) values (2, 'Sastojak2');
insert into ingredient(id, name) values (3, 'Sastojak3');

insert into medicine(id, name, jhi_type) values (1, 'Lek1', 0);
insert into medicine(id, name, jhi_type) values (2, 'Lek2', 1);
insert into medicine(id, name, jhi_type) values (3, 'Lek3', 1);
insert into medicine(id, name, jhi_type) values (4, 'Lek4', 2);
insert into medicine(id, name, jhi_type) values (5, 'Lek5', 1);

insert into medicine_ingredients(medicines_id, ingredients_id) values (1, 1);
insert into medicine_ingredients(medicines_id, ingredients_id) values (2, 1);

insert into patient_allergic_medicines(patients_id, allergic_medicines_id) values (3, 3);
insert into patient_allergic_medicines(patients_id, allergic_medicines_id) values (3, 5);
insert into patient_allergic_medicines(patients_id, allergic_medicines_id) values (5, 2);

insert into patient_allergic_ingredients(patients_id, allergic_ingredients_id) values (3, 1);
insert into patient_allergic_ingredients(patients_id, allergic_ingredients_id) values (5, 1);
insert into patient_allergic_ingredients(patients_id, allergic_ingredients_id) values (5, 2);
insert into patient_allergic_ingredients(patients_id, allergic_ingredients_id) values (5, 3);


--simptomi za prehladu
insert into symptom(id, name) values (1, 'Curenje iz nosa');
insert into symptom(id, name) values (2, 'Bol u grlu');
insert into symptom(id, name) values (3, 'Glavobolja');
insert into symptom(id, name) values (4, 'Kijanje');
insert into symptom(id, name) values (5, 'Kasalj');

--simptomi za groznicu
insert into symptom(id, name) values (6, 'Temperatura veca od 38 stepeni');
insert into symptom(id, name) values (7, 'Drhtavica');

--simptomi za upalu krajnika
insert into symptom(id, name) values (8, 'Bol koji se siri do usiju');
insert into symptom(id, name) values (9, 'Temperatura od 40 do 41 stepen');
insert into symptom(id, name) values (10, 'Gubitak apetita');
insert into symptom(id, name) values (11, 'Umor');
insert into symptom(id, name) values (12, 'Zuti sekret iz nosa');

--simptomi za sinusnu infekciju
insert into symptom(id, name) values (13, 'Oticanje oko ociju');
insert into symptom(id, name) values (14, 'Pacijent bolovao od prehlade ili groznice u proteklih 60 dana');

--simptomi za hipertenziju
insert into symptom(id, name) values (15, 'Pacijent u poslednjih 6 meseci imao bar 10 puta visok pritisak');
insert into symptom(id, name) values (16, 'Visok pritisak');

--simptomi za dijabetes
insert into symptom(id, name) values (17, 'Cesto uriniranje');
insert into symptom(id, name) values (18, 'Gubitak telesne tezine');
insert into symptom(id, name) values (19, 'Zamor');
insert into symptom(id, name) values (20, 'Mucnina i povracanje');

--simptomi za hronicnu bubreznu bolest
insert into symptom(id, name) values (21, 'Nocturia');
insert into symptom(id, name) values (22, 'Otoci nogu i zglobova');
insert into symptom(id, name) values (23, 'Gusenje');
insert into symptom(id, name) values (24, 'Bol u grudima');
insert into symptom(id, name) values (25, 'Pacijent boluje od hipertenzije vise od 6 meseci');
insert into symptom(id, name) values (26, 'Pacijent boluje od dijabetesa');

--simptomi za akutnu bubreznu povredu
insert into symptom(id, name) values (27, 'Oporavlja se od operacije');
insert into symptom(id, name) values (28, 'Dijareja');
insert into symptom(id, name) values (29, 'Povisena telesna temperatura u poslednjih 14 dana');
insert into symptom(id, name) values (30, 'Primao antibiotike u poslednjih 21 dan');

insert into disease(id, name) values (1, 'Prehlada');
insert into disease(id, name) values (2, 'Groznica');
insert into disease(id, name) values (3, 'Upala krajnika');
insert into disease(id, name) values (4, 'Sinusna infekcija');
insert into disease(id, name) values (5, 'Hipertenzija');
insert into disease(id, name) values (6, 'Dijabetes');
insert into disease(id, name) values (7, 'Hronicna bubrezna bolest');
insert into disease(id, name) values (8, 'Akutna bubrezna povreda');

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (1, 1);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (1, 2);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (1, 3);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (1, 4);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (1, 5);

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 4);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 2);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 5);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 6);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 1);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 3);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (2, 7);

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 2);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 8);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 3);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 9);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 7);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 10);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 11);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (3, 12);

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 13);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 3);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 12);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 2);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 6);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (4, 5);
insert into disease_specific_symptoms(disease_id, specific_symptoms_id) values (4, 14);

insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (5, 15);

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (6, 17);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (6, 18);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (6, 19);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (6, 20);

insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (7, 19);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (7, 21);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (7, 22);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (7, 23);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (7, 24);
insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (7, 25);
insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (7, 26);

insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (8, 27);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (8, 28);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (8, 22);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (8, 23);
insert into disease_general_symptoms(diseases_id, general_symptoms_id) values (8, 19);
insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (8, 29);
insert into disease_specific_symptoms(diseases_id, specific_symptoms_id) values (8, 30);

insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (1, NOW(), 1, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (2, NOW(), 2, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (3, NOW(), 2, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (4, NOW(), 3, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (5, NOW(), 4, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (6, NOW(), 1, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (7, NOW(), 2, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (8, NOW(), 2, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (9, NOW(), 3, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (10, NOW(), 4, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (11, NOW(), 3, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (12, NOW(), 1, 4, 1);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (13, NOW(), 2, 4, 1);

insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (14, NOW(), 1, 4, 2);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (15, TIMESTAMP '2017-9-23 15:36:38', 6, 4, 2);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (16, NOW(), 3, 4, 2);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (17, NOW(), 2, 4, 2);

insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (18, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (19, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (20, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (21, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (22, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (23, TIMESTAMP '2016-9-23 15:36:38', 4, 4, 3);

insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (24, NOW(), 4, 4, 4);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (25, NOW(), 4, 4, 4);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (26, NOW(), 4, 4, 4);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (27, NOW(), 4, 4, 4);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (28, NOW(), 4, 5, 4);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (29, NOW(), 4, 6, 4);

insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (30, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (31, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (32, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (33, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (34, NOW(), 5, 5, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (35, NOW(), 4, 6, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (36, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (37, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (38, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (39, NOW(), 4, 4, 5);
insert into diagnose(id, diagnose_date, disease_id, doctor_id, patient_id) values (40, NOW(), 4, 4, 5);

insert into diagnose_medicines(diagnoses_id, medicines_id) values (3, 3);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (4, 3);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (5, 3);

insert into diagnose_medicines(diagnoses_id, medicines_id) values (30, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (31, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (32, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (33, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (34, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (35, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (36, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (37, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (38, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (39, 1);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (40, 1);

insert into diagnose_medicines(diagnoses_id, medicines_id) values (24, 2);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (24, 5);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (25, 2);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (25, 5);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (28, 2);
insert into diagnose_medicines(diagnoses_id, medicines_id) values (29, 2);

insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (1, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (1, 3);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (1, 5);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (2, 9);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (2, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (2, 8);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (3, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (3, 2);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (3, 1);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (4, 1);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (4, 2);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (4, 3);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (5, 4);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (5, 5);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (5, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (6, 6);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (6, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (6, 7);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (7, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (7, 8);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (7, 9);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (8, 10);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (8, 11);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (8, 12);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (9, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (9, 15);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (9, 14);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (10, 4);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (10, 5);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (10, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (11, 16);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (11, 7);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (11, 8);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (12, 10);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (12, 11);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (12, 1);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (13, 1);
insert into diagnose_symptoms(diagnoses_id, symptoms_id) values (19, 22);

/*
insert into user_medical_record(user_id, medical_record_id) values (3, 1);
insert into user_medical_record(user_id, medical_record_id) values (3, 2);
insert into user_medical_record(user_id, medical_record_id) values (3, 3);
insert into user_medical_record(user_id, medical_record_id) values (3, 4);
insert into user_medical_record(user_id, medical_record_id) values (3, 5);
insert into user_medical_record(user_id, medical_record_id) values (3, 6);
insert into user_medical_record(user_id, medical_record_id) values (3, 7);
insert into user_medical_record(user_id, medical_record_id) values (3, 8);
insert into user_medical_record(user_id, medical_record_id) values (3, 9);
insert into user_medical_record(user_id, medical_record_id) values (3, 10);
insert into user_medical_record(user_id, medical_record_id) values (3, 11);
insert into user_medical_record(user_id, medical_record_id) values (3, 12);
insert into user_medical_record(user_id, medical_record_id) values (3, 13);
insert into user_medical_record(user_id, medical_record_id) values (4, 14);
insert into user_medical_record(user_id, medical_record_id) values (4, 15);
insert into user_medical_record(user_id, medical_record_id) values (4, 16);
insert into user_medical_record(user_id, medical_record_id) values (5, 18);
insert into user_medical_record(user_id, medical_record_id) values (5, 19);
insert into user_medical_record(user_id, medical_record_id) values (5, 20);
insert into user_medical_record(user_id, medical_record_id) values (5, 21);
insert into user_medical_record(user_id, medical_record_id) values (5, 22);
insert into user_medical_record(user_id, medical_record_id) values (5, 23);

insert into user_medical_record(user_id, medical_record_id) values (8, 24);
insert into user_medical_record(user_id, medical_record_id) values (8, 25);
insert into user_medical_record(user_id, medical_record_id) values (8, 26);
insert into user_medical_record(user_id, medical_record_id) values (8, 27);
insert into user_medical_record(user_id, medical_record_id) values (8, 28);
insert into user_medical_record(user_id, medical_record_id) values (8, 29);

insert into user_medical_record(user_id, medical_record_id) values (9, 30);
insert into user_medical_record(user_id, medical_record_id) values (9, 31);
insert into user_medical_record(user_id, medical_record_id) values (9, 32);
insert into user_medical_record(user_id, medical_record_id) values (9, 33);
insert into user_medical_record(user_id, medical_record_id) values (9, 34);
insert into user_medical_record(user_id, medical_record_id) values (9, 35);
insert into user_medical_record(user_id, medical_record_id) values (9, 36);
insert into user_medical_record(user_id, medical_record_id) values (9, 37);
insert into user_medical_record(user_id, medical_record_id) values (9, 38);
insert into user_medical_record(user_id, medical_record_id) values (9, 39);
insert into user_medical_record(user_id, medical_record_id) values (9, 40);

*/
