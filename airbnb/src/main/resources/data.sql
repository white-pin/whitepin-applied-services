------ INSERT user
INSERT INTO airbnb_user ( first_name
					, last_name
                    , sex
                    , birthday
                    , email
                    , telephone
                    , identity_yn
                    , language
                    , monetary_unit
                    , country
                    , self_info
					, join_date
                    , password
                    , whitepin_token)
VALUES ( '길동'
       , '홍'
       , '남성'
       , '1989'
       , 'sell@naver.com'
       , '01011112222'
       , 'Y'
       , '한국어'
       , '한국 원'
       , '대한민국'
       , '안녕하세요, 저는 Mary입니다.\r\n저는 에어비앤비 슈퍼호스트이며, 화이트핀 인증을 완료한 우수 판매자입니다. 홍대나 명동 등 주요 관광지 위주로 숙소를 관리하고 있는만큼, 한국을 방문하고 여행하는 모든 여행자분들의 안전하고 즐거운 여행을 위해 최선을 다하겠습니다.\r\n한국에 오게 된다면 언제든지 다시 찾아오고 싶은 숙소, mary house로 여러분을 초대합니다.'
	   , '2012'
       , '1234'
       , null
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user.first_name = airbnb_user.first_name
    , airbnb_user.last_name = airbnb_user.last_name
    , airbnb_user.sex = airbnb_user.sex
    , airbnb_user.birthday = airbnb_user.birthday
    , airbnb_user.email = airbnb_user.email
    , airbnb_user.telephone = airbnb_user.telephone
    , airbnb_user.identity_yn = airbnb_user.identity_yn
    , airbnb_user.language = airbnb_user.language
    , airbnb_user.monetary_unit = airbnb_user.monetary_unit
    , airbnb_user.country = airbnb_user.country
    , airbnb_user.self_info = airbnb_user.self_info
    , airbnb_user.password = airbnb_user.password
    , airbnb_user.whitepin_token = airbnb_user.whitepin_token;

------ INSERT user
INSERT INTO airbnb_user ( first_name
					, last_name
                    , sex
                    , birthday
                    , email
                    , telephone
                    , identity_yn
                    , language
                    , monetary_unit
                    , country
                    , self_info
					, join_date
                    , password
                    , whitepin_token)
VALUES ( '만월'
       , '장'
       , '여성'
       , '1992'
       , 'buy@naver.com'
       , '01012345678'
       , 'Y'
       , '한국어'
       , '한국 원'
       , '대한민국'
       , '천년동안 살고있다.'
	   , '2011'
       , '1234'
       , null
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user.first_name = airbnb_user.first_name
    , airbnb_user.last_name = airbnb_user.last_name
    , airbnb_user.sex = airbnb_user.sex
    , airbnb_user.birthday = airbnb_user.birthday
    , airbnb_user.email = airbnb_user.email
    , airbnb_user.telephone = airbnb_user.telephone
    , airbnb_user.identity_yn = airbnb_user.identity_yn
    , airbnb_user.language = airbnb_user.language
    , airbnb_user.monetary_unit = airbnb_user.monetary_unit
    , airbnb_user.country = airbnb_user.country
    , airbnb_user.self_info = airbnb_user.self_info
    , airbnb_user.password = airbnb_user.password
    , airbnb_user.whitepin_token = airbnb_user.whitepin_token;

------ INSERT product
INSERT INTO airbnb_product ( product_id
                    , info
                    , title
                    , user_id
                    , address
					, price
					, image)
VALUES ( 1
       , 'Mary House는 버스 정류장과 지하철역에서 도보로 5 분 거리에 있으며 서울에서 여행하기가 매우 쉽습니다. 또한 버스로 15 분이면 동대문 및 명동 지역으로 이동하기 쉽습니다. Mary House 근처에는 가장 크고 오래된 전통 경동 양녕 시장이 있으며 합리적인 가격으로 구입하십시오. Mary House는 매우 조용한 주거 지역에 위치하고 있습니다. MaryHouse는 공식적으로 정부의 인증을 받았으며 법적으로 운영됩니다.'
       , 'Calm and Friendly Mary House''s White Room in seoul'
       , (SELECT airbnb_user.user_id FROM airbnb_user WHERE email = 'sell@naver.com')
	   , '서울'
	   , '66,230'
	   , 'room-1.jpg'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_product.product_id = airbnb_product.product_id
    , airbnb_product.info = airbnb_product.info
    , airbnb_product.title = airbnb_product.title
    , airbnb_product.user_id = airbnb_product.user_id
    , airbnb_product.address = airbnb_product.address
    , airbnb_product.price = airbnb_product.price;
	
------ INSERT product
INSERT INTO airbnb_product ( product_id
                    , info
                    , title
                    , user_id
                    , address
					, price
					, image)
VALUES ( 2
       , ''
       , '[Hongdae 2 min] 홍대입구역 8번출구 걸어서 2분 거리! 최고 역세권, 가성비 최고 아늑한 방 #1'
       , (SELECT airbnb_user.user_id FROM airbnb_user WHERE email = 'sell@naver.com')
	   , '서울'
	   , '77,540'
	   , 'room-2.jpg'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_product.product_id = airbnb_product.product_id
    , airbnb_product.info = airbnb_product.info
    , airbnb_product.title = airbnb_product.title
    , airbnb_product.user_id = airbnb_product.user_id
    , airbnb_product.address = airbnb_product.address
    , airbnb_product.price = airbnb_product.price;

------ INSERT product
INSERT INTO airbnb_product ( product_id 
                    , info
                    , title
                    , user_id
                    , address
					, price
					, image)
VALUES ( 3
       , ''
       , 'Private terrace! 3mins from AREX station'
       , (SELECT airbnb_user.user_id FROM airbnb_user WHERE email = 'sell@naver.com')
	   , '서울'
	   , '48,550'
	   , 'room-3.jpg'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_product.product_id = airbnb_product.product_id
    , airbnb_product.info = airbnb_product.info
    , airbnb_product.title = airbnb_product.title
    , airbnb_product.user_id = airbnb_product.user_id
    , airbnb_product.address = airbnb_product.address
    , airbnb_product.price = airbnb_product.price;	

------ INSERT role-admin/user
INSERT INTO airbnb_role ( role ) VALUES ( 'user' )
ON DUPLICATE KEY
UPDATE airbnb_role.role = airbnb_role.role
;

------ INSERT airbnb_user_role
INSERT INTO airbnb_user_role ( user_id
                         , role_id)
VALUES (
        (SELECT airbnb_user.user_id FROM airbnb_user WHERE email = 'sell@naver.com')
       , (SELECT airbnb_role.role_id FROM airbnb_role WHERE role = 'user')
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user_role.user_id = airbnb_user_role.user_id
    , airbnb_user_role.role_id = airbnb_user_role.role_id;

INSERT INTO airbnb_user_role ( user_id
                         , role_id)
VALUES (
        (SELECT airbnb_user.user_id FROM airbnb_user WHERE email = 'buy@naver.com')
       , (SELECT airbnb_role.role_id FROM airbnb_role WHERE role = 'user')
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user_role.user_id = airbnb_user_role.user_id
    , airbnb_user_role.role_id = airbnb_user_role.role_id;
