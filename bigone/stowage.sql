

CREATE TABLE users 
(	first_name TEXT, 
	last_name TEXT, 
	username TEXT, 
	password TEXT, 
	id INTEGER not null PRIMARY KEY autoincrement, 
	records_indexed NUMERIC, 
	assigned_batch NUMERIC
);

CREATE TABLE projects 
(	sample_image TEXT, 
	title TEXT, 
	y_coord NUMERIC, 
	rec_height NUMERIC, 
	record_quantity NUMERIC, 
	id INTEGER not null PRIMARY KEY autoincrement
);

CREATE TABLE batches 
(	id INTEGER not null PRIMARY KEY autoincrement,
	image_url TEXT,
	field_quantity NUMERIC,
	owned_by NUMERIC,
	indexed INTEGER,
	proj_key INTEGER,
	FOREIGN KEY(proj_key) REFERENCES projects(id)
);

CREATE TABLE fields 
(	id INTEGER not null PRIMARY KEY autoincrement, 
	field_number NUMERIC, 
	title TEXT, 
	help_url TEXT, 
	x_coord NUMERIC, 
	width NUMERIC, 
	known_value_url TEXT,
	proj_key INTEGER,
	FOREIGN KEY(proj_key) REFERENCES projects(id) 
);

CREATE TABLE records 
(	id INTEGER not null PRIMARY KEY autoincrement,
	value TEXT, 
	number NUMERIC, 
	batch_key INTEGER,
	field_key INTEGER,
	FOREIGN KEY(batch_key) REFERENCES batches(id), 
	FOREIGN KEY(field_key) REFERENCES fields(id) 
);

