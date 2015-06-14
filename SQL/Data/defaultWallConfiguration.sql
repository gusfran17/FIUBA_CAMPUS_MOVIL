INSERT INTO `mobile-campus`.configuration

	(id, type, isEnabled, userName) 

	SELECT null, 'WALL_CONFIGURATION', true, userName 
	FROM `mobile-campus`.student;