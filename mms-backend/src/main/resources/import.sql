
insert into mms(museumid, museum_closing_time, museum_opening_time, pass_fee) values (1,'21:00:00', '09:00:00', 25)
On CONFLICT(museumid) DO NOTHING;

insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values ('Owner', 1, 'mmsOwner@gmail.com', 'artworksAreAwesome!', 'mKanaan', 1)
On CONFLICT(staff_memberid) DO NOTHING;

insert into room(room_type, roomid, display_room_number, room_maximum_capacity, room_size, museum_management_system_museumid) values ('StorageRoom', 1, NULL, NULL, NULL, 1)
On CONFLICT(roomid) DO NOTHING;

insert into visitor(visitorid, email, password, username, museum_management_system_museumid) values (1, 'theo@email.com', 'soccer123', 'theosoccer', 1)
On CONFLICT(visitorid) DO NOTHING;

insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values ('Employee', 2, 'Bob@gmail.com', 'pAsSwOrD', 'Bob', 1)
On CONFLICT(staff_memberid) DO NOTHING;


