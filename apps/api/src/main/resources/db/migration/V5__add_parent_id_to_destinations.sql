-- V5__add_parent_id_to_destinations.sql

ALTER TABLE destinations
ADD COLUMN parent_id BIGINT,
ADD CONSTRAINT fk_parent_id
FOREIGN KEY (parent_id) REFERENCES destinations(id) ON DELETE CASCADE;
