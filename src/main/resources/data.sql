-- Géneros SIEMPRE primero (las películas los referencian)
INSERT INTO generos (nombre) VALUES ('Acción');
INSERT INTO generos (nombre) VALUES ('Ciencia Ficción');
INSERT INTO generos (nombre) VALUES ('Comedia');
INSERT INTO generos (nombre) VALUES ('Drama');
INSERT INTO generos (nombre) VALUES ('Terror');
INSERT INTO generos (nombre) VALUES ('Fantasía');
INSERT INTO generos (nombre) VALUES ('Romance');

-- Películas: id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero
INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Matrix',136,1999,
  'Un hacker descubre que la realidad que conoce es una simulación creada por máquinas.',
  'Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss',
  'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
  'https://www.youtube.com/embed/vKQi3bBA1y8',
  5,true,true,2,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Gladiator',155,2000,
  'Un general romano traicionado busca venganza como gladiador.',
  'Russell Crowe, Joaquin Phoenix',
  'https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg',
  'https://www.youtube.com/embed/owK1qxDselE',
  5,false,true,1,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Interstellar',169,2014,
  'Astronautas viajan a través de un agujero de gusano en busca de un nuevo hogar.',
  'Matthew McConaughey, Anne Hathaway',
  'https://image.tmdb.org/t/p/w1280/9cTfZWP5TfdnmAjiD6ZBXWIJ7O9.jpg',
  'https://www.youtube.com/embed/zSWdZVtXT7E',
  5,true,true,2,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Joker',122,2019,
  'Arthur Fleck, comediante fracasado, desciende a la locura y se convierte en el Joker.',
  'Joaquin Phoenix, Robert De Niro',
  'https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg',
  'https://www.youtube.com/embed/zAGVQLHvwOY',
  4,false,true,4,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Inception',148,2010,
  'Un ladrón que roba secretos a través de los sueños recibe la misión de plantar una idea.',
  'Leonardo DiCaprio, Joseph Gordon-Levitt',
  'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg',
  'https://www.youtube.com/embed/YoHD9XEInc0',
  5,true,true,2,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Mad Max: Fury Road',120,2015,
  'En un mundo posapocalíptico, Max ayuda a Furiosa a escapar.',
  'Tom Hardy, Charlize Theron',
  'https://image.tmdb.org/t/p/w500/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg',
  'https://www.youtube.com/embed/hEJnMQG9ev8',
  5,true,true,1,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'The Mask',101,1994,
  'Un hombre encuentra una máscara que le da poderes locos.',
  'Jim Carrey',
  'https://image.tmdb.org/t/p/w1280/wPgEFEJ8OYmG5s6dAmd37d60mo2.jpg',
  'https://www.youtube.com/embed/5_sfnQDr1-o',
  4,false,true,3,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Superbad',113,2007,
  'Dos amigos intentan aprovechar su última fiesta de instituto.',
  'Jonah Hill, Michael Cera',
  'https://image.tmdb.org/t/p/w500/ek8e8txUyUwd2BNqj6lFEerJfbq.jpg',
  'https://www.youtube.com/embed/4eaZ_48ZYog',
  4,false,true,3,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Forrest Gump',142,1994,
  'Historia de un hombre con una vida extraordinaria.',
  'Tom Hanks',
  'https://image.tmdb.org/t/p/w500/clolk7rB5lAjs41SD0Vt6IXYLMm.jpg',
  'https://www.youtube.com/embed/bLvqoHBptjg',
  5,true,true,4, null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'The Conjuring',112,2013,
  'Investigadores paranormales ayudan a una familia.',
  'Vera Farmiga, Patrick Wilson',
  'https://image.tmdb.org/t/p/w500/wVYREutTvI2tmxr6ujrHT704wGF.jpg',
  'https://www.youtube.com/embed/k10ETZ41q5o',
  4,false,true,5,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'It',135,2017,
  'Un payaso aterrador acecha a unos niños.',
  'Bill Skarsgård, Jaeden Martell',
  'https://image.tmdb.org/t/p/w500/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg',
  'https://www.youtube.com/embed/xKJmEC5ieOk',
  4,true,true,5,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Harry Potter y la piedra filosofal',152,2001,
  'Un niño descubre que es mago.',
  'Daniel Radcliffe, Emma Watson, Rupert Grint',
  'https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg',
  'https://www.youtube.com/embed/VyHV0BRtdxo',
  5,true,true,6,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'El Señor de los Anillos: La Comunidad del Anillo',178,2001,
  'Un hobbit inicia la destrucción de un anillo.',
  'Elijah Wood, Ian McKellen, Viggo Mortensen',
  'https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg',
  'https://www.youtube.com/embed/V75dMMIW2B4',
  5,true,true,6,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'Titanic',195,1997,
  'Historia de amor en el Titanic.',
  'Leonardo DiCaprio, Kate Winslet',
  'https://image.tmdb.org/t/p/w500/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg',
  'https://www.youtube.com/embed/kVrqfYjkTdQ',
  5,true,true,7,null);

INSERT INTO peliculas (id, titulo, duracion, anio, sinopsis, actores, url_portada, url_trailer, valoracion, favorita, vista, id_genero,resena_personal)
VALUES (DEFAULT,'La La Land',128,2016,
  'Una actriz y un músico luchan por sus sueños.',
  'Ryan Gosling, Emma Stone',
  'https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg',
  'https://www.youtube.com/embed/0pdqf4P9MB8',
  5,true,true,7,null);
