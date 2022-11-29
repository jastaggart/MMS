
insert into mms(museumid, museum_closing_time, museum_opening_time, pass_fee) values 
(1,'21:00:00', '09:00:00', 25)
On CONFLICT(museumid) DO NOTHING;

insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values
 ('Owner', 1, 'mmsOwner@gmail.com', 'artworksAreAwesome!', 'mKanaan', 1)
On CONFLICT(staff_memberid) DO NOTHING;

insert into room(room_type, roomid, display_room_number, room_maximum_capacity, room_size, museum_management_system_museumid) values
 ('StorageRoom', 1, NULL, NULL, NULL, 1)
On CONFLICT(roomid) DO NOTHING;

insert into visitor(visitorid, email, password, username, museum_management_system_museumid) values
(1, 'theo@email.com', 'soccer123', 'theosoccer', 1)
On CONFLICT(visitorid) DO NOTHING;

insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values 
('Employee', 2, 'Bob@gmail.com', 'pAsSwOrD', 'Bob', 1)
On CONFLICT(staff_memberid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',2,10,200,'Small',1)
On CONFLICT(roomid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(1, 'Pablo Picasso', 'True', 'Guernica', 'OnDisplay', 1, 2)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(2, 'Pablo Picasso', 'True', 'La Vie', 'OnDisplay', 1, 2)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(3, 'Claude Monet', 'True', 'The Poppy Field near Argenteuil', 'OnDisplay', 1, 2)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(4, 'Claude Monet', 'True', 'San Giorgio Maggiore at Dusk', 'OnDisplay', 1, 2)
On CONFLICT(artworkid) DO NOTHING;





