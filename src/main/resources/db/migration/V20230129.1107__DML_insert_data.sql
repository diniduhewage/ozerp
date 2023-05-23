INSERT INTO Action(id, name, code, createdDate, modifiedDate)
VALUES 	(1, 'Add', 'ADD', 1674970713508, 1674970713508),
	(2, 'View', 'VIEW', 1674970713508, 1674970713508),
	(3, 'View List', 'VIEW_LIST', 1674970713508, 1674970713508),
	(4, 'Edit', 'EDIT', 1674970713508, 1674970713508),
	(5, 'Delete', 'DELETE', 1674970713508, 1674970713508);
	
	
INSERT INTO Role(id, name, code, createdDate, modifiedDate)
VALUES 	(1, 'Super Admin', 'SUPER_ADMIN', 1674970713508, 1674970713508),
	(2, 'Syatem User', 'SYSTEM_USER', 1674970713508, 1674970713508);
	


INSERT INTO User(id, email, enabled, firstName, lastName, password, createdDate, modifiedDate)
VALUES 	(1, 'onezerotec@gmail.com', true, 'Dinidu', 'onezerotec', '$2a$11$qxLuE/aZO1iNWaxPOyXB.OkNKrRtbhKBPz5CcdBRM.HXhpwUkui96', 1674970713508, 1674970713508);
	

