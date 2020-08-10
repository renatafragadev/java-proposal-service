/* Drop column document_type */
ALTER TABLE `associate` DROP COLUMN `document_type`;

/* Alter column size document */
ALTER TABLE `associate` MODIFY COLUMN `document` VARCHAR(11) NOT NULL;
