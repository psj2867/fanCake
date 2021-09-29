-- user1
INSERT INTO public.user_info(
     id, is_creator, login_type, name, passwd, created_date)
	VALUES ( 'user1', true , 'ORIGIN', 'USER1', 'safasdf' , now());

INSERT INTO public.authorities(
	 auth, user_idx, created_date)
	VALUES ('test_auth', (select max(idx) from user_info ), now());

INSERT INTO public.authorities(
	 auth, user_idx,created_date)
	VALUES ('creator',( select max(idx) from user_info ), now());

INSERT INTO public.channel_info(
	 channnel_id, channel_title, owner_idx, created_date)
	VALUES ( 'dij03092', 'channel_title1', (select max(idx) from user_info) , now());

INSERT INTO public.video(
	 expiration_date, price_per_share, stock_size, video_id, video_title,  channel_idx, created_date)
	VALUES ( '2021-10-03', 1000.5, 100, 'd303j', 'video_title1', (select max(idx) from channel_info) , '2020-01-02');
INSERT INTO public.video(
	 expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx, created_date)
	VALUES ( '2022-01-03', 1000.5, 100, 'd303j', 'video_title1',(select max(idx) from channel_info),now() );
INSERT INTO public.video(
	 expiration_date, price_per_share, stock_size, video_id, video_title, channel_idx, created_date)
	VALUES ( '2020-01-01', 1000.5, 100, 'd303j', 'video_title1', (select max(idx) from channel_info) ,'2019-12-11');



-- user2
INSERT INTO public.user_info(
     id, is_creator, login_type, name, passwd, created_date)
	VALUES ( 'user2', false , 'ORIGIN', 'USER1', 'safasdf',now());

INSERT INTO public.authorities(
	 auth, user_idx, created_date)
	VALUES ('test2_auth', (select max(idx) from user_info),now() );

INSERT INTO public.stock(
	 price, size, owner_idx, video_idx, created_date)
	VALUES ( '1000.5', '3', (select max(idx) from user_info), (select max(idx) from video), now());