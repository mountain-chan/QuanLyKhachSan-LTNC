-- DROP DATABASE QuanLyKhachSanDb --

CREATE DATABASE QuanLyKhachSanDb
go
USE QuanLyKhachSanDb
GO

CREATE TABLE ThanhPho (
	Id int PRIMARY KEY identity(1, 1),
	Ten nvarchar(100),
	MoTa nvarchar(200)
)
GO

CREATE TABLE KhachSan(
	Id int PRIMARY KEY identity(1, 1),
	Ten nvarchar(100),
	DiaChi nvarchar(100),
	SoDienThoai varchar(10),
	CachTrungTam int,
	MoTa nvarchar(1000),
	GiapBien bit,
	DanhGia int check(DanhGia >= 0 and DanhGia <= 5),
	BuaAn int check(BuaAn >= 0 and BuaAn <= 4), --['Không Có', 'Bữa Sáng', 'Bữa Sáng Và Trưa', 'Bữa Sáng Và Tối', 'Cả Ba Bữa']
	IdThanhPho int REFERENCES ThanhPho(Id)
)
GO

CREATE TABLE Phong(
	Id int PRIMARY KEY identity(1, 1),
	Ten nvarchar(100),
	DienTich int,
	GiaThue int,
	TienNghi nvarchar(200),
	MoTa nvarchar(1000),
	ConTrong bit,
	LoaiGiuong int check(LoaiGiuong >= 0 and LoaiGiuong <= 1), --['Giuong Don', 'Giuong Doi']
	IdKhachSan int REFERENCES KhachSan(Id)
)
GO

CREATE TABLE TaiKhoan(
	TenTaiKhoan varchar(50) PRIMARY KEY,
	MatKhau varchar(20),
	TenKhachHang nvarchar(50),
	GioiTinh bit,
	SoDienThoai varchar(15),
	Email varchar(50),
	IsAdmin bit
)
GO

CREATE TABLE DatPhong(
	Id int PRIMARY KEY identity(1, 1),
	TaiKhoan varchar(50) REFERENCES TaiKhoan(TenTaiKhoan),
	IdPhong int REFERENCES Phong(Id),
	NgayDat date,
	NgayDen date,
	NgayTra date,
	DichVu nvarchar(200),
	GhiChuCuaKH bit,
	ThanhTien int
)
GO

-- Insert data

insert into ThanhPho values
(N'Hà Nội', N'Thành Phố Hà Nội'),
(N'TP. Hồ Chí Minh', N'Thành Phố Hồ Chí Minh'),
(N'Đà Nắng', N'Thành Phố Đà Nẵng'),
(N'Đà Lạt', N'Thành Phố Đà Lạt'),
(N'Hội An', N'Thành Phố Hội An'),
(N'Hạ Long', N'Thành Phố Hạ Long'),
(N'Sapa', N'Thành Phố Sapa'),
(N'Huế', N'Thành Phố Huế'),
(N'Nha Trang', N'Thành Phố Nha Trang')

