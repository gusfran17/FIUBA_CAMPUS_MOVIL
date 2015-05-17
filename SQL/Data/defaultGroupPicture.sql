/*Cambiar la ruta de la imagen*/
INSERT INTO `mobile-campus`.group_picture
(id, contentType, image, groupId) 
SELECT null, 'image/png', LOAD_FILE('C:\\Users\\gustavo.franco\\Desktop\\defaultGroupPicture.png'), id
FROM `mobile-campus`.study_group;
