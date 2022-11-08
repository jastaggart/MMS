
insert into mms(museumid, museum_closing_time, museum_opening_time, pass_fee) values (1, '09:00:00', '21:00:00', 25)
On CONFLICT(museumid) DO NOTHING;

