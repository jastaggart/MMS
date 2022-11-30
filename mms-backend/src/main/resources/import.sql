
-- Instantiating museum 
insert into mms(museumid, museum_closing_time, museum_opening_time, pass_fee) values 
(1,'21:00:00', '09:00:00', 25)
On CONFLICT(museumid) DO NOTHING;

-- Instantiating owner and employee
insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values
 ('Owner', 1, 'mmsOwner@gmail.com', 'artworksAreAwesome!', 'mKanaan', 1)
On CONFLICT(staff_memberid) DO NOTHING;

insert into staff_member(employee_type, staff_memberid, email, password, username, museum_management_system_museumid) values 
('Employee', 2, 'Bob@gmail.com', 'pAsSwOrD', 'Bob', 1)
On CONFLICT(staff_memberid) DO NOTHING;

-- Instantiating rooms (storage + display rooms)
insert into room(room_type, roomid, display_room_number, room_maximum_capacity, room_size, museum_management_system_museumid) values
 ('StorageRoom', 1, NULL, NULL, NULL, 1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',2,10,200,'Small',1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',3,10,200,'Small',1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',4,10,200,'Small',1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',5,10,300,'Big',1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',6,10,300,'Big',1)
On CONFLICT(roomid) DO NOTHING;

insert into room(room_type,roomid,display_room_number,room_maximum_capacity,room_size,museum_management_system_museumid) values 
('DisplayRoom',7,10,300,'Big',1)
On CONFLICT(roomid) DO NOTHING;

-- Instantiating visitor
insert into visitor(visitorid, email, password, username, museum_management_system_museumid) values
(1, 'theo@email.com', 'soccer123', 'theosoccer', 1)
On CONFLICT(visitorid) DO NOTHING;


-- Instantiating artworks
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

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(5, 'Claude Monet', 'True', 'Impression, Sunrise', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(6, 'Claude Monet', 'True', 'Bain a la Grenouillere', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(7, 'Claude Monet', 'True', 'On the Bank of the Seine, Bennecourt', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(8, 'Paul Cezanne', 'True', 'The Basket of Apples', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(9, 'Paul Cezanne', 'True', 'The Card Players', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(10, 'Paul Cezanne', 'True', 'Apples and Oranges', 'OnDisplay', 1, 3)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(11, 'Vincent van Gogh', 'True', 'The Starry Night', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(12, 'Vincent van Gogh', 'True', 'Irises', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(13, 'Vincent van Gogh', 'True', 'Bedroom in Arles', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(14, 'Rembrandt', 'True', 'The Night Watch', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(15, 'Rembrandt', 'True', 'Syndics of the Drapers Guild', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(16, 'Salvador Dali', 'True', 'The Persistence of Memory', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(17, 'Jean-Michel Basquiat', 'True', 'Riding with Death', 'OnDisplay', 1, 5)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(18, 'Pablo Picasso', 'True', 'Dove', 'OnDisplay', 1, 4)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(19, 'Rembrandt', 'True', 'The Storm on the Sea of Galilee', 'OnDisplay', 1, 6)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(20, 'Rembrandt', 'True', 'Self-Portrait with Two Circles', 'OnDisplay', 1, 7)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(21, 'Rembrandt', 'True', 'The Anatomy Lesson of Dr. Nicolaes Tulp', 'OnDisplay', 1, 7)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(22, 'Rembrandt', 'True', 'The Conspiracy of Claudius Civilis', 'OnDisplay', 1, 7)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(23, 'Pierre-Auguste Renoir', 'True', 'Luncheon of the Boating Party', 'InStorage', 1, 1)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(24, 'Pierre-Auguste Renoir', 'True', 'La Grenouillere', 'InStorage', 1, 1)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(25, 'Pierre-Auguste Renoir', 'True', 'The Skiff', 'InStorage', 1, 1)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(26, 'Pierre-Auguste Renoir', 'True', 'Le Pont-Neuf', 'InStorage', 1, 1)
On CONFLICT(artworkid) DO NOTHING;

insert into artwork(artworkid,artist,is_available_for_loan,artwork_name,display_status,museum_management_system_museumid,room_roomid) values 
(27, 'Pierre-Auguste Renoir', 'True', 'Girl Reading', 'InStorage', 1, 1)
On CONFLICT(artworkid) DO NOTHING;



















