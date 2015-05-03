/*Cambiar la ruta de la imagen*/
INSERT INTO `mobile-campus`.picture
(id, contentType, image, userName) 
SELECT null, 'image/png', LOAD_FILE('/home/marcelo/Development/gitRepos/FIUBA_CAMPUS_MOVIL/fiubappREST/src/main/resources/defaultProfilePicture.png'), userName 
FROM `mobile-campus`.student;
