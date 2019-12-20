# Project Quản Lý Khách Sạn

Quản Lý Khách Sạn - Hotel Booking By Java

BTL Môn Lập Trình Nâng Cao

--------------

## Sử dụng:

NetBeans IDE 8.2

Java 8.0_231

Glassfish 4.1.1

PrimeFaces 7.0

Jdbc4-2.0

## Cài đặt:

Trước tiên chạy QuanLyKhachSanDb.sql bằng MSSQL, ở đây dùng Jdbc để kết nối MSSQL

Dùng NetBeans IDE 8.2 mở Project

Chạy thôi

## Demo:

Up lên host thấy nhiều lỗi quá http://whknightz.kilatiron.com/


## Giải thích các Bean:

ApplicationScoped: Tất cả các bean này sẽ được khởi tạo khi ứng dụng web chạy, có lẽ là khi build luôn, nó đi theo server, nghĩa là mỗi server chỉ tồn tại một bean có tên như vậy

SessionScoped: Đi theo session, mỗi khi một trình duyệt vào web thì tất cả các bean này được khởi tạo đi theo phiên làm việc đó, mất đi khi trình duyệt đó tắt hoặc hết thời gian, nghĩa là mỗi trình duyệt có một bean riêng

=> Các bean như BeanKhachSan, BeanLoaiKhachSan, BeanThanhPho chỉ để hiển thị cho người dùng, ai cũng thấy như nhau nên để ApplicaionScoped

=> BeanNavigation, BeanDangNhap lưu các thông tin người dùng, mỗi người một khác nên để SessionScoped
