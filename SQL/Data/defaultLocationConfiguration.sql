INSERT INTO `mobile-campus`.configuration
(id, type, isEnabled, distanceInKm, userName) 
SELECT null, 'LOCATION_CONFIGURATION', false, 10, userName 
FROM `mobile-campus`.student;