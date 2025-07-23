INSERT INTO [DressOfficeShop].[dbo].[users]
([address], [created_at], [email], [name], [password_hash], [phone], [role])
VALUES
    ('123 Đường ABC, TP.HCM', GETDATE(), 'alice@example.com', 'Alice Nguyen', 'hash123', '0912345678', 'USER'),
    ('456 Đường XYZ, Hà Nội', GETDATE(), 'bob@example.com', 'Bob Tran', 'hash456', '0987654321', 'USER'),
    ('789 Đường DEF, Đà Nẵng', GETDATE(), 'charlie@example.com', 'Charlie Le', 'hash789', '0909090909', 'USER'),
    ('321 Đường QWE, Cần Thơ', GETDATE(), 'dana@example.com', 'Dana Pham', 'hash321', '0933333333', 'ADMIN'),
    ('654 Đường RTY, Huế', GETDATE(), 'edward@example.com', 'Edward Vo', 'hash654', '0944444444', 'USER');

INSERT INTO [DressOfficeShop].[dbo].[fabrics] ([name], [description])
VALUES
    (N'Cotton', N'Vải cotton mềm mại, thấm hút mồ hôi, phù hợp mùa hè.'),
    (N'Linen', N'Vải lanh mát, thoáng khí, thường dùng trong trang phục mùa nóng.'),
    (N'Denim', N'Vải jeans dày dặn, phù hợp phong cách năng động, cá tính.'),
    (N'Silk', N'Vải lụa cao cấp, mềm mịn, sang trọng dùng cho đầm dạ hội.'),
    (N'Polyester', N'Vải nhân tạo có độ bền cao, ít nhăn, dễ giặt ủi.'),
    (N'Tweed', N'Vải dạ ấm áp, thường dùng trong trang phục mùa đông.'),
    (N'Voan', N'Vải voan nhẹ nhàng, mỏng, phù hợp váy maxi hoặc dạ tiệc.'),
    (N'Kaki', N'Vải kaki dày, đứng form, dùng cho đầm công sở.'),
    (N'Ren', N'Vải ren hoa văn, tạo điểm nhấn nữ tính cho đầm dự tiệc.'),
    (N'Nhung', N'Vải nhung sang trọng, dày dặn, giữ ấm tốt.');


INSERT INTO [DressOfficeShop].[dbo].[styles] ([name], [description])
VALUES
    (N'Đầm Suông', N'Kiểu đầm rộng rãi, thoải mái, không ôm sát cơ thể.'),
    (N'Đầm Ôm Body', N'Thiết kế ôm sát đường cong cơ thể, tôn dáng.'),
    (N'Đầm Xòe', N'Phần chân váy xòe rộng, tạo cảm giác nhẹ nhàng nữ tính.'),
    (N'Đầm Peplum', N'Đầm có phần xếp tầng ở eo, giúp che bụng và tạo đường cong.'),
    (N'Đầm Maxi', N'Dài tới mắt cá chân, thướt tha, thường mặc đi biển hoặc dạo phố.'),
    (N'Đầm Midi', N'Dài qua đầu gối đến bắp chân, lịch sự và nữ tính.'),
    (N'Đầm Vest', N'Phong cách thanh lịch, như blazer dáng váy, dùng cho công sở.'),
    (N'Đầm Cổ Yếm', N'Kiểu dáng trẻ trung, hở vai và cổ, thường dùng mùa hè.'),
    (N'Đầm Dạ Hội', N'Dầm dài và sang trọng, phù hợp sự kiện, tiệc cưới.'),
    (N'Đầm Babydoll', N'Dáng rộng, phần chân váy xếp tầng, tạo vẻ đáng yêu trẻ trung.');


INSERT INTO [DressOfficeShop].[dbo].[decorations] ([description], [extra_price], [name])
VALUES
    ('Nơ thắt lưng', 20000, 'Belt Bow'),
    ('Viền ren tay áo', 15000, 'Sleeve Lace'),
    ('Hoa cài áo', 25000, 'Brooch Flower'),
    ('Chân váy 2 lớp', 30000, 'Double Skirt'),
    ('Khuy kim loại', 18000, 'Metal Buttons'),
    ('Túi giả', 10000, 'Fake Pocket'),
    ('Cổ vest phối ren', 35000, 'Lace Collar'),
    ('Họa tiết in nổi', 22000, 'Embossed Pattern'),
    ('Thêu tay', 40000, 'Hand Embroidery'),
    ('Đính ngọc', 50000, 'Pearl Decor');


INSERT INTO [DressOfficeShop].[dbo].[products]
([created_at], [description], [name], [price], [fabric_id], [style_id])
VALUES
    (GETDATE(), 'Váy đầm công sở thanh lịch, phù hợp môi trường văn phòng.', N'Đầm Công Sở Dài Tay', 550000, 1, 1),
    (GETDATE(), 'Váy ôm body sang trọng, chất liệu cao cấp co giãn tốt.', N'Đầm Body Sang Trọng', 680000, 2, 2),
    (GETDATE(), 'Váy maxi nhẹ nhàng, phù hợp đi biển hoặc dự tiệc ngoài trời.', N'Đầm Maxi Hè', 720000, 3, 3),
    (GETDATE(), 'Váy suông basic dễ mặc, phối đồ đơn giản.', N'Váy Suông Basic', 450000, 1, 4),
    (GETDATE(), 'Đầm xòe nữ tính, form rộng thoải mái.', N'Đầm Xòe Nữ Tính', 600000, 2, 5),
    (GETDATE(), 'Váy midi thời trang, phối kèm thắt lưng da.', N'Váy Midi Kèm Thắt Lưng', 700000, 3, 2),
    (GETDATE(), 'Đầm cổ V sang chảnh, phối ren phần tay áo.', N'Đầm Cổ V Phối Ren', 850000, 1, 3),
    (GETDATE(), 'Váy vest form chuẩn, phù hợp phỏng vấn hoặc gặp đối tác.', N'Váy Vest Công Sở', 950000, 4, 1),
    (GETDATE(), 'Váy denim cá tính, hợp phong cách đường phố.', N'Váy Jean Denim', 500000, 5, 6),
    (GETDATE(), 'Đầm dạ hội đuôi cá, phù hợp tiệc cưới.', N'Đầm Dạ Hội Đuôi Cá', 1200000, 6, 2);