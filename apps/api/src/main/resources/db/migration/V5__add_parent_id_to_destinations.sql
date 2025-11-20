ALTER TABLE destinations
    ADD COLUMN IF NOT EXISTS parent_id BIGINT;

ALTER TABLE destinations
    ADD CONSTRAINT IF NOT EXISTS fk_parent_id
    FOREIGN KEY (parent_id) REFERENCES destinations(id) ON DELETE CASCADE;
