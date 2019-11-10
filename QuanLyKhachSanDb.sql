CREATE DATABASE QuanLyKhachSanDb
go
USE QuanLyKhachSanDb
GO

CREATE TABLE ThanhPho (
	Id int identity PRIMARY KEY,
	Ten nvarchar(100)
)
GO

CREATE TABLE KhachSan(
	Id int identity PRIMARY KEY,
	Ten nvarchar(100),
	DiaChi nvarchar(100),
	SoDienThoai varchar(15),
	CachTrungTamTP int,
	MoTa text,
	GiapBien bit,
	DanhGia int check(DanhGia >= 0 and DanhGia <= 5),
	BuaAn int check(BuaAn >= 0 and BuaAn <= 4), --['Khong co', 'Bua Sang', 'Bua Sang va Trua', 'Bua Sang va Toi', 'Bua Sang, Trua va Toi']
	IdThanhPho int REFERENCES ThanhPho(Id)
)
GO

CREATE TABLE Phong(
	Id int identity PRIMARY KEY,
	Ten nvarchar(100),
	DienTich int,
	GiaThue int,
	TienNghi nvarchar(200),
	MoTa text,
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
	Id int identity PRIMARY KEY,
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

insert into ThanhPho values(N'Hà Nội'),(N'TP. Hồ Chí Minh'), (N'Đà Nắng'), (N'Đà Lạt'), (N'Hội An'), (N'Hạ Long'), (N'Sapa'), (N'Huế'), (N'Nha Trang')

select * from ThanhPho