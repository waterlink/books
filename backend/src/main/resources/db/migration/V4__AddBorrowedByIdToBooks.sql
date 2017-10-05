ALTER TABLE books ADD COLUMN borrowed_by_id VARCHAR(50);

ALTER TABLE books ADD FOREIGN KEY (borrowed_by_id) REFERENCES members(id);