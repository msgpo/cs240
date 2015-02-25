BEGIN TRANSACTION;
DROP TABLE WHERE *;
CREATE TABLE batches (id INTEGER PRIMARY KEY, image_url TEXT, field_quantity NUMERIC), FOREIGN KEY(proj_key) REFERENCES projects(id), FOREIGN KEY(owned_by) REFERENCES users(id);
CREATE TABLE fields (FOREIGN KEY(batch_key) REFERENCES batches(id), id INTEGER PRIMARY KEY, field_number NUMERIC, title TEXT, help_url TEXT, x_coord NUMERIC, width NUMERIC, known_value_url TEXT);
CREATE TABLE records (FOREIGN KEY(batch_key) REFERENCES batches(id), FOREIGN KEY(field_key) 
	REFERENCES fields(id), value TEXT, id INTEGER PRIMARY KEY);
CREATE TABLE projects (sample_image TEXT, title TEXT, y_coord NUMERIC, rec_height NUMERIC, record_quantity NUMERIC, id INTEGER PRIMARY KEY);
CREATE TABLE users (first_name TEXT, last_name TEXT, password TEXT, id INTEGER PRIMARY KEY, records_indexed NUMERIC, FOREIGN KEY(assigned_batch) REFERENCES batches(id);
COMMIT;
