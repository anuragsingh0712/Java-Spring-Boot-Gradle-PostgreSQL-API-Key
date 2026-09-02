INSERT INTO gym_records (id, record_type, title, status) VALUES ('00000000-0000-0000-0000-000000000001', 'gyms', 'Central Fitness', 'ACTIVE') ON CONFLICT DO NOTHING;
INSERT INTO gym_records (id, record_type, title, status) VALUES ('00000000-0000-0000-0000-000000000002', 'members', 'Seed Member', 'ACTIVE') ON CONFLICT DO NOTHING;
INSERT INTO gym_records (id, record_type, title, status) VALUES ('00000000-0000-0000-0000-000000000003', 'trainers', 'Seed Trainer', 'ACTIVE') ON CONFLICT DO NOTHING;
