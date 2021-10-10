-- user1
INSERT INTO public.user_info(
    created_date, email, id, login_type, name, password, phone_number, updated_date, balance)
	VALUES ( now(), 'user1', 'user1',  'ORIGIN', 'USER1', 'asdfasdf', '010-1234-1234' , now(), 1000);

INSERT INTO public.authorities(
	 auth, user_idx, created_date)
	VALUES ('CREATOR', (select max(idx) from user_info ), now());

INSERT INTO public.channel_info(
	 channnel_id, channel_title, owner_idx, created_date)
	VALUES ( 'dij03092', 'channel_title1', (select max(idx) from user_info) , now());

INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx)
	VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));

INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx)
	VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));

INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) 
	VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx)
	VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));

INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) 
	VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx)
	VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));

-- user2
INSERT INTO public.user_info(
    created_date, email, id, login_type, name, password, phone_number, updated_date, balance)
	VALUES ( now(), 'user2', 'user2',  'ORIGIN', 'USER1', 'asdfasdf', '010-1234-1235' , now(), 9845.51);

INSERT INTO public.authorities(
	 auth, user_idx, created_date)
	VALUES ('CREATOR', (select max(idx) from user_info ), now());

INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));
INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));
INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));
INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));
INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));
INSERT INTO public.video( created_date, expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx) VALUES ( now(), now()+ interval '136 day', 1264.4 , 100 , '6q4DSKFn_k8', 'title1', ( select max(idx) from channel_info ));


-- user3
INSERT INTO public.user_info(
    created_date, email, id, login_type, name, password, phone_number, updated_date)
	VALUES ( now(), 'user3', 'user3',  'ORIGIN', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));



-- user4
INSERT INTO public.user_info(
    created_date, email, id, login_type, name, password, phone_number, updated_date)
	VALUES ( now(), 'user4', 'user4',  'ORIGIN', 'name4', 'asdfasdf', '010-1234-1235' , now());

INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));
INSERT INTO public.stock( created_date, price, size, owner_idx, video_idx) VALUES ( now(), 1264.4, 3, ( select max(idx) from user_info ), ( select max(idx) from video ));
INSERT INTO public.trading_history( channelid, channel_title, created_date, price, size, type, user_balance, video_title, owner_idx) VALUES ( ( select max(channnel_id) from channel_info ), ( select max(channel_title) from channel_info ), now(), 1264.4, 3, 'BUY', 10600, ( select max(video_title) from video ), (select max(idx) from user_info ));

-- user4
INSERT INTO public.user_info(
    created_date, email, id, login_type, name, password, phone_number, updated_date)
	VALUES ( now(), 'user4', 'user4',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());

INSERT INTO public.authorities(
	 auth, user_idx, created_date)
	VALUES ('ADMIN', (select max(idx) from user_info ), now());


-- users
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u1',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u2',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u3',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u4',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u5',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u6',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u7',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u8',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
INSERT INTO public.user_info(created_date, email, id, login_type, name, password, phone_number, updated_date) VALUES ( now(), 'user4', 'u9',  'NAVER', 'USER1', 'asdfasdf', '010-1234-1235' , now());