insert into KhachSan values
('The Light Hotel', N'79 Tran Cung, Quận Từ Liêm, Hà Nội', '0366918587', 15, N'Nằm ở quận Hoàn Kiếm, thuộc thành phố Hà Nội, The Light Hotel chỉ cách 300 m từ Nhà Thờ Lớn. Với các phòng nghỉ thanh lịch và hiện đại, khách sạn có hồ bơi ngoài trời cùng tầm nhìn ra vườn.', 0, 3, 2, 1),
('Rising Dragon Palace Hotel', N'24 Hang Ga Street, Quận Ba Đình, Hà Nội', '0328758787', 25, N'Rising Dragon Palace Hotel cách Hồ Hoàn Kiếm và Nhà hát múa rối nước chỉ vài dãy nhà. Khách sạn cung cấp chỗ đỗ xe cũng như Wi-Fi miễn phí và bữa sáng tự chọn miễn phí.', 0, 4, 4, 1),
('Classic Street Hotel', N'41 Hang Be Street, Quận Hoàn Kiếm, Hà Nội', '0365618587', 10, N'Classic Street Hotel tọa lạc trên Phố cổ Hàng Bè ở thành phố Hà Nội, nằm trong bán kính 5 phút đi bộ từ Hồ Hoàn Kiếm, Đền Ngọc Sơn và Nhà hát Múa rối Nước Thăng Long. Khách sạn có lối trang trí kiểu Việt Nam truyền thống, Wi-Fi miễn phí và nhà hàng trong khuôn viên phục vụ các món ăn Việt Nam.', 0, 5, 1, 1),
('O Gallery Majestic Hotel', N'38 Tran Phu, Quận Bắc Từ Liêm, Hà Nội', '0366916587', 12, N'Tọa lạc tại Khu phố Pháp yên bình, O Gallery Majestic Hotel có hồ bơi ngoài trời, phòng tập thể dục và vườn. Khách sạn nằm trong bán kính 700 m từ Nhà thờ Lớn Hà Nội, Bảo tàng Lịch sử Quân sự Việt Nam, Cột cờ Hà Nội và Hoàng thành Thăng Long. Du khách có thể truy cập Wi-Fi miễn phí.', 0, 5, 4, 1),
('Hanoi Allure Hotel', N'52 Dao Duy Tu, Quận Hai Bà Trưng, Hà Nội', '0366918891', 11, N'Hanoi Allure Hotel tọa lạc tại vị trí lý tưởng ở quận Hoàn Kiếm của thành phố Hà Nội, cách Ô Quan Chưởng 200 m, Chợ Đồng Xuân 700 m và Nhà hát múa rối nước Thăng Long 800 m. Trong số các tiện nghi của chỗ nghỉ này có nhà hàng, lễ tân 24 giờ, dịch vụ phòng và WiFi miễn phí trong toàn bộ khuôn viên. Chỗ nghỉ cũng cung cấp sảnh khách chung, trung tâm dịch vụ doanh nhân và dịch vụ thu đổi ngoại tệ cho khách.', 0, 3, 3, 1),
('Hanoi Cristina Hotel & Travel', N'40 Phat Loc, Quận Đống Đa, Hà Nội', '0366915457', 8, N'Có vị trí thuận tiện ở quận Hoàn Kiếm thuộc thành phố Hà Nội, Hanoi Cristina Hotel & Travel nằm cách Ô Quan Chưởng 500 m, Nhà hát múa rối nước Thăng Long 600 m và Chợ Đồng Xuân chưa đến 1 km. Trong số các tiện nghi của khách sạn này có nhà hàng, lễ tân 24 giờ, dịch vụ phòng và Wi-Fi miễn phí, Khách sạn cung cấp các phòng gia đình.', 1, 5, 4, 1),
('Dola Hostel', N'205/26 Bui Vien, Quận 1, TP. Hồ Chí Minh', '0366956527', 4, N'Tọa lạc tại Thành phố Hồ Chí Minh, cách Bảo tàng Mỹ thuật trong vòng 1,3 km và trung tâm thương mại Takashimaya Việt Nam 1,6 km, Dola Hostel cung cấp chỗ nghỉ với sảnh khách chung đồng thời miễn phí WiFi cũng như chỗ đỗ xe riêng cho khách lái xe. Chỗ nghỉ này còn có dịch vụ phòng và sân hiên. Tại đây cũng có lễ tân 24 giờ, bếp chung và dịch vụ thu đổi ngoại tệ cho khách.', 1, 2, 3, 2),
('9 Hostel and Bar', N'9 Bui Thi Xuan street, Quận 10, TP. Hồ Chí Minh', '0365456287', 10, N'Chỉ cách Phố đi bộ Bùi Viện nổi tiếng một quãng đi bộ ngắn, 9 Hostel and Rooftop cung cấp các phòng ngủ tập thể và phòng riêng bắt mắt với truy cập Wi-Fi miễn phí. Tọa lạc tại trung tâm Quận 1 sôi động ở Thành phố Hồ Chí Minh, nhà trọ này có lối đi dễ dàng đến hầu hết các địa danh chính của thành phố như Chợ Bến Thành, Nhà hát Thành phố, Bảo tàng Mỹ thuật và Nhà thờ Đức Bà.', 0, 3, 4, 2),
('Town House 23 Saigon', N'23 Dang Thi Nhu, Quận 2, TP. Hồ Chí Minh', '0366454787', 18, N'Chỉ cách Chợ Bến Thành nổi tiếng 300 m, Town House 23 Saigon cung cấp các phòng được bài trí trang nhã với phòng tắm riêng. Khách cũng được truy cập Wi-Fi miễn phí.', 1, 4, 4, 2),
('Victory Airport Hotel', N'96 Yên Thế, Quận Tân Bình, TP. Hồ Chí Minh', '0366475187', 25, N'Nằm ở thành phố Hồ Chí Minh, cách chợ Tân Định 6 km, Victory Airport Hotel có chỗ nghỉ với quầy bar, chỗ đỗ xe riêng miễn phí, sảnh khách chung và sân hiên. Khách sạn cung cấp WiFi miễn phí, đồng thời nằm trong bán kính khoảng 6 km từ Chùa Giác Lâm và 7 km từ Bảo tàng Chứng tích Chiến tranh. Tại đây cũng cung cấp dịch vụ lễ tân 24 giờ, dịch vụ phòng và dịch vụ thu đổi ngoại tệ cho khách.', 1, 5, 1, 2)

--select * from ThanhPho
--select * from KhachSan