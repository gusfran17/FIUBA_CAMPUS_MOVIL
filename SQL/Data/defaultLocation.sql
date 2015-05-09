INSERT INTO `mobile-campus`.location
(id, latitude, longitude, userName) 
SELECT null, null, null, userName
FROM `mobile-campus`.student;