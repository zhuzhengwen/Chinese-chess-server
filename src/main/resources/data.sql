INSERT INTO users (id, phone, nickname, password_hash, avatar, is_vip, vip_plan, vip_expire_date, role, created_at) VALUES
(1, '13800000001', '管理员', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaQBQYClhUNkK', '', false, null, null, 'ADMIN', NOW()),
(2, '13800000002', '棋友2024', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaQBQYClhUNkK', '', true, 'YEAR', DATEADD('YEAR', 1, NOW()), 'USER', NOW());

INSERT INTO manuals (id, title, dynasty, author, category, difficulty, is_premium, description, content, total_moves, view_count, created_at) VALUES
(1, '橘中秘·出车取士局', '明代', '朱晋桢', 'cat1', 1, false, '此局以出车取士为主题，是橘中秘中最经典的开局之一，适合初学者学习基本车法。', '[]', 32, 1520, NOW()),
(2, '梅花谱·蚯蚓降龙', '清代', '张乔栋', 'cat1', 3, false, '蚯蚓降龙是梅花谱中著名的弃子攻杀局，展示了以弱胜强的高超技法。', '[]', 48, 2340, NOW()),
(3, '适情雅趣·当头炮进三兵', '明代', '颜丙', 'cat1', 2, true, '当头炮进三兵是适情雅趣中的经典布局，展示了明代象棋中进攻性战术的精髓。', '[]', 38, 980, NOW()),
(4, '韬略元机·顺炮横车对直车', '清代', '张志', 'cat1', 2, true, '顺炮横车对直车是清代棋谱中记录的经典对局，体现了炮的灵活运用。', '[]', 42, 876, NOW()),
(5, '马炮争雄·马后炮绝杀', '近代', '李志远', 'cat2', 3, true, '马后炮是中国象棋中经典的将杀方式，此局展示了如何巧妙布置马炮配合完成绝杀。', '[]', 56, 1230, NOW()),
(6, '七星聚会', '古代', '佚名', 'cat3', 4, true, '七星聚会是古代流传下来的著名残局，七个兵卒齐聚形成强大攻势，是残局艺术的极致体现。', '[]', 64, 1890, NOW());
