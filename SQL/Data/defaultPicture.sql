/*Cambiar la ruta de la imagen*/
INSERT INTO `mobile-campus`.picture
(id, contentType, image, userName) 
SELECT null, 'image/png', LOAD_FILE('C:\\Users\\gustavo.franco\\Desktop\\defaultProfilePicture.png'), userName 
FROM `mobile-campus`.student;
