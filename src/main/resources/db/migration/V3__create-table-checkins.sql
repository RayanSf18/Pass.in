CREATE TABLE tb_check_ins (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    participant_id UUID NOT NULL UNIQUE,
    FOREIGN KEY (participant_id) REFERENCES tb_participants(id) ON DELETE RESTRICT ON UPDATE CASCADE
);