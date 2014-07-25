-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.14 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             8.1.0.4545
-- --------------------------------------------------------

-- Volcando datos para la tabla incostdb.user: ~4 rows (aproximadamente)
INSERT INTO `user` (`ID`, `EMAIL`, `LAST_NAME`, `LOGIN`, `NAME`, `PASSWORD`) VALUES
	(1, 'roberto@rikimred.es', 'Neyra Mendoza', 'roberto', 'Jorge', '72534c4a93ddc043fe3229ed46b1d526c4ccc747febdcd0f284f7f6057a37858'),
	(2, 'karen@rikimred.es', 'Rengifo Cruzalegui', 'karen', 'Karen', '904294d8c54b1c63e40832fa1d95bcde534b310df6d42882ce4baf28f3e0184a'),
	(3, 'roneypc@rikimred.es', 'roney', 'roney', 'roney', '3f1d1b6fe88b31c6151592d43e6f91383bf9a9da11aa5581fd41d7f818a14273'),
	(4, 'mateo@rikimred.es', 'Neyra Rengifo', 'mateo', 'Mateo', '34ddf679b4b0ed44ce874ec3e11cf84359acb7a09bacaa3e50232bbdce8da5b3');

-- Volcando datos para la tabla incostdb.role: ~3 rows (aproximadamente)
INSERT INTO `role` (`ID`, `ROLEDESC`, `ROLENAME`) VALUES
	(1, 'Administrator', 'Administrator'),
	(2, 'User', 'User');

-- Volcando datos para la tabla incostdb.user_roles: ~4 rows (aproximadamente)
INSERT INTO `user_roles` (`ID_ROLE`, `ID_USER`) VALUES
	(1, 1),
	(2, 2),
	(2, 3),
	(2, 4);

-- Volcando datos para la tabla incostdb.category: ~3 FILAS --> Usuario RONEY
INSERT INTO `category` (`ID`, `CATEGORY_TYPE`, `DESCRIPTION`, `NAME`, `ID_USER`) VALUES
	(1, 'FIXED_INCOME', 'FIXED_INCOME', 'FIXED_INCOME', 3),
	(2, 'FIXED_BUDGET', 'FIXED_BUDGET', 'FIXED_BUDGET', 3),
	(3, 'FIXED_EXPENSES', 'FIXED_EXPENSES', 'FIXED_EXPENSES', 3),
	(4, 'FIXED_CASH', 'FIXED_CASH', 'FIXED_CASH', 3),
	(5, 'CATEGORY_SUB_BALANCE', 'CATEGORY_SUB_BALANCE', 'CATEGORY_SUB_BALANCE', 3);
