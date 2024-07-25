CREATE TABLE tb_events (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    details VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    maximum_attendees INTEGER NOT NULL
);