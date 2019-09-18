------ INSERT user
INSERT INTO airbnb_user ( id
					, first_name
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
VALUES ( 1
	   , '길동'
       , '홍'
       , '남성'
       , '1989'
       , 'hong@naver.com'
       , '01011112222'
       , 'Y'
       , '한국어'
       , '한국 원'
       , '대한민국'
       , '동해번쩍 서해번쩍'
	   , '2012'
       , 'gildong'
       , null
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user.id = airbnb_user.id
    , airbnb_user.first_name = airbnb_user.first_name
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
INSERT INTO airbnb_user ( id
					, first_name
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
VALUES ( 2
	   , '만월'
       , '장'
       , '여성'
       , '1992'
       , 'man@naver.com'
       , '01012345678'
       , 'Y'
       , '한국어'
       , '한국 원'
       , '대한민국'
       , '천년동안 살고있다.'
	   , '2011'
       , 'manman'
       , null
       )
ON DUPLICATE KEY
UPDATE
    airbnb_user.id = airbnb_user.id
    , airbnb_user.first_name = airbnb_user.first_name
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
INSERT INTO airbnb_product ( id
					, info
                    , title
                    , user_id
                    , address)
VALUES ( 1
	   , '1934년 지어진 한옥을 2011-2012년 에 집 전체를 리노베이션 한 고급 한옥입니다. 한옥을 독채로 빌릴 수 있기 때문에 다른 사람들의 방해를 받지 않고 이용할 수 있습니다. 호스트는 같은 집에서 생활하지 않으며, 편한 시간에 셀프체크인 할 수 있습니다. 이 한옥은 4인 가족이 쾌적하게 사용할 수 있도록 세팅되어 있습니다.'
       , '(독채) 경복궁과 청와대 5분거리 리노베이션 한옥'
       , 1
	   , '서울'
       )
ON DUPLICATE KEY
UPDATE
    airbnb_product.id = airbnb_product.id
    , airbnb_product.info = airbnb_product.info
    , airbnb_product.title = airbnb_product.title
    , airbnb_product.user_id = airbnb_product.user_id
    , airbnb_product.address = airbnb_product.address;
	
